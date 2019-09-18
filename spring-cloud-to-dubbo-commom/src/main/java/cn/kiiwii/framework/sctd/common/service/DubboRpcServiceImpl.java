package cn.kiiwii.framework.sctd.common.service;

import cn.kiiwii.framework.sctd.common.beans.DubboRpcBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 项目名  jdrx-microservice
 * Created by zhongdev.
 * Created at 2019-05-30
 * 描述:DubboRpcServiceImpl
 */

@Service(group = "${spring.application.name}")
public class DubboRpcServiceImpl implements IDubboRpcService, ApplicationContextAware, InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(DubboRpcServiceImpl.class);
    private ApplicationContext applicationContext;

    /**
     * 存放contoller层所所有的类，方法mapping
     */
    private List<HandlerMapping> handlerMappings;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 存放了全局异常的异常类类型，和exceptionHandlerInvocation
     */
    private Map<Class<?>, ExceptionHandlerInvocation> exceptionHandlerInvocationMap = new LinkedHashMap<>();

    private MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

    /**
     * 路径输入错误返回的提示map对象，优先返回VO对象，VO为空则返回map对象
     */
    private Map<String, Object> pathNotFound = new HashMap<String, Object>() {{
        put("status", "1");
        put("message", "调用的路径未找到");
        put("data", "");
    }};

    @Override
    public Object callback(DubboRpcBean dubboRpcBean) throws Exception {

        String path = dubboRpcBean.getRpcPath();
        for (HandlerMapping handlerMapping : handlerMappings) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = ((RequestMappingHandlerMapping) handlerMapping).getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                    RequestMappingInfo key = entry.getKey();
                    HandlerMethod value = entry.getValue();

                    PatternsRequestCondition patternsCondition = key.getPatternsCondition();
                    List<String> matchingPatterns = patternsCondition.getMatchingPatterns(path);
                    if (matchingPatterns.size() > 0) {
                        Method method = value.getMethod();
                        Class<?> beanType = value.getBeanType();
                        Object instance = applicationContext.getBean(beanType);
                        MethodParameter[] methodParameters = value.getMethodParameters();

                        if (methodParameters.length <= 0) {
                            return doInvoke(method, instance, null);
                        } else {
                            Object[] values = new Object[methodParameters.length];
                            for (int i = 0; i < methodParameters.length; i++) {
                                MethodParameter methodParameter = methodParameters[i];
                                values[i] = fullValue(dubboRpcBean, methodParameter);
                            }
                            return doInvoke(method, instance, values);
                        }

                    }
                }
            }
        }
        return pathNotFound;
    }

    /**
     * 具体执行controller接口的方法
     * @param method            方法的名称
     * @param instance          类的对象实例
     * @param parameterValues   方法的参数
     * @return                  返回controller层返回的对象
     * @throws Exception        如果controller抛出异常，会进入全局异常的处理
     */
    private Object doInvoke(Method method, Object instance, Object[] parameterValues) throws Exception {
        try {
            if (parameterValues == null) {
                return method.invoke(instance);
            } else {
                return method.invoke(instance, parameterValues);
            }
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                logger.error(targetException.getMessage(), targetException);
                for (Map.Entry<Class<?>, ExceptionHandlerInvocation> entry : exceptionHandlerInvocationMap.entrySet()) {
                    Class<?> aClass = entry.getKey();
                    ExceptionHandlerInvocation exceptionHandlerInvocation = entry.getValue();
                    if (aClass.isAssignableFrom(targetException.getClass())) {
                        Class<?>[] parameterTypes = exceptionHandlerInvocation.getParameterTypes();
                        if (parameterTypes.length == 1) {
                            return exceptionHandlerInvocation.getMethod().invoke(exceptionHandlerInvocation.getTarget(), targetException);
                        }
                    }
                }
            }
            throw e;
        }
    }

    /**
     * 为参数名称填充值
     */
    private Object fullValue(DubboRpcBean dubboRpcBean, MethodParameter methodParameter) throws IOException {

        try {
            methodParameter = methodParameter.nestedIfOptional();
            Class<?> contextClass = methodParameter.getContainingClass();
            Type targetClass = methodParameter.getNestedGenericParameterType();
            Class<?> nestedParameterType = methodParameter.getNestedParameterType();

            RequestHeader requestHeader = methodParameter.getParameterAnnotation(RequestHeader.class);
            if (requestHeader != null) {
                String name = requestHeader.name();
                Object value = dubboRpcBean.getHeader(name);
                if (value == null && requestHeader.required()) {
                    throw new IllegalArgumentException("the header [" + name + "] can not be null ");
                } else if (value == null && !requestHeader.required()) {
                    return null;
                }

                return objectMapper.readValue(objectMapper.writeValueAsString(value), nestedParameterType);
            }

            RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);
            if (requestParam != null) {
                String name = requestParam.name();
                Object value = dubboRpcBean.getParam(name);

                if (value == null && requestParam.required()) {
                    throw new IllegalArgumentException("the param [" + name + "] can not be null ");
                } else if (value == null && !requestParam.required()) {
                    return null;
                }

                return objectMapper.readValue(objectMapper.writeValueAsString(value), nestedParameterType);
            }

            RequestBody requestBody = methodParameter.getParameterAnnotation(RequestBody.class);
            if (requestBody != null) {
                Object bodyString = dubboRpcBean.getBody();
                if (bodyString == null && requestBody.required()) {
                    throw new IllegalArgumentException("the request body can not be null ");
                } else if (bodyString == null && !requestBody.required()) {
                    return null;
                }

                //处理IdDTO这种范性的参数
                InputStream inputStream = new ByteArrayInputStream(objectMapper.writeValueAsBytes(bodyString));
                HttpInputMessage httpInputMessage = new MappingJacksonInputMessage(inputStream, new HttpHeaders());

                return messageConverter.read(targetClass, contextClass, httpInputMessage);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        initHandlerMapping();
        initExceptionHandlerInvocationMap();
    }

    /**
     * 初始化全局异常的
     */
    private void initExceptionHandlerInvocationMap() {
        Map<String, Object> beansWithExceptionHandler = applicationContext.getBeansWithAnnotation(ControllerAdvice.class);
        beansWithExceptionHandler.forEach((s, o) -> {
            Method[] declaredMethods = o.getClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                ExceptionHandler annotation = declaredMethod.getAnnotation(ExceptionHandler.class);
                if (annotation != null) {
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    Parameter[] parameters = declaredMethod.getParameters();
                    Class<? extends Throwable>[] value = annotation.value();
                    for (Class<? extends Throwable> aClass : value) {
                        if (exceptionHandlerInvocationMap.containsKey(aClass)) {
                            continue;
                        }
                        ExceptionHandlerInvocation exceptionHandlerInvocation = new ExceptionHandlerInvocation(o, declaredMethod, parameterTypes, parameters);
                        exceptionHandlerInvocationMap.put(aClass, exceptionHandlerInvocation);
                    }
                }
            }
        });
    }

    private void initHandlerMapping() {
        this.handlerMappings = null;
        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerMapping.class, true, false);

        if (!matchingBeans.isEmpty()) {
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
            AnnotationAwareOrderComparator.sort(this.handlerMappings);
        }
    }

    class ExceptionHandlerInvocation {

        private Object target;
        private Method method;
        private Class<?>[] parameterTypes;
        private Object[] arguments;

        public ExceptionHandlerInvocation(Object target, Method method, Class<?>[] parameterTypes, Object[] arguments) {
            this.target = target;
            this.method = method;
            this.parameterTypes = parameterTypes;
            this.arguments = arguments;
        }

        public Method getMethod() {
            return this.method;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public Object getTarget() {
            return target;
        }
    }
}

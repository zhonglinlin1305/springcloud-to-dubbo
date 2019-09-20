package cn.kiiwii.framework.sctd.sample.zookeeper.comsumer.controller;

import cn.kiiwii.framework.sctd.common.beans.DubboRpcBean;
import cn.kiiwii.framework.sctd.common.service.IDubboRpcService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名  dubbo-nacos-rpc
 * Created by zhongdev.
 * Created at 2019-08-02
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Reference(group = "spring-cloud-to-dubbo-sample-zookeeper-provider", check = false)
    IDubboRpcService dubboRpcService;

    @RequestMapping("/testT")
    public @ResponseBody
    Object test() throws Exception {
        List<Long> longList = Lists.newArrayList();
        longList.add(1L);
        longList.add(2L);
        longList.add(3L);
        longList.add(4L);
        longList.add(5L);
        HashMap<String,Object> body= Maps.newHashMap();
        body.put("ids",longList);

        DubboRpcBean dubboRpcBean = DubboRpcBean
                .builder()
                .setRpcPath("/test/testT")
                .setBody(body)
                .build();
        Object callback = dubboRpcService.callback(dubboRpcBean);
        return callback;
    }

    @RequestMapping("/testO")
    public @ResponseBody
    Object testO() throws Exception {

        Map<String,Object> body= new HashMap<>();
        body.put("name","dubbo");
        body.put("address","china");
        body.put("status",0);

        DubboRpcBean dubboRpcBean = DubboRpcBean
                .builder()
                .setRpcPath("/test/testObject")
                .setBody(body)
                .build();
        Object callback = dubboRpcService.callback(dubboRpcBean);
        return callback;
    }
}

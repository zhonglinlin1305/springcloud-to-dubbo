package cn.kiiwii.framework.sctd.common.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名  project-test
 * Created by zhongdev.
 * Created at 2019-05-18
 * 描述:DubboRpcBean
 */
public class DubboRpcBean implements Serializable {

    private final static String U_ID = "X-UID";
    private final static String ORG_ID = "X-OID";

    private DubboRpcBean(DubboRpcBeanBuilder dubboRpcBeanBuilder) {
        this.dubboRpcBeanBuilder = dubboRpcBeanBuilder;
    }

    private DubboRpcBeanBuilder dubboRpcBeanBuilder;

    public static DubboRpcBeanBuilder builder() {
        return new DubboRpcBeanBuilder();
    }

    public static class DubboRpcBeanBuilder implements Serializable {
        private String rpcPath;
        private Map<String, Object> headers = new HashMap<>();
        private Object body;
        private Map<String, Object> params = new HashMap<>();

        private DubboRpcBeanBuilder() {
        }

        public DubboRpcBean build() {
            return new DubboRpcBean(this);
        }

        public DubboRpcBeanBuilder setRpcPath(String rpcPath) {
            this.rpcPath = rpcPath;
            return this;
        }

        public DubboRpcBeanBuilder setHeader(String k, Object v) {
            headers.put(k, v);
            return this;
        }

        public DubboRpcBeanBuilder addHeaders(Map<String, Object> map) {
            headers.putAll(map);
            return this;
        }

        public DubboRpcBeanBuilder setUid(Object uid) {
            headers.put(U_ID, uid);
            return this;
        }

        public DubboRpcBeanBuilder setOrgId(Object orgId) {
            headers.put(ORG_ID, orgId);
            return this;
        }

        public DubboRpcBeanBuilder setBody(Map<String, Object> body) {
            this.body = body;
            return this;
        }

        public DubboRpcBeanBuilder setBody(Object body) {
            this.body = body;
            return this;
        }

        public DubboRpcBeanBuilder setParam(String k, Object v) {
            params.put(k, v);
            return this;
        }
    }

    public String getRpcPath() {
        return dubboRpcBeanBuilder.rpcPath;
    }

    public Object getBody() {
        return dubboRpcBeanBuilder.body;
    }

    public Object getHeader(String k) {
        return this.dubboRpcBeanBuilder.headers.get(k);
    }

    public Object getUid() {
        return this.dubboRpcBeanBuilder.headers.get(U_ID);
    }

    public Object getOrgId() {
        return this.dubboRpcBeanBuilder.headers.get(ORG_ID);
    }

    public Object getParam(String k) {
        return dubboRpcBeanBuilder.params.get(k);
    }

    public DubboRpcBeanBuilder getBuilder(){
        return this.dubboRpcBeanBuilder;
    }

}

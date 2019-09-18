package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  platform-parent
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.REGISTRY_PROFILE)
public class RegistryProperties extends RegistryConfig {

    /**
     * true:注册dubbo到注册中心
     * false:将不会注册dubbo到注册中新
     */
    private boolean registerEnabled = true;

    private String namespace;

    private String endpoint;

    private String secretKey;

    private String accessKey;

    private String clusterName;

    public boolean isRegisterEnabled() {
        return registerEnabled;
    }

    public void setRegisterEnabled(boolean registerEnabled) {
        this.registerEnabled = registerEnabled;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}

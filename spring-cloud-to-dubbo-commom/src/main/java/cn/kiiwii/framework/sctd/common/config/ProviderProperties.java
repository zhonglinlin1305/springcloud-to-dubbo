package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.ProviderConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  springcloud-to-dubbo
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.PROVIDER_PROFILE)
public class ProviderProperties extends ProviderConfig {

}

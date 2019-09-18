package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  platform-parent
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.SERVICE_PROFILE)
public class ServiceProperties extends ServiceConfig {

}

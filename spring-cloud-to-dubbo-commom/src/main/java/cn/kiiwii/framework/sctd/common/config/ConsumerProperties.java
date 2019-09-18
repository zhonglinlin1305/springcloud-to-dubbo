package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.ProtocolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  springcloud-to-dubbo
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.CONSUMER_PROFILE)
public class ConsumerProperties extends ProtocolConfig {

}

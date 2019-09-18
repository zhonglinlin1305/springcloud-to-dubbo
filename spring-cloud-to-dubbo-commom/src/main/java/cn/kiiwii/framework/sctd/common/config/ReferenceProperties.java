package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.ReferenceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  springcloud-to-dubbo
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.REFERENCE_PROFILE)
public class ReferenceProperties extends ReferenceConfig {

}

package cn.kiiwii.framework.sctd.common.config;

import cn.kiiwii.framework.sctd.common.utils.DubboConstants;
import org.apache.dubbo.config.ModuleConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目名  platform-parent
 * Created by zhongdev.
 * Created at 2019-06-27
 */
@ConfigurationProperties(prefix = DubboConstants.MODULE_PROFILE)
public class ModuleProperties extends ModuleConfig {

}

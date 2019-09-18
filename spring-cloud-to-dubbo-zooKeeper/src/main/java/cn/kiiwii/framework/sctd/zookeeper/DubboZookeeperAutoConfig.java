package cn.kiiwii.framework.sctd.zookeeper;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 项目名  jdrx-microservice
 * Created by zhongdev.
 * Created at 2019-05-30
 * 描述:开始配置的类
 */
@ConditionalOnProperty(value = "dubbo.registry.register-enabled",havingValue = "true",matchIfMissing = true)
@Configuration
@EnableDubbo
@PropertySource("classpath:/spring/dubbo.properties")
@DubboComponentScan(basePackages ={"cn.kiiwii.framework.sctd.common.service"})
public class DubboZookeeperAutoConfig {
}

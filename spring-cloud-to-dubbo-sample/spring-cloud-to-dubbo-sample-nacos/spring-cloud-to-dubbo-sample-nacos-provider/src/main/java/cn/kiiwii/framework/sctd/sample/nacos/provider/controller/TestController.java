package cn.kiiwii.framework.sctd.sample.nacos.provider.controller;

import cn.kiiwii.framework.sctd.sample.nacos.provider.beans.IdsDTO;
import cn.kiiwii.framework.sctd.sample.nacos.provider.beans.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名  dubbo-nacos-rpc
 * Created by zhongdev.
 * Created at 2019-08-02
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/testT")
    public Object testT(@RequestBody IdsDTO idsDTO) {

        return "provider---------------\r\n"+ idsDTO;
    }

    @RequestMapping("/testObject")
    public Object testObject(@RequestBody User user) {

        return "provider---------------\r\n"+ user;
    }
}

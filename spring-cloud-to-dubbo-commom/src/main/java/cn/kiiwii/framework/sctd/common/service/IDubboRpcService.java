package cn.kiiwii.framework.sctd.common.service;

import cn.kiiwii.framework.sctd.common.beans.DubboRpcBean;

/**
 * 项目名  micro-soft-test
 * Created by zhongdev.
 * Created at 2019-05-19
 */
public interface IDubboRpcService {
    Object callback(DubboRpcBean dubboRpcBean) throws Exception;
}

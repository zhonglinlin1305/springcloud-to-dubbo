package cn.kiiwii.framework.sctd.sample.zookeeper.provider.beans;

import java.util.List;

/**
 * 项目名  dubbo-nacos-rpc
 * Created by zhongdev.
 * Created at 2019-09-04
 */
public class IdsDTO<T> {

    private List<T> ids;

    public IdsDTO() {
    }

    public IdsDTO(List<T> ids) {
        this.ids = ids;
    }

    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "IdsDTO{" +
                "ids=" + ids +
                '}';
    }
}

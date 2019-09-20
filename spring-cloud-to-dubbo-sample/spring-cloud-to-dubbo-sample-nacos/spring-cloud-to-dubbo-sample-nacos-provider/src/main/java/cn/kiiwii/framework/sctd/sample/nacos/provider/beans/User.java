package cn.kiiwii.framework.sctd.sample.nacos.provider.beans;

import java.io.Serializable;

/**
 * 项目名  dubbo-nacos-rpc
 * Created by zhongdev.
 */
public class User implements Serializable {

    private String name;
    private String address;
    private int status;

    public User() {
    }

    public User(String name, String address, int status) {
        this.name = name;
        this.address = address;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}

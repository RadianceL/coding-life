package com.eddie.mybatis.data;

import java.util.Set;

public class MapperBean {
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * sql列表
     */
    private Set<ExecuteMethod> list;

    public MapperBean() {
    }

    public MapperBean(String interfaceName, Set<ExecuteMethod> list) {
        this.interfaceName = interfaceName;
        this.list = list;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Set<ExecuteMethod> getList() {
        return list;
    }

    public void setList(Set<ExecuteMethod> list) {
        this.list = list;
    }
}

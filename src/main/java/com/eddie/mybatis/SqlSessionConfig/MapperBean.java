package com.eddie.mybatis.SqlSessionConfig;

import java.util.Set;

public class MapperBean {

    private String interfaceName;

    private Set<Function> list;

    public MapperBean() {
    }

    public MapperBean(String interfaceName, Set<Function> list) {
        this.interfaceName = interfaceName;
        this.list = list;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Set<Function> getList() {
        return list;
    }

    public void setList(Set<Function> list) {
        this.list = list;
    }
}

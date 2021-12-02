package com.eddie.mybatis.data;


import lombok.Data;

@Data
public class ExecuteMethod {
    private String sqlType;
    private String funcName;
    private String sql;
    private Object resultType;
    private String parameterType;
}

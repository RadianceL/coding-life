package com.eddie.mybatis.SqlSessionConfig;


import lombok.Data;

@Data
public class Function {
    /**
     * sql类型
     */
    private String sqlType;
    /**
     * 方法名称
     */
    private String funcName;
    /**
     * sql语句
     */
    private String sql;
    /**
     * 返回类型
     */
    private Object resultType;
    /**
     * 参数类型
     */
    private String parameterType;
}

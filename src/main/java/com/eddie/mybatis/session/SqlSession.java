package com.eddie.mybatis.session;

import java.lang.reflect.Proxy;

/**
 * @author eddie
 */
public class SqlSession {

    private final Executor executor = new QueryExecutor();

    private final Configuration myConfiguration = new Configuration();

    public <T> T selectOne(String statement, Object parameter, Object resultType) {
        System.out.println("第三步：开始代理查询");
        return executor.query(statement, parameter, resultType);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> cls) {
        System.out.println("第一步：代理要映射的对象");
        //动态代理调用
        return (T) Proxy.newProxyInstance(
        		cls.getClassLoader(),
				new Class[]{cls},
                new MapperProxy(myConfiguration, this));
    }
}

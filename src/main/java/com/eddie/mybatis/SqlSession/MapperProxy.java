package com.eddie.mybatis.SqlSession;

import com.eddie.mybatis.SqlSessionConfig.Function;
import com.eddie.mybatis.SqlSessionConfig.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public class MapperProxy implements InvocationHandler {

    private Configuration myConfiguration;

    private SqlSession sqlSession;

    public MapperProxy(Configuration myConfiguration, SqlSession sqlSession) {
        this.myConfiguration = myConfiguration;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("第二步：读取Mapper配置文件");
        MapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }
        Set<Function> list = readMapper.getList();
        if (null != list || 0 != list.size()) {
            for (Function function : list) {
                //id是否和接口方法名一样
                System.out.println("获取方法名添加到list：" + function.getFuncName());
                if (method.getName().equals(function.getFuncName())) {
                    System.out.println("获取方法参数：" + args[0]);
                    return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]), function.getResultType());
                }
            }
        }
        return null;
    }
}

package com.eddie.mybatis.session;

import com.eddie.mybatis.data.ExecuteMethod;
import com.eddie.mybatis.data.MapperBean;
import com.mysql.cj.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Configuration {

    private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     */
    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!"database".equals(node.getName())) {
            throw new RuntimeException("root should be <database>");
        }
        List<Element> property = node.elements("property");
        Map<String, Element> propertyMap = property.stream().collect(Collectors.toMap(Element::getName, Function.identity(), (k1, k2) -> k1));
        String url = getValue(propertyMap.get("url"));
        String username = getValue(propertyMap.get("username"));
        String password = getValue(propertyMap.get("password"));
        String driverClassName = getValue(propertyMap.get("driverClassName"));

        // 注册驱动
        Class.forName(driverClassName);
        Connection connection;
        try {
            //建立数据库链接
            if (StringUtils.isNullOrEmpty(url)){
                throw new RuntimeException("数据库URL不能为空");
            }
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
     */
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            //把mapper节点的nameSpace值存为接口名
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
            //用来存储方法的List
            Set<ExecuteMethod> list = new HashSet<>();
            //遍历根节点下所有子节点
            for (Iterator<Element> rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                //用来存储一条方法的信息
                ExecuteMethod fun = new ExecuteMethod();
                Element e = rootIter.next();
                String sqlType = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqlType(sqlType);
                fun.setFuncName(funcName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }
            mapper.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapper;
    }
}


package com.eddie.mybatis.SqlSession;

import com.eddie.mybatis.SqlSessionConfig.Function;
import com.eddie.mybatis.SqlSessionConfig.MapperBean;
import com.mysql.cj.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Configuration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

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
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for (Element item : node.elements("property")) {
            String value = getValue(item);
            String name = item.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }
            //赋值
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }

        Class.forName(driverClassName);
        Connection connection = null;
        try {
            //建立数据库链接
            if (StringUtils.isNullOrEmpty(url)){
                throw new RuntimeException("数据库URL不能为空");
            }
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
     * @param node
     * @return
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
            Set<Function> list = new HashSet<>();
            //遍历根节点下所有子节点
            for (Iterator<Element> rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                //用来存储一条方法的信息
                Function fun = new Function();
                Element e = rootIter.next();
                String sqltype = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqlType(sqltype);
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


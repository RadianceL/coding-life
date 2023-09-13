package com.eddie.conditional;

import com.eddie.conditional.condition.config.MyConfig;
import com.eddie.conditional.condition.service.GenerateSqlService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author eddie
 * @createTime 2018-12-09
 * @description
 */
public class DoMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(MyConfig.class);
        GenerateSqlService service= context.getBean(GenerateSqlService.class);
        System.out.println("使用的数据库服务:"+service.show());
        context.close();
    }
}

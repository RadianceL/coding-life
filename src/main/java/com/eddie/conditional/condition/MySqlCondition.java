package com.eddie.conditional.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * @author eddie
 * @createTime 2018-12-09
 * @description 数据库判定类
 */
public class MySqlCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context,@NonNull AnnotatedTypeMetadata metadata) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}

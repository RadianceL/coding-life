package com.eddie.conditional.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * @author eddie
 * @createTime 2018-12-09
 * @description
 */
public class OracleCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

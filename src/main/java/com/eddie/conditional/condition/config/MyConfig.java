package com.eddie.conditional.condition.config;

import com.eddie.conditional.condition.MySqlCondition;
import com.eddie.conditional.condition.OracleCondition;
import com.eddie.conditional.condition.service.GenerateSqlService;
import com.eddie.conditional.condition.service.impl.GenerateMySqlServiceImpl;
import com.eddie.conditional.condition.service.impl.GenerateOracleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author eddie
 * @createTime 2018-12-09
 * @description
 */
@Configuration
public class MyConfig {
    /**
     * @Condition 放在类上面，如果配置的条件符合，下面所有的Bean都会被实例化，并交付给Spring容器管理
     * @Condition 放在方法上面，如果配置的条件符合，方法内指定的类会被实例化
     * @return
     */

    @Bean(name = "service")
    @Conditional(OracleCondition.class)
    public GenerateSqlService oracle() {
        return new GenerateOracleServiceImpl();
    }

    @Bean(name = "service")
    @Conditional(MySqlCondition.class)
    public GenerateSqlService mysql() {
        return new GenerateMySqlServiceImpl();
    }

}

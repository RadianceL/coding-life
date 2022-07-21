package com.eddie.conditional.condition.service.impl;

import com.eddie.conditional.condition.service.GenerateSqlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author eddie
 * @createTime 2018-12-09
 * @description 生成MySql数据库方言
 */
@Service
public class GenerateMySqlServiceImpl implements GenerateSqlService {

    @Override
    public String show() {
        return "MysqlService";
    }
}

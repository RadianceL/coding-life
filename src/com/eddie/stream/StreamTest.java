package com.eddie.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author eddie
 * @createTime 2017-04-13
 * @description
 */
public class StreamTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        //添加数据
        for (int i = 0; i < 10; i++) {
            list.add("数据:" + i);
        }

        //在一个Stream操作中，可以有多次惰性求值，但有且仅有一次及早求值。
        List<String> result = list.stream()
                //.parallel()
                //过滤数据
                .filter(e -> e.length() <= 4)
                //.map(e -> e.charAt(2))
                .sorted()
                .peek(e -> System.out.println("打印所有数据  ----  :" + e))
                //惰性求值，不立即返回
                .map(String::valueOf)
                .collect(Collectors.toList());

        System.out.println("----------------------------");
        System.out.println(result);

        long count = list.stream()
                .filter(Objects::nonNull)
                .count();
        System.out.println(count);
    }
}

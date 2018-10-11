package com.eddie.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流处理
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
                .map(e -> String.valueOf(e)) //惰性求值，不立即返回
                .collect(Collectors.toList());

        System.out.println("----------------------------");
        System.out.println(result);

        long count = list.stream()
                .filter(artist -> artist == null ? false : true)
                .count();
        System.out.println(count);
    }
}

package com.eddie.data;

/**
 * @author eddie.lys
 * @since 2023/11/8
 */
public class Test {

    public static void main(String[] args) {
        DataBuilder build = DataBuilder.builder().name("test").from("builder").build();
        DataBuilderFactory.create("test");
        DataBuilder builder = new DataBuilder("", null);
    }
}

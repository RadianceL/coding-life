package com.eddie.data;

/**
 * @author eddie.lys
 * @since 2023/11/8
 */
public class DataBuilderFactory {

    public static DataBuilder create(String type) {
        if (type == null) {
            throw new IllegalArgumentException("type is null");
        }
        return new DataBuilder("", "factory");
    }
}

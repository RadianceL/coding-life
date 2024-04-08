package com.eddie.data;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author eddie.lys
 * @since 2023/11/8
 */
@Builder
public class DataBuilder {

    private String name;

    private String from;

    public DataBuilder(String name, String from) {
        this.name = name;
        this.from = from;
        Throwable cause = new Throwable();
        StackTraceElement[] stackTrace = cause.getStackTrace();
        stackTrace[0].
    }
}

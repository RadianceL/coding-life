package com.eddie.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * since 2020/5/23
 *
 * @author eddie
 */
public class Main {

    public static void main(String[] args) {
        ServiceLoader<SPIService> load = ServiceLoader.load(SPIService.class);
        System.out.println("--------------------------------");
        for (SPIService ser : load) {
            ser.execute();
        }
    }
}

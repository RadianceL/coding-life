package com.eddie.spi.impl;

import com.eddie.spi.SPIService;

/**
 * since 2020/5/23
 *
 * @author eddie
 */
public class SpiService1Impl implements SPIService {

    @Override
    public void execute() {
        System.out.println("SpiImpl1.execute()");
    }
}
package com.eddie.loader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author eddie.lys
 * @since 2023/9/11
 */
public class ClassLoaderPathTest {

    public static void main(String[] args) {
        System.out.println("boot class loader");
    }

}

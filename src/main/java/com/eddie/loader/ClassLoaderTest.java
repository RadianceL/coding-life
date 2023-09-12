package com.eddie.loader;

import sun.misc.Unsafe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author eddie.lys
 * @since 2023/9/11
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException {
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("Sys: " + sysClassLoader);
        ClassLoader extClassLoader = sysClassLoader.getParent();
        System.out.println("Ext: " + extClassLoader);
        ClassLoader bootClassLoader = extClassLoader.getParent();
        System.out.println("Boot: " + bootClassLoader);

        ClassLoader currentClassLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("Cur: " + currentClassLoader);

        ClassLoader booleanClassLoader = Boolean.class.getClassLoader();
        System.out.println("bool: " + booleanClassLoader);


        ClassLoader unsafeClassLoader =  MessageDigest.class.getClassLoader();
        System.out.println("Sun: " + unsafeClassLoader);
    }
}

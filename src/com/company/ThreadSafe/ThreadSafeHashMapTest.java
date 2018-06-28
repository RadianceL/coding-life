package com.company.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreadSafeHashMapTest {


    public static void main(String[] args){

        Map<String,String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

        Thread work1 = new Thread(()->{
            synchronizedMap.put("key","123");
            System.out.println(synchronizedMap);
        });
        Thread work2 = new Thread(()->{
            synchronizedMap.remove("key");
            System.out.println(synchronizedMap);
        });

        work1.start();
        work2.start();
    }


}

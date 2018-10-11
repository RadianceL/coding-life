package com.eddie.container.safe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {


    /**
     * 现在我们知道了什么是ConcurrentHashMap和什么时候该用ConcurrentHashMap，下面我们来复习一下CHM的一些关键点。
     * <p>
     * CHM允许并发的读和线程安全的更新操作
     * 在执行写操作时，CHM只锁住部分的Map
     * 并发的更新是通过内部根据并发级别将Map分割成小部分实现的
     * 高的并发级别会造成时间和空间的浪费，低的并发级别在写线程多时会引起线程间的竞争
     * CHM的所有操作都是线程安全
     * CHM返回的迭代器是弱一致性，fail-safe并且不会抛出ConcurrentModificationException异常
     * CHM不允许null的键值
     * 可以使用CHM代替HashTable，但要记住CHM不会锁住整个Map
     *
     * @param args
     */
    public static void main(String[] args) {

        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        //if map.get(key) == null
        //then map.put(key,value)
        concurrentHashMap.putIfAbsent("", "");


    }

}

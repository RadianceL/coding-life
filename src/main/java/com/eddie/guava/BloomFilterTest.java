package com.eddie.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eddie
 * @createTime 2018-12-10
 * @description 布隆过滤器
 */
public class BloomFilterTest {

    private static final int size = 1000000;

    /**
     * 布隆过滤器原理：
     * 内部存在一个bit数组
     * 初始化后，当一个参数进来，根据算法，随机把3个或多个位置的数置为1，例如：
     * 初始状态： 0000 00000 0000 0000 0000 0000
     * 进来一个数 0001 10000 0000 0100 0000 0000
     * 再来一个数 1001 10010 0000 1000 0000 0000
     * 。。。。。以此类推
     * 当然会有某个数的其中一次或者多次计算的位置之前已经变更成1了，此时该位置值不变，还是1
     * 当布隆过滤器需要判断一个数是否存在于容器内，会先计算这个数对应的三个位置，然后会把这三个位置的值拿出来，是否均为1
     * 如果三个位置的值均为1，则该值大概率在布隆过滤器容器中
     * 大概率：因为有可能某一个数三次计算的位置之前均被其他数置为1，那此时布隆过滤器认为该值存在于容器中，但实际不存在
     * 为了解决这个问题，初始化函数中后两个参数十分重要，第一个是总大小（预计传入多少个），第二个期望容错（0 < fpp < 1）
     * 原因：
     * 当你可能存10个数的时候，你的布隆过滤器内部数组只会初始化10个数的容量，但你如果存1000个数，那结果就是这个数组所有位置均为1
     * 也就会导致任何值布隆过滤器都会认为通过，错误率达到百分之百，这是我们不可接受的，但同时你也不能设置太大
     * 你设置的无限大，那就会占用过多内存导致内存溢出
     *
     * 最后一个参数fpp，是你的容错期望，你能容忍多少的错误率。这个参数会决定把bit数组扩容，或者增加计算次数，比如之前寻找3个位置
     * 如果这个值设置的无限小，那么这个计算就会达到5次 8次 10次甚至更多以达到你的期望容错
     *
     * 其中n为可能有多少个数被添加到过滤器中， p表示你的期望容错
     * 需要注意的是，如果你没有设置你的容错期望，根据Guava的实现，会给p赋值为Double.MIN_VALUE
     * 所以建议是使用三个参数的构造器，以免造成预料之外的内存消耗，程序设计时，应保证所有的内存占用都在预料之中
     * 数组大小的计算公式为：(long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)))
     */
    private static final BloomFilter<Integer> BLOOM_FILTER = BloomFilter.create(Funnels.integerFunnel(), size, 0.001);

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            BLOOM_FILTER.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!BLOOM_FILTER.mightContain(i)) {
                System.out.println("有数据没有被识别到，判定失效");
            }
        }

        List<Integer> list = new ArrayList<>(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (BLOOM_FILTER.mightContain(i)) {
                list.add(i);
            }
        }
        System.out.println("误判通过：" + list.size());
    }

}

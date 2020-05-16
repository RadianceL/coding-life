package com.eddie.structure.function;

/**
 * @author eddie
 * @createTime 2018-10-14
 * @description 集合接口定义
 */
public interface TSet<E> {

    /**
     * 添加一个元素
     * @param e
     */
    void add(E e);

    /**
     * 移除一个元素
     * @param e
     */
    void remove(E e);

    /**
     * 是否包含
     * @param e
     * @return
     */
    boolean contains(E e);

    /**
     * 获取大小
     * @return
     */
    int getSize();

    /**
     * 是否为空
     * @return
     */
    boolean isEmpty();
}

package com.eddie.structure.function;

/**
 * @author eddie
 * @createTime 2018-10-14
 * @description 集合接口定义
 */
public interface Set<E> {

    void add(E e);

    void remove(E e);

    boolean contains(E e);

    int getSize();

    boolean isEmpty();
}

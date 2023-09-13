package com.eddie.structure.function;

public interface TQueue<E> {

    void put(E e);

    E remove();

    int count();

    int getSize();

    boolean isEmpty();


}

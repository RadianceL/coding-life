package com.eddie.structure.function;

public interface TQueue<E> {

	void put(E e);

	E get();

	E delete();

	int count();

	int getSize();

	boolean isEmpty();


}

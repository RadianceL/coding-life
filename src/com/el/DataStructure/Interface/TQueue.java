package com.el.DataStructure.Interface;

public interface TQueue<E> {

	void put(E e);

	E get();

	E delete();

	int count();

	int getSize();

	boolean isEmpty();


}

package com.company.DataStructure.Interface;

public interface TLinkedList<E> {

	E put(E e);

	E put(int pos, E e);

	E putIfAbsent(E e);

	E putAll(TLinkedList<? extends E> linkedList);

	E putFirstIndex(E e);

	E contains(E e);

	E get(E e);

	E get(int pos);

	E delete(E e);

	E delete(int pos);

	void clear();

	void forEach();

	boolean isEmpty();

	//返回修改后大小
	int resize(int capacity);

	//获得容量
	int getCapacity();

	//获得目前有多少数据
	int getSize();
}

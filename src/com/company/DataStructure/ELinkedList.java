package com.company.DataStructure;

import com.company.DataStructure.Interface.TLinkedList;

import java.io.Serializable;
import java.util.Objects;

public class ELinkedList<E> implements
		Cloneable, Serializable, TLinkedList<E> {

	//序列化版本信息
	private static final long serialVersionUID = 1L;

	static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * 牛逼算法，借鉴hashMap 获得大于等于#{cap}的最小2的幂
	 * @param cap
	 * @return
	 */
	static final int tableSizeFor(int cap) {
		int n = cap - 1;    //没有这步操作，则如果#{cap} 已经满足条件，结果将是#{cap}*2n 或 n的右移 1 位  如入参为10， -1 为 9，
		n |= n >>> 1;       //0000 1001 或 0000 0100 结果为 0000 1101
		n |= n >>> 2;       //0000 1101 或 0000 0011 结果为 0000 1111
		n |= n >>> 4;       //0000 1111 或 0000 0000 结果为 0000 1111
		n |= n >>> 8;       //0000 1111 或 0000 0000 结果为 0000 1111
		n |= n >>> 16;      //0000 1111 或 0000 0000 结果为 0000 1111
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	//存储单元
	transient ElementNode<E> node;

	//实际有多少数据
	transient int size;

	public ELinkedList(){
		node = new ElementNode<>(this.hashCode(), null,null);
	}

	@Override
	public E put(E e) {
		put(size, e);
		return e;
	}

	@Override
	public E put(int pos, E e) {
		if (pos < 0 || pos > size)
			throw new IllegalArgumentException("非法参数[pos], pos < 0 || pos >" + size);

		if (pos == 0)
			putFirstIndex(e);
		else{
			ElementNode prev = node;
			for (int i = 0; i < pos - 1; i ++)
				prev = prev.next;

			prev.next = new ElementNode(e.hashCode(), e , prev.next);
		}

		return null;
	}

	@Override
	public E putIfAbsent(E e) {
		return null;
	}

	@Override
	public E putAll(TLinkedList<? extends E> linkedList) {
		return null;
	}

	@Override
	public E putFirstIndex(E e) {
		node = new ElementNode(e.hashCode(), e, node);
		size ++;
		return e;
	}

	@Override
	public E contains(E e) {
		if (e instanceof ElementNode){
			ElementNode element = (ElementNode) e;
		}

		return null;
	}

	@Override
	public E get(E e) {
		return null;
	}

	@Override
	public E get(int pos) {
		return null;
	}

	@Override
	public E delete(E e) {
		return null;
	}

	@Override
	public E delete(int pos) {
		return null;
	}

	@Override
	public void clear() {

	}

	@Override
	public void forEach() {

	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int resize(int capacity) {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append( "ELinkedList{node=[");
		ElementNode ele = node;
		while (ele.next != null){
			E value = (E)ele.getValue();
			ele = ele.next;

			if (ele.next == null)
				result.append(value + "]");
			else
				result.append(value + ",");
		}
		result.append(", size=" + size + "}");

		return result.toString();
	}

	/**
	 * 存储节点 链表
	 * @param <E>
	 */
	static class ElementNode<E>{
		final int hash;
		ElementNode previous;
		E element;
		ElementNode next;

		public ElementNode(int hash, ElementNode previous, E element, ElementNode next){
			this.hash = hash;
			this.previous = previous;
			this.element = element;
			this.next = next;
		}

		public ElementNode(int hash, E element, ElementNode next){
			this.hash = hash;
			this.element = element;
			this.next = next;
		}

		public E getValue() {
			return element;
		}

		public int hashCode(){
			return Objects.hashCode(element);
		}

		public E setValue(E e) {
			E oldVal = element;
			this.element = e;
			return oldVal;
		}

		@Override
		public String toString() {
			return "{" +
					"hash=" + hash +
					", element=" + element +
					"},";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (obj instanceof ElementNode) {
				ElementNode element = (ElementNode) obj;
				if (this.getValue() == element.getValue()){
					return true;
				}

				return false;
			}

			return false;
		}
	}

	/**
	 * 改写序列化方法
	 * @param s
	 * @throws Exception
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws Exception {
		s.defaultWriteObject();
		s.writeInt(size);
	}

	/**
	 * 改写序列化方法
	 * @param s
	 * @throws Exception
	 */
	private void readObject(java.io.ObjectInputStream s) throws Exception {
		s.defaultReadObject();
	}

	public static void main(String args[]){
		ELinkedList<String> stringELinkedList = new ELinkedList<>();
		stringELinkedList.putFirstIndex("fa");
		stringELinkedList.putFirstIndex("fb1");
		stringELinkedList.putFirstIndex("fb2");
		stringELinkedList.put("fb3");
		stringELinkedList.putFirstIndex("fb4");
		stringELinkedList.put(4,"fc");
		System.out.println(stringELinkedList.toString());
	}
}

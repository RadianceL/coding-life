package com.el.DataStructure;

import com.el.DataStructure.Interface.TLinkedList;

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
		node = new ElementNode(1,null);
		size = 0;
	}

	@Override
	public E put(E e) {
		put(size, e);
		return e;
	}

	public void add(int index, E e){

		if(index < 0 || index > size)
			throw new IllegalArgumentException("Add failed. Illegal index.");

		ElementNode prev = node;
		for(int i = 0 ; i < index ; i ++)
			prev = prev.next;

		prev.next = new ElementNode(e, prev.next);
		size ++;
	}

	@Override
	public E put(int index, E e) {
		if (index < 0 || index > size)
			throw new IllegalArgumentException("非法参数[pos], pos < 0 || pos >" + size);

		ElementNode prev = node;
		for (int i = 0; i < index; i ++)
			prev = prev.next;

		prev.next = new ElementNode(e, prev.next);
		size ++;
		return e;
	}

	@Override
	public void putIfAbsent(E e) {
		if (get(e) == -1)
			put(size, e);
	}

	@Override
	public E putFirstIndex(E e) {
		node = new ElementNode(e, node);
		size ++;
		return e;
	}

	@Override
	public boolean contains(E e) {
		if (get(e) == -1){
			return false;
		}
		return true;
	}

	@Override
	public int get(E e) {
		ElementNode prev = node.next;
		int local = 0;
		while (prev != null){
			if (prev.getValue().equals(e)){
				return local;
			}
			local ++;
			prev = prev.next;
		}
		return -1;
	}

	@Override
	public E get(int index) {
		ElementNode prev = node.next;
		for(int i = 0 ; i < index ; i ++)
			prev = prev.next;
		return (E)prev.getValue();
	}

	@Override
	public E delete(E e) {
		ElementNode prev = node.next;
		while (prev != null){
			if (prev.next.getValue().equals(e)){
				E result = (E) prev.next.getValue();
				prev.next = prev.next.next;
				size --;
				return result;
			}
			prev = prev.next;
		}
		return null;
	}

	@Override
	public E delete(int pos) {
		ElementNode head = node;
		for (int i = 0; i < pos; i++)
			head = head.next;

		ElementNode oldValue = head;
		head.next = head.next.next;
		oldValue.next = null;

		size --;
		return (E)oldValue.getValue();
	}

	@Override
	public void clear() {
		node.next = null;
	}

	@Override
	public void forEach(Action e) {
		ElementNode head = node;
		while (head.next != null) {
			e.execute(head);
			head = head.next;
		}

	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append( "ELinkedList{node=[");
		ElementNode ele = node.next;
		while (ele != null){
			E value = (E)ele.getValue();
			result.append(value + "->");
			ele = ele.next;

		}
		result.append("NULL], size=" + size + "}");

		return result.toString();
	}

	/**
	 * 存储节点 链表
	 * @param <E>
	 */
	static class ElementNode<E> implements NodeList<E>{
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

		public ElementNode(E element, ElementNode next){
			this.hash = element.hashCode();
			this.element = element;
			this.next = next;
		}

		public int hashCode(){
			return Objects.hashCode(element);
		}

		@Override
		public E getValue() {
			return element;
		}

		@Override
		public E setValue(E e) {
			E oldVal = element;
			this.element = e;
			return oldVal;
		}

		public ElementNode getPrevious() {
			return previous;
		}

		public ElementNode getNext() {
			return next;
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

	@FunctionalInterface
	public interface Action {
		void execute(ElementNode e);
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

}

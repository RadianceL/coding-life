package com.company.DataStructure;

import com.company.DataStructure.Interface.TStack;

//FIFO
public class EStack<E> implements TStack<E> {

	private Array<E> stack;

	public EStack(int capacity){
		stack = new Array<>(capacity, 0.4f);
	}

	public EStack(){
		stack = new Array<>();
	}

	@Override
	public int getSize(){
		return stack.getSize();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	//O(1) 均摊复杂度
	@Override
	public void push(E e) {
		stack.put(e);
	}

	//O(1)
	@Override
	public E pop(){
		return stack.deleteLast();
	}

	//O(1)
	@Override
	public E peek(){
		return stack.getLast();
	}

	@Override
	public String toString() {
		return "EStack{" +
				"stack=" + stack +
				'}';
	}

	public static void main(String[] args){
		EStack<String> stringEStack = new EStack<>();
		stringEStack.push("ssss");
		stringEStack.push("yyyy");
		stringEStack.push("zzzz");
		stringEStack.push("ssss");
		stringEStack.push("yyyy");
		stringEStack.push("zzzz");
		stringEStack.push("ssss");
		stringEStack.push("yyyy");
		stringEStack.push("zzzz");
		//输出
		System.out.println(stringEStack);
		System.out.println(stringEStack.pop());
		System.out.println(stringEStack);

		stringEStack.push("ttttt");
		//查看栈顶
		System.out.println(stringEStack.peek());
		//弹栈
		System.out.println(stringEStack.pop());
		//压栈（压入size所在的位置）
		stringEStack.push("shshshs");

		System.out.println(stringEStack.pop());
		System.out.println(stringEStack);


	}
}

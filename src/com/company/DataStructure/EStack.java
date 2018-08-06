package com.company.DataStructure;

//FIFO
public class EStack<E> {

	private Array<E> stack;

	private int size;

	public EStack(int capacity){
		stack = new Array<>(capacity, 0.4f);
		size = 0;
	}

	public EStack(){
		stack = new Array<>();
	}

	public int getSize() {
		return size;
	}

	//O(1) 均摊复杂度
	public void push(E e){
		stack.put(e);
		size ++;
	}

	//O(1)
	public E pop(){
		if (size <= 0)
			throw new IllegalArgumentException("栈中无数据");
		size --;
		return stack.deleteLast();
	}

	//O(1)
	public E peek(){
		if (size <= 0)
			throw new IllegalArgumentException("栈中无数据");

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

package com.company.Reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class doMain {

	public static void main(String args[]) {
		WeakReference<String> softReference = new WeakReference<>("fff");
		String s = softReference.get();
		System.out.println(s);
		System.gc();
		System.out.println(s);
	}
}

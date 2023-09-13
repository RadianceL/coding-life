package com.eddie.structure;

import com.eddie.structure.function.TSet;
import com.eddie.structure.tree.impl.BinarySearchTree;

/**
 * @author eddie
 * @createTime 2018-10-14
 * @description 基于二分搜索树的Set
 */
public class BinarySearchTreeSet<E extends Comparable<E>> implements TSet<E> {

    private BinarySearchTree<E> bst;

    public BinarySearchTreeSet(){
        bst = new BinarySearchTree<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.getSize();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}

package com.eddie.structure.tree;

/**
 * @author eddie.lys
 * @since 2022/11/7
 */
@FunctionalInterface
public interface TreeAction<E> {

    void doAction(E e);

}

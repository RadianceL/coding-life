package com.eddie.structure.function;

/**
 * @author eddie
 * @createTime 2018-10-11
 * @description 二分搜索树接口类
 */
public interface TBinarySearchTree<E> {

    /**
     * 获取二分搜索树中的元素个数
     * @return
     */
    int getSize();

    /**
     * 判断二分搜索树是否为空
     */
    boolean isEmpty();

    /**
     * 添加一个元素E
     * @param e
     */
    void add(E e);

    /**
     * 判断树中是否包含元素E
     * @param e
     * @return
     */
    boolean contains(E e);

    /**
     * 前序遍历 深度遍历方式
     */
    void perOrder();

    /**
     * 中序遍历 深度遍历方式
     */
    void inOrder();

    /**
     * 后序遍历 深度遍历方式
     */
    void postOrder();

    /**
     * 非递归前序遍历 深度遍历方式
     */
    void perOrderNR();

    /**
     * 层序遍历 广度优先遍历 利用队列 QUEUE
     * 搜索策略
     */
    void levelOrder();

    /**
     * 获取最小值
     * @return
     */
    E minimum();

    void remove(E e);

    /**
     * 获取最大值
     * @return
     */
    E maximum();

    E removeMin();

    E removeMax();
}

package com.eddie.structure.tree.impl;

import com.eddie.structure.tree.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 平衡二叉树
 *
 * @author eddie.lys
 * @since 2022/11/7
 */
public class AvlTree<E extends Comparable<E>> implements Tree<E> {

    /**
     * 根节点
     */
    private AvlTree.Node<E> root;

    /**
     * 整个二分搜索树中一共有多少个元素E
     */
    private int size;

    //      8  --根节点
    //     / \
    //    5   9 --子节点
    //   / \ / \
    //  1  4 7 11 --叶子节点
    /**
     * 内部类，二分搜索树的节点
     */
    private static class Node<E extends Comparable<E>> {
        /**
         * 高度
         */
        private Integer height;
        /**
         * 值
         */
        public E val;
        /**
         * 左右子树
         */
        public AvlTree.Node<E> left, right;

        public Node(E val) {
            this.height = 1;
            this.val = val;
            this.left = null;
            this.right = null;
        }

    }

    public static void main(String[] args) {
        AvlTree<Integer> avlTree = new AvlTree<>();
        Node<Integer> integerNode = avlTree.buildTree(new Integer[]{3, 2, 1, 4, 5, 6, 7, 10, 9, 8});
        System.out.println(avlTree.levelTraverse(integerNode));
        StringBuilder result = new StringBuilder();
        avlTree.generateBinarySearchTreeResultString(integerNode, 0, result);
        System.out.println(result.toString());
    }

    public List<E> levelTraverse(Node<E> root) {
        List<E> list = new ArrayList<>();
        // 特殊情况，直接返回结果
        if (root == null) {
            return list;
        }

        // 借助队列实现层次遍历
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        list.add(root.val);

        while (!queue.isEmpty()) {
            // 出队，访问左右子节点
            Node<E> node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
                list.add(node.left.val);
            }

            if (node.right != null) {
                queue.offer(node.right);
                list.add(node.right.val);
            }
        }

        return list;
    }

    public Node<E> buildTree(E[] nums) {
        // 创建一个空的根节点
        Node<E> root = null;

        // 依次完成节点插入
        for (int i = 0; i < nums.length; i++) {
            root = insert(nums[i], root);
        }

        return root;
    }

    public int getHeight(Node<E> node) {
        return node == null ? 0 : node.height;
    }

    public Node<E> insert(E val, Node<E> root) {
        // 根节点为空，直接新建节点
        if (root == null) {
            return new Node<>(val);
        }

        // 根据大小关系确定插入位置
        if (root.val.compareTo(val) > 0) {
            // 在左儿子中插入，可能会使得左儿子变高
            root.left = insert(val, root.left);

            // 插入后，不平衡需要调整
            if (getHeight(root.left) - getHeight(root.right) == 2) {
                // 插入的位置是左儿子的左子树，需要右旋
                if (root.left.val.compareTo(val) > 0) {
                    root = rightRota(root);
                } else { // 左儿子的右子树，需要先左旋再右旋
                    root = leftRightRota(root);
                }
            }
        } else if (root.val.compareTo(val) < 0) {
            root.right = insert(val, root.right);

            // 插入后，不平衡需要调整
            if (getHeight(root.right) - getHeight(root.left) == 2) {
                // 右儿子的右子树，左旋
                if (root.right.val.compareTo(val) < 0) {
                    root = leftRota(root);
                } else {
                    root = rightLeftRota(root);
                }
            }
        }

        // 完成插入更新高度
        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        return root;
    }
    /**
     * 右旋
     * @return      新root节点
     */
    private Node<E> rightRota(Node<E> root){
        Node<E> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.left), root.height) + 1;
        return newRoot;
    }
    /**
     * 左旋
     * @return      新root节点
     */
    private Node<E> leftRota(Node<E> root){
        Node<E> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        newRoot.height = Math.max(getHeight(newRoot.right), root.height) + 1;
        return newRoot;
    }
    /**
     * 右友旋
     * @return      新root节点
     */
    private Node<E> leftRightRota(Node<E> root){
        root.left = leftRota(root.left);
        return rightRota(root);
    }
    /**
     * 右左旋
     * @return      新root节点
     */
    private Node<E> rightLeftRota(Node<E> root){
        root.right = rightRota(root.right);
        return leftRota(root);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void add(E e) {

    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public void perOrder() {

    }

    @Override
    public void inOrder() {

    }

    @Override
    public void postOrder() {

    }

    @Override
    public void perOrderNR() {

    }

    @Override
    public void levelOrder() {
        // 借助队列实现层次遍历
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 出队，访问左右子节点
            Node<E> node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    @Override
    public E minimum() {
        return null;
    }

    @Override
    public void remove(E e) {

    }

    @Override
    public E maximum() {
        return null;
    }

    @Override
    public E removeMin() {
        return null;
    }

    @Override
    public E removeMax() {
        return null;
    }

    @Override
    public E[] floor(E e) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        generateBinarySearchTreeResultString(root, 0, result);
        return result.toString();
    }

    public void generateBinarySearchTreeResultString(AvlTree.Node<E> node, int depth, StringBuilder result) {
        if (node == null) {
            result.append(generateDepthString(depth)).append("null\n");
            return;
        }

        result.append(generateDepthString(depth)).append(node.val).append("\n");
        generateBinarySearchTreeResultString(node.left, depth + 1, result);
        generateBinarySearchTreeResultString(node.right, depth + 1, result);
    }

    private String generateDepthString(int depth) {
        return "-".repeat(Math.max(0, depth));
    }


}

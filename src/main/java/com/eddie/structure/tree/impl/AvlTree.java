package com.eddie.structure.tree.impl;

import com.eddie.structure.tree.Tree;
import com.eddie.structure.tree.TreeAction;

import java.util.LinkedList;
import java.util.Objects;
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

    public void buildTree(E[] nums) {
        // 依次完成节点插入
        for (E num : nums) {
            this.root = add(num, root);
        }
    }

    public int getHeight(Node<E> node) {
        return node == null ? 0 : node.height;
    }

    @Override
    public void add(E e) {
        this.root = add(e, root);
        this.size++;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
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
    public void levelOrder(TreeAction<E> treeAction) {
        // 借助队列实现层次遍历
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 出队，访问左右子节点
            Node<E> node = queue.poll();
            treeAction.doAction(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public Node<E> remove(Node<E> root, E val) {
        // 停止条件：节点为null，无法继续删除
        if (root == null) {
            return null;
        }

        // 通过大小关系，确定删除节点的位置
        if (root.val.compareTo(val) > 0) {
            // 在左子树进行节点删除
            root.left = remove(root.left, val);
        } else if (root.val.compareTo(val) < 0) {
            root.right = remove(root.right, val);
        } else {
            // 找到了对应的节点，按情况删除
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) {
                // 如果右树不为空 则右树的最左叶子节点提前，删除右树最左叶子节点
                Node<E> successor = successor(root);
                root.val = successor.val;

                // 在右子树中删除后继节点
                root.right = remove(root.right, successor.val);
            } else {
                // 如果右树不为空 则右树的最左叶子节点提前，删除右树最左叶子节点
                // 前驱节点上提，再删除前驱节点
                Node<E> preSuccessor = preSuccessor(root);
                root.val = preSuccessor.val;

                // 在左子树中删除前驱节点
                root.left = remove(root.left, preSuccessor.val);
            }
        }

        // 删除完成后，可能需要调整平衡度
        if (root == null) {
            return null;
        }

        // 左子树比右子树高，说明删除的是右子树的节点
        if (getHeight(root.left) - getHeight(root.right) >= 2) {
            // 模拟在左子树插入的情况：在左儿子的左子树插入，在左儿子的右子树插入
            if (getHeight(root.left.left) > getHeight(root.left.right)) {
                return rightRota(root);
            } else {
                return leftRightRota(root);
            }
        } else if (getHeight(root.right) - getHeight(root.left) >= 2) { // 在左子树删除节点
            // 模拟在右子树插入节点
            if (getHeight(root.right.right) > getHeight(root.right.left)) {
                return leftRota(root);
            } else {
                return rightLeftRota(root);
            }
        }

        // 无需调整，直接更新root的高度并返回
        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        return root;
    }

    // 寻找前驱节点
    private Node<E> preSuccessor(Node<E> root) {
        // 特殊情况
        if (root == null) {
            return null;
        }
        // 左子树寻找最右节点
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // 寻找后继节点
    private Node<E> successor(Node<E> root) {
        // 特殊情况
        if (root == null) {
            return null;
        }
        // 在右子树寻找最左节点
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }

        return root;
    }


    @Override
    public E minimum() {
        return minimum(root);
    }

    private E minimum(Node<E> root) {
        if (Objects.nonNull(root.left)) {
            return minimum(root.left);
        }
        return root.val;
    }

    @Override
    public void remove(E e) {

    }

    @Override
    public E maximum() {
        return maximum(this.root);
    }

    private E maximum(Node<E> root) {
        if (Objects.nonNull(root.right)) {
            return maximum(root.right);
        }
        return root.val;
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

    public Node<E> add(E val, Node<E> root) {
        // 根节点为空，直接新建节点
        if (root == null) {
            return new Node<>(val);
        }

        // 根据大小关系确定插入位置
        if (root.val.compareTo(val) > 0) {
            // 在左儿子中插入，可能会使得左儿子变高
            root.left = add(val, root.left);

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
            root.right = add(val, root.right);

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

    private void generateBinarySearchTreeResultString(AvlTree.Node<E> node, int depth, StringBuilder result) {
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


    public static void main(String[] args) {
        AvlTree<Integer> avlTree = new AvlTree<>();
//        Node<Integer> integerNode = avlTree.buildTree(new Integer[]{3, 2, 1, 4, 5, 6, 7, 10, 9, 8});
        avlTree.add(3);
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(3);
        avlTree.add(4);
        avlTree.add(5);
        avlTree.add(6);
        avlTree.add(10);
        avlTree.add(8);
        avlTree.add(8);
//        System.out.println(avlTree.levelTraverse());
        System.out.println(avlTree.maximum());
        System.out.println(avlTree.minimum());
    }

}

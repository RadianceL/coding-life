package com.eddie.structure.tree.impl;

import com.eddie.structure.EQueue;
import com.eddie.structure.EStack;
import com.eddie.structure.tree.Tree;

import java.util.*;

/**
 * @author eddie
 * @createTime 2018-10-10
 * @description 二分搜索树 左子树所有节点比右子树小 接口实现 对外屏蔽递归实现方式
 */
public final class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    //      8  --根节点
    //     / \
    //    5   9 --子节点
    //   / \ / \
    //  1  4 7 11 --叶子节点
    //  部分叶子节点为空时 只要满足所有的左子树所有节点比右子树小 也是一个二分搜索树
    //  二分搜索树的元素E必须是可比较的 即继承Comparable的元素

    /**
     * 内部类，二分搜索树的节点
     */
    private static class Node<E> {
        public E e;
        public Node<E> left, right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * 根节点
     */
    private Node<E> root;

    /**
     * 整个二分搜索树中一共有多少个元素E
     */
    private int size;

    /**
     * 默认的初始化构造方法
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * 获取元素E个数
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * 该二分搜索树是否为空
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 添加一个元素E
     */
    @Override
    public void add(E e) {
        root = addNode(root, e);
    }

    /**
     * 是否包含一个元素E
     */
    @Override
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 前序遍历 深度遍历方式
     */
    @Override
    public void perOrder() {
        perOrder(root);
    }

    /**
     * 中序遍历 深度遍历方式
     */
    @Override
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 后序遍历 深度遍历方式
     */
    @Override
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 非递归前序遍历 深度遍历方式
     */
    @Override
    public void perOrderNR() {
        //自己实现的Stack结构 利用底层数组
        EStack<Node<E>> stack = new EStack<>();
        if (root != null) {
            stack.push(root);

            while (!stack.isEmpty()) {
                //取出栈顶元素
                Node<E> cur = stack.pop();
                System.out.println(cur.e);

                //因为利用栈结构 Stack 后进先出 所以 先压入右子树 再压左子树
                //前提 该子节点的子节点不为空 则压入 否则不做处理
                if (!Objects.isNull(cur.right)) {
                    stack.push(cur.right);
                }
                if (!Objects.isNull(cur.left)) {
                    stack.push(cur.left);
                }
            }
        }else {
            throw new IllegalArgumentException("root node is empty!");
        }
    }

    /**
     * 层序遍历 广度优先遍历 利用队列 QUEUE
     * 搜索策略
     */
    @Override
    public void levelOrder(){
        /////////////////
        //      5      //   先把 5 压入队列 弹出5打印 并把5的左右子树压入队列
        //     / \     //   5 <- queue <- 3 6
        //    3   6    //   弹出 3 节点打印 把 3 的左右子树压入队列 同理 6 节点打印 压入左右子树
        //   / \   \   //   3 <- queue <- 6 2 4 && 6 <- queue <- 2 4 8
        //  2   4   8  //   此时队列中 所有节点均为叶子节点 该轮处理完成后 队列为空 结束遍历
        /////////////////
        Queue<Node<?>> queue = new ArrayDeque<>();
        queue.add(root);
        //队列先进先出原则 先把根节点压入队列 打印 再把左右子树压入队列
        //继续处理左右子树 打印左子树 并把左子树左右子树再压入队列 然后处理右子树
        while (!queue.isEmpty()){
            Node<?> cur = queue.remove();
            System.out.println(cur.e);

            if (!Objects.isNull(cur.left)) {
                queue.add(cur.left);
            }
            if (!Objects.isNull(cur.right)) {
                queue.add(cur.right);
            }
        }
    }

    /**
     * 获取该二分搜索树中最小元素E
     */
    @Override
    public E minimum(){
        if (size == 0){
            throw new IllegalArgumentException("There is no element, You idiot!");
        }

        return minimum(root).e;
    }

    @Override
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 获取该二分搜索树中最大元素E
     * @return
     */
    @Override
    public E maximum(){
        if (size == 0){
            throw new IllegalArgumentException("There is no element, You idiot!");
        }
        return maximum(root).e;
    }

    @Override
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    @Override
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    @Override
    public E[] floor(E e) {
        floor(root, e);

        return null;
    }

    private Node<E> floor(Node<E> node, E e) {
        // 中序遍历得到最接近且比e小的节点
        if (node == null) {
            return null;
        }

        int cmp = e.compareTo((E) node.e);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return floor(node.left, e);
        }
        Node<E> rightNode = floor(node.right, e);
        return Objects.requireNonNullElse(rightNode, node);
    }

    /**
     * 删除以node为根节点的二分搜索树最小节点
     * 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @return
     */
    private Node<E> removeMin(Node<E> node) {
        if (node.left == null){
            return deleteLeftNode(node);
        }
        //如果node.left != null 此时说明当前node节点还有更小值 递归寻找
        //直到找到最小值后 让node.left挂载原最小值节点的右子树
        node.left = removeMin(node.left);
        //最后返回整个二分搜索树的根节点 替换root节点
        return node;
    }

    /**
     * 删除以node为根节点的二分搜索树最大节点
     * 返回删除节点后新的二分搜索树的根 原理同removeMin
     *
     * @param node
     * @return
     */
    private Node<E> removeMax(Node<E> node) {
        if (node.right == null){
            return deleteRightNode(node);
        }
        node.right = removeMax(node.right);
        return node;
    }

    private Node<E> deleteLeftNode(Node<E> node){
        //如果node.left == null 说明当前node节点为该二分搜索树的最小值节点
        //保存当前节点的右子树 右子树为空也没关系 总体看不影响正常运行 也不影响二分搜索树的定义
        Node<E> rightTree = node.right;
        //把当前node节点的右子树置为null 根据垃圾回收可达性分析 此时当前node节点不可达 下次System.gc()时就会被清理
        node.right = null;
        //维护size的值
        size --;
        //返回右子树 结束条件判定结束
        return rightTree;
    }

    private Node<E> deleteRightNode(Node<E> node){
        Node<E> rightTree = node.left;
        node.left = null;
        size --;
        return rightTree;
    }

    private Node<E> minimum(Node<E> node) {
        if (node.left == null){
            return node;
        }
        return minimum(node.left);
    }

    private Node<E> maximum(Node<E> node) {
        if (node.right == null){
            return node;
        }
        return maximum(node.right);
    }

    /**
     * 删除以node为根的二分搜索树节点为E的节点 递归算法
     * 返回删除节点后新二分搜索树的根
     * @param node
     * @param e
     * @return
     */
    private Node<E> remove(Node<E> node, E e) {
        if (node == null){
            //找到最后一个节点 依旧没有找到 则要删除的元素不存在 直接返回null
            return null;
        }
        //如果元素比当前元素小 则在该节点的左子树递归寻找
        if (e.compareTo((E) node.e) < 0){
            node.left = remove(node.left, e);
            return node;
        }
        //如果元素比当前元素大 则在该节点的右子树递归寻找
        if (e.compareTo((E) node.e) > 0){
            node.right = remove(node.right, e);
            return node;
        }

        //如果当前元素与之相等 则当前节点就是要删除的节点 开始删除逻辑
        if (e.compareTo((E) node.e) == 0){
            //左子树为空 则直接删除当前节点 让右子树替换当前节点
            if (node.left == null){
                return deleteRightNode(node);
            }
            //右子树为空 则直接删除当前节点 让左子树替换当前节点
            if (node.right == null){
                return deleteLeftNode(node);
            }

            //待删除节点左右子树都不为空 找到其右子树的最小值
            Node<E> successor = minimum(node.right);
            //将其删除 赋值到successor的右子树节点
            successor.right = removeMin(node.right);
            //因为其删除的节点并未真正删除 需要吧removeMin中size--操作加回来
            size ++;

            //将successor的左子树赋值为原当前节点的左子树
            successor.left = node.left;

            //将原节点值置空 等待系统GC处理
            node.left = node.right = null;
            //此时因为递归操作 栈中上一个函数会把node.right或node.left赋值为successor，所以当前这个函数中的node节点不可达
            //此时真正删除当前节点 siz --;
            size --;
            return successor;
        }
        return null;
    }

    /**
     * 需要提前判定"root"节点为NULL情况
     * 向以Node为根节点的二分搜索树中插入元素E 递归算法
     *
     * @param node
     * @param e
     */
    private void add(Node<E> node, E e) {
        if (e.equals(node.e)) {
            //终止条件 不考虑相等的情况
            return;
        }
        if (e.compareTo((E) node.e) < 0 && node.left == null) {
            node.left = new Node<>(e);
            size++;
            //终止条件
            return;
        }
        if (e.compareTo((E) node.e) > 0 && node.right == null) {
            node.right = new Node<>(e);
            size++;
            //终止条件
            return;
        }
        if (e.compareTo(node.e) < 0) {
            //要添加的元素E < 当前节点的元素E 此时要添加的元素E 一定在当前节点的左子树上
            add(node.left, e);
        } else {
            //相反 一定在当前节点的右子树上 不考虑相等的情况
            add(node.right, e);
        }
    }

    /**
     * 返回插入新节点后二分搜索树的根 优化递归算法
     *
     * @param node
     * @param e
     */
    private Node<E> addNode(Node<E> node, E e) {
        if (node == null) {
            size ++;
            //先判定如果当前节点为空，那么如果需要添加，那么一定需要new一个节点Node，存放新的元素E
            return new Node<>(e);
        }
        if (e.compareTo(node.e) < 0) {
            //如果 要添加的元素E < 当前节点的元素E 则需要把新的节点挂载在当前元素的左子树上
            node.left = addNode(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            //相反 则挂载在当前元素的右子树上
            node.right = addNode(node.right, e);
        }
        return node;
    }

    /**
     * 以node 为根节点的二分搜索树中 是否包含元素E 递归实现
     *
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node<E> node, E e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    /**
     * 前序遍历以node为根的二分搜索树 常用方式 按顺序遍历所有元素
     *
     * @param node
     */
    private void perOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        //do something
        System.out.println(node.e);
        perOrder(node.left);
        perOrder(node.right);
    }

    /**
     * 中序遍历以node为根的二分搜索树 符合二分搜索树 则结果必然为顺序排列
     * 先访问左子树最小的节点 然后访问左子树右侧节点 从小到大
     *
     * @param node
     */
    private void inOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        //do something
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 后序遍历以node为根的二分搜索树 场景：释放内存方式 处理所有子节点 最后处理节点本身
     *
     * @param node
     */
    private void postOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        //do something
        System.out.println(node.e);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        generateBinarySearchTreeResultString(root, 0, result);
        return result.toString();
    }

    private void generateBinarySearchTreeResultString(Node<E> node, int depth, StringBuilder result) {
        if (node == null) {
            result.append(generateDepthString(depth)).append("null\n");
            return;
        }

        result.append(generateDepthString(depth)).append(node.e).append("\n");
        generateBinarySearchTreeResultString(node.left, depth + 1, result);
        generateBinarySearchTreeResultString(node.right, depth + 1, result);
    }

    private String generateDepthString(int depth) {
        return "-".repeat(Math.max(0, depth));
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(1);
        tree.add(2);
        tree.perOrderNR();

        System.out.println(tree);
    }
}

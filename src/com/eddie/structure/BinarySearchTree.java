package com.eddie.structure;

import java.util.Objects;

/**
 * @author eddie
 * @createTime 2018-10-10
 * @description 二分搜索树，左子树所有节点比右子树小
 */
public final class BinarySearchTree<E extends Comparable<E>> {

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
    private class Node<E> {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * 根节点
     */
    private Node root;

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
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 该二分搜索树是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 添加一个元素E
     *
     * @param e
     */
    public void add(E e) {
        root = addNode(root, e);
    }

    /**
     * 是否包含一个元素E
     *
     * @param e
     * @return
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 前序遍历 深度遍历方式
     */
    public void perOrder() {
        perOrder(root);
    }

    /**
     * 中序遍历 深度遍历方式
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 后序遍历 深度遍历方式
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 非递归前序遍历 深度遍历方式
     */
    public void perOrderNR() {
        //自己实现的Stack结构 利用底层数组
        EStack<Node> stack = new EStack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            //取出栈顶元素
            Node cur = stack.pop();
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
    }

    /**
     * 层序遍历 广度优先遍历 利用队列 QUEUE
     * 搜索策略
     */
    public void levelOrder(){
        /////////////////
        //      5      //   先把 5 压入队列 弹出5打印 并把5的左右子树压入队列
        //     / \     //   5 <- queue <- 3 6
        //    3   6    //   弹出 3 节点打印 把 3 的左右子树压入队列 同理 6 节点打印 压入左右子树
        //   / \   \   //   3 <- queue <- 6 2 4 && 6 <- queue <- 2 4 8
        //  2   4   8  //   此时队列中 所有节点均为叶子节点 该轮处理完成后 队列为空 结束遍历
        /////////////////
        EQueue<Node> queue = new EQueue<>();
        queue.put(root);
        //队列先进先出原则 先把根节点压入队列 打印 再把左右子树压入队列
        //继续处理左右子树 打印左子树 并把左子树左右子树再压入队列 然后处理右子树
        while (!queue.isEmpty()){
            Node cur = queue.remove();
            System.out.println(cur.e);

            if (!Objects.isNull(cur.left)){
                queue.put(cur.left);
            }
            if (!Objects.isNull(cur.right)){
                queue.put(cur.right);
            }
        }
    }

    /**
     * 需要提前判定"root"节点为NULL情况
     * 向以Node为根节点的二分搜索树中插入元素E 递归算法
     *
     * @param node
     * @param e
     */
    private void add(Node node, E e) {
        if (e.equals(node.e)) {
            //终止条件 不考虑相等的情况
            return;
        }
        if (e.compareTo((E) node.e) < 0 && node.left == null) {
            node.left = new Node(e);
            size++;
            //终止条件
            return;
        }
        if (e.compareTo((E) node.e) > 0 && node.right == null) {
            node.right = new Node(e);
            size++;
            //终止条件
            return;
        }
        if (e.compareTo((E) node.e) < 0) {
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
    private Node addNode(Node node, E e) {
        if (node == null) {
            //先判定如果当前节点为空，那么如果需要添加，那么一定需要new一个节点Node，存放新的元素E
            return new Node(e);
        }
        if (e.compareTo((E) node.e) < 0) {
            //如果 要添加的元素E < 当前节点的元素E 则需要把新的节点挂载在当前元素的左子树上
            node.left = addNode(node.left, e);
        } else if (e.compareTo((E) node.e) > 0) {
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
    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo((E) node.e) == 0) {
            return true;
        } else if (e.compareTo((E) node.e) < 0) {
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
    private void perOrder(Node node) {
        if (node == null) {
            return;
        }
        //do some thing
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
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        //do some thing
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 后序遍历以node为根的二分搜索树 场景：释放内存方式 处理所有子节点 最后处理节点本身
     *
     * @param node
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        //do some thing
        System.out.println(node.e);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        generateBinarySearchTreeResultString(root, 0, result);
        return result.toString();
    }

    private void generateBinarySearchTreeResultString(Node node, int depth, StringBuilder result) {
        if (node == null) {
            result.append(generateDepthString(depth) + "null\n");
            return;
        }

        result.append(generateDepthString(depth) + node.e + "\n");
        generateBinarySearchTreeResultString(node.left, depth + 1, result);
        generateBinarySearchTreeResultString(node.right, depth + 1, result);
    }

    private String generateDepthString(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("-");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            tree.add(num);
        }
        tree.levelOrder();
    }
}

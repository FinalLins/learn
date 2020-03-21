package com.lin.learn.java.structure.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * 自定义简单二叉树
 */
public class MyBinaryTree<E> {

    private Node<E> root;

    public MyBinaryTree(Node<E> root) {
        this.root = root;
    }

    public void pot() {
        System.out.println("-----递归方式前序遍历开始-----");
        preOrderTraverse(root);
        System.out.println();
        System.out.println("-----递归方式前序遍历结束-----");

        System.out.println("-----堆栈方式前序遍历开始-----");
        preOrderTraverse2(root);
        System.out.println();
        System.out.println("-----堆栈方式前序遍历结束-----");
        System.out.println();
    }

    public void mot() {
        System.out.println("-----递归方式中序遍历开始-----");
        midOrderTraverse(root);
        System.out.println();
        System.out.println("-----递归方式中序遍历结束-----");

        System.out.println("-----堆栈方式中序遍历开始-----");
        midOrderTraverse2(root);
        System.out.println();
        System.out.println("-----堆栈方式中序遍历结束-----");
        System.out.println();
    }

    public void postOT() {
        System.out.println("-----递归方式后序遍历开始-----");
        postOrderTraverse(root);
        System.out.println();
        System.out.println("-----递归方式后序遍历结束-----");

        System.out.println("-----堆栈方式后序遍历开始-----");
        postOrderTraverse2(root);
        System.out.println();
        System.out.println("-----堆栈方式后序遍历结束-----");
        System.out.println();
    }

    /**
     * 前序遍历
     *
     * @param node
     */
    public void preOrderTraverse(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.print("->" + node.item);
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public void midOrderTraverse(Node<E> node) {
        if (node == null) {
            return;
        }
        midOrderTraverse(node.leftChild);
        System.out.print("->" + node.item);
        midOrderTraverse(node.rightChild);
    }

    /**
     * 后序遍历
     *
     * @param node
     */
    public void postOrderTraverse(Node<E> node) {
        if (node == null) {
            return;
        }
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print("->" + node.item);
    }

    /**
     * 堆栈方式前序遍历
     *
     * @param node
     */
    public void preOrderTraverse2(Node<E> node) {
        if (node == null) {
            return;
        }

        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node<E> cur = stack.pop();
            System.out.print("->" + cur.item);

            if (cur.rightChild != null) {
                stack.push(cur.rightChild);
            }
            if (cur.leftChild != null) {
                stack.push(cur.leftChild);
            }
        }

//        Node<E> cur = node;
//        while (cur != null || !stack.isEmpty()) {
//            if (cur != null) {
//                System.out.print("->" + cur.item);
//                stack.push(cur);
//                cur = cur.leftChild;
//            } else {
//                cur = stack.pop();
//                cur = cur.rightChild;
//            }
//        }
    }

    public void midOrderTraverse2(Node<E> node) {
        if (node == null) {
            return;
        }

        Stack<Node<E>> stack = new Stack<>();
        Node<E> cur = node;

        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.leftChild;
            } else {
                cur = stack.pop();
                System.out.print("->" + cur.item);
                cur = cur.rightChild;
            }

        }
    }

    public void postOrderTraverse2(Node<E> node) {
        if (node == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        Stack<Node<E>> out = new Stack<>();

        Node<E> cur = node;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
//                System.out.print("->" + cur.item);
                out.push(cur);
                stack.push(cur);
                cur = cur.rightChild;
            } else {
                cur = stack.pop();
                cur = cur.leftChild;
            }
        }

        for (;!out.isEmpty();) {
            System.out.print("->" + out.pop().item);
        }
    }


    public static void test() {

        Node<Integer> node8 = new Node<>(8, null, null);
        Node<Integer> node7 = new Node<>(7, null, null);
        Node<Integer> node6 = new Node<>(6, null, null);
        Node<Integer> node5 = new Node<>(5, null, null);
        Node<Integer> node4 = new Node<>(4, node8, null);
        Node<Integer> node3 = new Node<>(3, node6, node7);
        Node<Integer> node2 = new Node<>(2, node4, node5);
        Node<Integer> node1 = new Node<>(1, node2, node3);

        MyBinaryTree<Integer> binaryTree = new MyBinaryTree<>(node1);
        binaryTree.pot();
        binaryTree.mot();
        binaryTree.postOT();

        //算法思路 ： 怎么把递归改成非递归方式？？？？？

    }

    private static class Node<E> {
        E item;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E item, Node<E> leftChild, Node<E> rightChild) {
            this.item = item;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}

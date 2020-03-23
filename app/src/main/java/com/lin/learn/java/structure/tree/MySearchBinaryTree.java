package com.lin.learn.java.structure.tree;

import java.util.Stack;

/**
 * 自定义二叉树排序树（查找二叉树）
 */
public class MySearchBinaryTree<E extends Comparable<E>> {

    private Node<E> root;

    public void add(E e) {
        Node<E> newNode = new Node<>(e);
        if (root == null) {
            root = newNode;
            return;
        }

        Node<E> parent = null;
        Node<E> node = root;
        while (node != null) {
            parent = node;
            if (node.item.compareTo(e) < 0) {
                node = node.rightChild;
            } else {
                node = node.leftChild;
            }
        }
        if (parent.item.compareTo(e) < 0) {
            parent.rightChild = newNode;
        } else {
            parent.leftChild = newNode;
        }
        newNode.parent = parent;
    }

    public void midTraverse() {
        if (root == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        Node<E> cur = root;
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

    public static void test() {
        int[] arr = {5, 2, 1, 7, 3, 8, 0, 9};
        MySearchBinaryTree<Integer> tree = new MySearchBinaryTree<>();
        for (int i : arr) {
            tree.add(i);
        }
        tree.midTraverse();
    }

    private static class Node<E> {
        E item;
        Node<E> parent;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E item) {
            this(item, null, null);
        }

        public Node(E item, Node<E> leftChild, Node<E> rightChild) {
            this.item = item;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}

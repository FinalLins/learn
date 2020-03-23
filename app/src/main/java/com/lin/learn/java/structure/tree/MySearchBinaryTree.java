package com.lin.learn.java.structure.tree;

import java.util.Stack;

import androidx.annotation.NonNull;

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
            if (e.compareTo(node.item) < 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        if (e.compareTo(parent.item) < 0) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }
        newNode.parent = parent;
    }

    public Node<E> search(E e) {
        if (root == null) {
            return null;
        }
        Node<E> node = root;
        while (node != null) {
            if (e.compareTo(node.item) == 0) {
                return node;
            } else if (e.compareTo(node.item) < 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return null;
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
        System.out.println();
    }

    public static void test() {
        int[] arr = {5, 2, 1, 7, 3, 8, 0, 9};
        MySearchBinaryTree<Integer> tree = new MySearchBinaryTree<>();
        for (int i : arr) {
            tree.add(i);
        }
        tree.midTraverse();

        System.out.println(tree.search(2));
        System.out.println(tree.search(3));
        System.out.println(tree.search(7));
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

        @NonNull
        @Override
        public String toString() {
            return "item : " + item +
                    ",parent : " + parent.item +
                    ",leftChild : " + ((leftChild == null) ? "null" : leftChild.item) +
                    ",rightChild : " + ((rightChild == null) ? "null" : rightChild.item);
        }
    }
}

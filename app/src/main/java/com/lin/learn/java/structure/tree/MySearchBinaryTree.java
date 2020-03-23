package com.lin.learn.java.structure.tree;

import java.util.Stack;

import androidx.annotation.NonNull;

/**
 * 自定义二叉树排序树（查找二叉树）
 */
public class MySearchBinaryTree<E extends Comparable<E>> {

    private Node<E> root;

    public Node<E> add(E e) {
        Node<E> newNode = new Node<>(e);
        if (root == null) {
            root = newNode;
            return newNode;
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
        return newNode;
    }

    public Node<E> remove(E e) {

        Node<E> node = search(e);
        if (node == null) {
            return null;
        }

        Node<E> parent = node.parent, leftChild = node.leftChild, rightChild = node.rightChild;

        if (leftChild != null && rightChild == null) { //1.只有左子树
            if (parent == null) { //node == root
                root = leftChild;
            } else if (node == parent.leftChild) {
                parent.leftChild = leftChild;
            } else if (node == parent.rightChild) {
                parent.rightChild = leftChild;
            }
            leftChild.parent = parent;
        } else if (leftChild == null && rightChild != null) {//2.只有右子树
            if (parent == null) {//node == root
                root = rightChild;
            } else if (parent.leftChild == node) {
                parent.leftChild = rightChild;
            } else if (parent.rightChild == node) {
                parent.rightChild = rightChild;
            }
            rightChild.parent = parent;
        } else if (leftChild != null && rightChild != null) { //3.左右子树都有
            //3.1找到要删除的节点的右子树的最小值代替要删除的节点
            Node<E> minLeftChild = rightChild;
            while (minLeftChild.leftChild != null) {
                minLeftChild = minLeftChild.leftChild;
            }

            // 如果要删除的节点有右子树有左子树的情况，
            // 需要把minLeftChild(要删除的节点的右子树的最小值)的右子树
            // 赋值给需要把minLeftChild.parent.leftChild
            if (rightChild.leftChild != null) {
                Node<E> minParent = minLeftChild.parent;
                minParent.leftChild = minLeftChild.rightChild;
            }

            minLeftChild.leftChild = node.leftChild;
            if (parent == null) { //root == node
                root = minLeftChild;
            } else if (parent.leftChild == node) {
                parent.leftChild = minLeftChild;
            } else if (parent.rightChild == node) {
                parent.rightChild = minLeftChild;
            }
            minLeftChild.parent = parent;
        } else { //4.左右子树都没有
            if (parent == null) {
                root = null;
            } else if (parent.leftChild == node) {
                parent.leftChild = null;
            } else if (parent.rightChild == node) {
                parent.rightChild = null;
            }
        }

        node.parent = null;
        node.leftChild = null;
        node.rightChild = null;
        return node;
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

        tree.remove(2);
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

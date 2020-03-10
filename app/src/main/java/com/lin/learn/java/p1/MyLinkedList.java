package com.lin.learn.java.p1;

import java.util.Objects;

/**
 * 自定义单链表
 */
public class MyLinkedList<E> {

    /**
     * 表头
     */
    private Node<E> head;

    private int size;

    private int capacity;

    public MyLinkedList(int capacity) {
        this.capacity = capacity;
    }

    public void add(E element) {
        if (size >= capacity) {
            return;
        }

        Node<E> node = new Node<>(element, null);
        //1.head == null 说明链表为空
        if (head == null) {
            head = node;
            return;
        }

        //2.否则新节点的指向老的表头，然后成为新的表头
        node.next = head;
        head = node;
        size++;
    }

    public boolean remove(E element) {
        if (size == 0) {
            return false;
        }
        Node<E> parent = head;
        Node<E> node = head;
        while (node != null) {
            if (Objects.equals(element, node.data)) {
                parent.next = node.next;
                size--;
                return true;
            }
            parent = node;
            node = node.next;
        }

        return false;
    }

    public void reverse() {
        if (head == null || size == 1) {
            return;
        }

        Node<E> pre = null, cur = head, next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        head = pre;
    }

    public static <E> Node<E> reverse2(Node<E> node) {
        if (node.next == null) {
            return node;
        }
        Node<E> newNode = reverse2(node.next);
        node.next.next = node;
        node.next = null;
        return newNode;
    }


    public static void display(Node head) {
        if (head == null) {
            return;
        }
        Node node = head;
        do {
            System.out.print(node.data.toString() + ", ");
            node = node.next;
        } while (node != null);
        System.out.println();
    }

    public static void test() {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>(10);

        int[] array = {8, 3, 5, 1, 2};
        for (int i : array) {
            linkedList.add(i);
            display(linkedList.head);
        }

        linkedList.reverse();
        display(linkedList.head);

        linkedList.head = reverse2(linkedList.head);
        display(linkedList.head);

        System.out.println(linkedList.remove(1));
        display(linkedList.head);

        System.out.println(linkedList.remove(10));
        display(linkedList.head);
    }

    public static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}

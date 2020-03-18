package com.lin.learn.java.structure;

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

    public int size () {
        return size;
    }

    public void add(E element) {
        if (size >= capacity) {
            return;
        }

        Node<E> node = new Node<>(element, null);
        //1.head == null 说明链表为空
        if (head == null) {
            head = node;
            size++;
            return;
        }

        //2.否则新节点的指向老的表头，然后成为新的表头
        node.next = head;
        head = node;
        size++;
    }

    public E poll() {
        if (size == 0) {
            return null;
        }
        E e = head.data;
        head = head.next;
        size--;
        return e;
    }

    public boolean remove(E element) {
        if (size == 0) {
            return false;
        }
        Node<E> parent = head;
        Node<E> node = head;
        while (node != null) {
            if (Objects.equals(element, node.data)) {   //找到该节点
                parent.next = node.next;                //把上一个节点和下一个节点连接起来

                node.data = null;
                node.next = null;                       //断开该节点引用关系
                size--;
                return true;
            }
            parent = node;
            node = node.next;
        }

        return false;
    }

    public Node<E> getHead() {
        return head;
    }

    /**
     * 递推的方式翻转链表
     */
    public void reverse() {
        if (head == null || size == 1) {
            return;
        }

        Node<E> prev = null;        //上一个节点
        Node<E> cur = head;         //当前节点
        Node<E> next = null;        //下一个节点
        while (cur != null) {
            next = cur.next;        //记录下一个节点的引用
            cur.next = prev;        //让当前节点的下一个节点指向上一个节点
            prev = cur;             //指针后移
            cur = next;             //指针后移
        }
        head = prev;                //新的链表头
    }

    /**
     * 递归方法翻转链表
     *
     * @param node
     * @param <E>
     * @return
     */
    public static <E> Node<E> reverse2(Node<E> node) {
        if (node.next == null) {                    //递归结束条件
            return node;
        }
        Node<E> newNode = reverse2(node.next);      //递归到最后一个节点
        node.next.next = node;                      //倒数第二个节点的下下个节点指向自己（翻转核心代码）
        node.next = null;                           //断开原有关系
        return newNode;
    }

    public void display() {
        display(head);
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
        public E data;
        public Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}

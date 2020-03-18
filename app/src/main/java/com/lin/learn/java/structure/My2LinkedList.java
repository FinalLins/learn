package com.lin.learn.java.structure;

import java.util.List;
import java.util.Objects;

/**
 * 自定义双向链表
 */
public class My2LinkedList<E> {

    private Node first;
    private Node last;

    private int size;

    public Node indexOf(int index) {
        if (size < 0 || index >= size) {
            return null;
        }

        if (index < (size >> 1)) {
            Node node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }


    /**
     * 从链表头插入数据
     *
     * @param element
     */
    public void addFirst(E element) {
        Node newNode = new Node<>(null, element, first);
        Node f = first;
        if (f == null) {        //如果头为空，代表整个链表为空
            last = newNode;
        } else {
            f.prev = newNode;   //把老的链表头的上一个指针指向新节点
        }
        first = newNode;        //新节点成为新的链表头（链表尾没有任何变化）
        size++;
    }

    /**
     * 从链表尾插入数据
     *
     * @param element
     */
    public void addLast(E element) {
        Node newNode = new Node<>(last, element, null);
        Node l = last;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        last = newNode;
        size++;
    }

    public void remove(E element) {
        //1.先找到这个节点
        //2.找到该节点的prev和next 链接起来
        //3.把该节点释放掉
        Node node = node(element);
        if (node == null) {
            return;
        }

        Node prev = node.prev;
        Node next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

        node.prev = null;
        node.next = null;
        node.data = null;
        size--;

    }

    public void display() {
        display(first);
        display2(last);
    }

    void display(Node node) {
        if (node == null) {
            return;
        }
        Node temp = node;
        while (temp != null) {
            System.out.print(temp.data + ", ");
            temp = temp.next;
        }
        System.out.println();
    }

    void display2(Node node) {
        if (node == null) {
            return;
        }
        Node temp = node;
        while (temp != null) {
            System.out.print(temp.data + ", ");
            temp = temp.prev;
        }
        System.out.println();
    }

    Node node(E element) {
        if (first == null || last == null) {
            return null;
        }
        Node node = first;
        while (node != null) {
            if (Objects.equals(node.data, element)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    public static void test() {
        int[] arr = {3, 5, 2, 8, 7};
        My2LinkedList<Integer> my2LinkedList = new My2LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            my2LinkedList.addFirst(arr[i]);
            my2LinkedList.display();
        }
        my2LinkedList.remove(2);
        my2LinkedList.display();
        my2LinkedList.addLast(10);
        my2LinkedList.display();
    }

    public static class Node<E> {
        E data;
        Node prev;
        Node next;

        public Node(Node prev, E data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}

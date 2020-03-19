package com.lin.learn;

import com.lin.learn.java.BitOperation;
import com.lin.learn.java.structure.My2LinkedList;
import com.lin.learn.java.structure.MyArray;
import com.lin.learn.java.structure.MyHeap;
import com.lin.learn.java.structure.MyLinkedList;
import com.lin.learn.java.Sort;
import com.lin.learn.java.structure.MyStack;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class T {
    /**
     * 位运算
     */
    @Test
    public void bitOperationTest() {
        BitOperation.test();
    }

    /**
     * 简单排序算法
     */
    @Test
    public void sortTest() {
        Sort.test();
    }

    /**
     * 自定义顺序存储结构
     */
    @Test
    public void arrayTest() {
        MyArray.test();
    }

    /**
     * 自定义堆结构
     */
    @Test
    public void heapTest() {
        MyHeap.test();
    }

    /**
     * 单链表
     */
    @Test
    public void singleLinkedListTest() {
        MyLinkedList.test();
    }

    /**
     * 双向链表
     */
    @Test
    public void linkedListTest() {
        My2LinkedList.test();
    }

    /**
     * 栈
     */
    @Test
    public void stackTest() {
        MyStack.test();
    }
}


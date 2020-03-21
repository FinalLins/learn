package com.lin.learn.structure;


import com.lin.learn.java.BitOperation;
import com.lin.learn.java.structure.My2LinkedList;
import com.lin.learn.java.structure.MyArray;
import com.lin.learn.java.structure.MyHeap;
import com.lin.learn.java.structure.MyLinkedList;
import com.lin.learn.java.Sort;
import com.lin.learn.java.structure.MyStack;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import androidx.collection.LruCache;

public class T {
    /**
     * 位运算
     */
    public void bitOperationTest() {
        BitOperation.test();
    }

    /**
     * 简单排序算法
     */
    public void sortTest() {
        Sort.test();
    }

    /**
     * 自定义顺序存储结构
     */
    public void arrayTest() {
        MyArray.test();
    }

    /**
     * 自定义堆结构
     */
    public void heapTest() {
        MyHeap.test();
    }

    /**
     * 单链表
     */
    public void singleLinkedListTest() {
        MyLinkedList.test();
    }

    /**
     * 双向链表
     */
    public void linkedListTest() {
        My2LinkedList.test();
    }

    /**
     * 栈
     */
    public void stackTest() {
        MyStack.test();
    }
}


package com.lin.learn.java.structure;

import java.util.EmptyStackException;

/**
 * 自定义栈结构
 */
public class MyStack {
    private int[] array;
    private int size;
    private int capacity;

    public MyStack(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
    }

    public void push(int e) {
        if (size >= capacity) {
            return;
        }
        array[size++] = e;
    }

    public int pop() {
        if (size <= 0) throw new EmptyStackException();
        int e = array[size - 1];
        size--;
        return e;
    }
}

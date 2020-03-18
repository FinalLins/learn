package com.lin.learn.java.structure;

import java.util.Arrays;

/**
 * 自定义顺序表存储结构
 */
public class MyArray {
    private static final int DEFAULT_CAPACITY = 10;

    private int[] array;
    private int capacity;
    private int size = 0;

    public MyArray(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
    }

    public void add(int element) {
        if (size >= capacity) {
            return;
        }
        array[size++] = element;
    }

    public void remove(int index) {
        if (index >= size) {
            return;
        }

        if (index != size - 1) {
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
        }
        array[size - 1] = -1;
        size--;
    }

    public int modfiy(int index, int newValue) {
        int oldValue = array[index];
        array[index] = newValue;
        return oldValue;
    }

    public int search(int index) {
        return array[index];
    }

    public int searchIndex(int value) {
        for (int i = 0; i < size - 1; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return -1;
    }

    public void display() {
        System.out.println(Arrays.toString(array));
    }

    public static void test() {
        MyArray array = new MyArray(10);
        array.add(2);
        array.add(1);
        array.add(5);
        array.add(9);
        array.display();
        array.remove(2);
        array.display();
    }
}

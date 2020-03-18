package com.lin.learn.java.structure;

import java.util.Arrays;

/**
 * 自定义堆结构（完全二叉树）
 * 父节点(i-1)/2
 * 左子节点2*i+1
 * 右子节点2*i+2
 */
public class MyHeap {
    private int[] array;        //容器
    private int capacity;       //容量
    private int size;           //当前大小

    public MyHeap(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
    }

    public void add(int element) {
        if (size >= capacity) {
            return;
        }
        array[size] = element;
        adjustUp(size++);
    }

    /**
     * 向上调整
     *
     * @param i
     */
    private void adjustUp(int i) {
        int index = i;
        int parent = (index - 1) / 2;
        while (
                index > 0
                        && parent >= 0
                        && array[parent] < array[index]) { //构造的是最大堆
            swap(parent, index);        //交换数据
            index = parent;             //向上递推
            parent = (index - 1) / 2;
        }
    }

    /**
     * 删除堆顶元素
     */
    public void remove() {
        if (size == 0) {
            return;                     //堆里已经没有元素了
        }
        array[0] = array[size - 1];     //删除堆顶元素并且把最后的元素放到堆顶来
        array[size - 1] = 0;
        size--;
        adjustDown(0);               //删除和调整堆顶元素后，堆结构被破坏，重新调整
    }

    /**
     * 递归的方式向下调整堆结构
     *
     * @param i
     */
    private void adjustDown(int i) {
        int index = i;
        int leftChild = 2 * i + 1;
        int rightChild = leftChild + 1;

        if (leftChild < size && array[index] < array[leftChild]) {
            index = leftChild;
        }

        if (rightChild < size && array[index] < array[rightChild]) {
            index = rightChild;
        }

        if (index == i) {
            return;
        }

        swap(index, i);
        adjustDown(index);
    }

    /**
     * 递推方式向下调整堆结构
     *
     * @param i
     */
    private void adjustDown2(int i) {
        int index = i;
        int leftChild, rightChild, max;
        while (index < size) {
            leftChild = 2 * index + 1;
            rightChild = leftChild + 1;
            max = index;
            if (leftChild < size && array[max] < array[leftChild]) {
                max = leftChild;
            }

            if (rightChild < size && array[max] < array[rightChild]) {
                max = rightChild;
            }

            if (max == index) {
                break;
            }

            swap(index, max);
            index = max;
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void display() {
        if (array == null) {
            return;
        }
        System.out.println(Arrays.toString(array));
    }

    public static void test() {
        MyHeap heap = new MyHeap(10);
        int[] array = {2, 5, 8, 1, 9, 3};
        for (int i : array) {
            heap.add(i);
            heap.display();
        }
        heap.remove();
        heap.display();
    }
}

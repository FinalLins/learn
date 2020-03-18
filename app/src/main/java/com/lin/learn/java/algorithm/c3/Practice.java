package com.lin.learn.java.algorithm.c3;

import java.util.Arrays;

public class Practice {
    /**
     * 堆排序
     *
     * @param array
     */
    public static void heapSort(int[] array) {
        int len = array.length;

        System.out.println(Arrays.toString(array));
        //1.构建最小堆
        buildMinHeap(array, len);
        System.out.println(Arrays.toString(array));
        //2.
        for (int i = len - 1; i >= 0; i--) {
            swap(array, i, 0);
            adjustHeap(array, 0, i);
        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * 构建最小堆
     */
    private static void buildMinHeap(int[] array, int len) {
        //找到最后一个非叶子节点
        int last = (len - 1) / 2;
        for (int i = last; i >= 0; i--) {
            adjustHeap(array, i, len);
        }
    }

    private static void adjustHeap(int[] array, int index, int len) {
        int minIndex = -1;

        while (index < len) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            minIndex = index;
            if (leftChild < len && array[leftChild] < array[minIndex]) {
                minIndex = leftChild;
            }

            if (rightChild < len && array[rightChild] < array[minIndex]) {
                minIndex = rightChild;
            }

            if (minIndex == index) {
                break;
            }

            swap(array, minIndex, index);
            index = minIndex;
        }


    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void test() {
        int[] array = {7, 8, 2, 6, 9, 1, 3, 5, 4};
        heapSort(array);
    }
}

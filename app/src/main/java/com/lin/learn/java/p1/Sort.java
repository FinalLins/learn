package com.lin.learn.java.p1;

import java.util.Arrays;

public class Sort {

    private static void printArray(int[] array) {
        if (array == null) {
            System.out.println("数组为空");
            return;
        }
        System.out.println(Arrays.toString(array));
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 冒泡排序
     * 排序逻辑，每次对相邻数据进行比较，一次循环下来，会把最大或者最小的数据排到最后
     */
    public static void bubbleSort(int[] array) {
        int len = array.length;
        boolean flag;
        for (int i = 0; i < len - 1; i++) {
            flag = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    flag = true;
                }
            }

            if (!flag) {
                break;
            }
        }
        printArray(array);
    }

    /**
     * 选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        int len = array.length;
        int min;
        for (int i = 0; i < len - 1; i++) {
            min = i;
            for (int j = i + 1; j < len; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(array, min, i);
            }
        }
        printArray(array);
    }

    /**
     * 快速排序
     *
     * @param array
     */
    public static void quickSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int l = start;
        int r = end;
        int p = array[l];
        while (l < r) {
            while (l < r && p <= array[r]) { //从右边开始比，如果小于就把指针往左边移动
                r--;
            }

            if (l < r) {
                array[l++] = array[r];
            }

            while (l < r && array[l] <= p) { //从左边开始比，如果大于等于就把指针往右边移动
                l++;
            }

            if (l < r) {
                array[r--] = array[l];
            }
        }
        array[l] = p; //l == r
        quickSort(array, start, l - 1);
        quickSort(array, l + 1, end);
        //end----两次递归，根据顺序的意思就是DLR，二叉树的前序遍历
    }

    public static void mergeSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) >> 1; //等同 (start + end) / 2
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);

//        //合并
        int[] tempArr = Arrays.copyOfRange(array, start, end + 1);
        int len = tempArr.length;
        int l = start;
        int r = mid + 1;
        for (int i = 0; i < len; i++) {
            if (l > mid) { //左边的数组合并完成
                array[i + start] = tempArr[r - start];
                r++;
            } else if (r > end) {
                array[i + start] = tempArr[l - start];
                l++;
            } else if (tempArr[l - start] <= tempArr[r - start]) {
                array[i + start] = tempArr[l - start];
                l++;
            } else {
                array[i + start] = tempArr[r - start];
                r++;
            }
        }
        //end----两次递归，根据顺序的意思就是LRD，二叉树的后序遍历
    }

    public static void insertSort(int[] array) {
        insertSort(array, 1);
        printArray(array);
    }

    /**
     * 插入排序
     *
     * @param array
     * @param gap
     */
    public static void insertSort(int[] array, int gap) {
        int len = array.length;
        for (int i = gap; i < len; i++) {
            int j = i - gap;
            int temp = array[i];
            while (j >= 0 && array[j] > temp) {
                array[j + gap] = array[j];
                j -= gap;
            }
            array[j + gap] = temp;
        }
    }

    public static void shellSort(int[] array) {
        int len = array.length;
        for (int gap = len >> 1; gap >= 1; gap >>= 1) {
            insertSort(array, gap);
        }
        printArray(array);
    }

    public static void test() {
        int[] array = {30, 7, 15, 6, 8, 0, 19, 3, 9, 4, 11, 21, 22, 24};
        int len = array.length;
        bubbleSort(Arrays.copyOf(array, len));
        selectSort(Arrays.copyOf(array, len));
        insertSort(Arrays.copyOf(array, len), 1);
        shellSort(Arrays.copyOf(array, len));

        int[] quickArray = Arrays.copyOf(array, len);
        quickSort(quickArray, 0, len - 1);
        printArray(quickArray);

        int[] mergeArray = Arrays.copyOf(array, len);
        mergeSort(mergeArray, 0, len - 1);
        printArray(mergeArray);


    }
}

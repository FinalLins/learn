package com.lin.learn.java.algorithm.c2;

import java.util.Arrays;

/**
 * 分治法练习
 * 有 n = 2^k 个选手参加比赛，要求设计一个满足以下要求的日程表：
 * 1.每个选手必须与其他的 n-1 个选手比赛一次
 * 2.每个选手每天只能比赛一次
 */
public class Practice {

    public static void test() {
        int[][] arr = table(3);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    /**
     * @param k 2^k个选手
     * @return 日程表
     */
    public static int[][] table(int k) {
        if (k == 0) {
            return new int[][]{
                    {1}
            };
        }
        int n = 1 << k;                 //算出人数(2^k)
        int[][] arr = new int[n][n];    //创建日程表

        //1.先填充好第一天的数据
        for (int i = 0; i < n; i++) {
            arr[0][i] = i + 1;
        }

        //2.使用分治法进行数据填写,r:循环次数
        for (int r = 1; r < n; r *= 2) {
            for (int i = 0; i < n; i += r * 2) {
                copy(arr,
                        0, i,
                        r, r + i,
                        r);             //左上拷贝到右下
                copy(arr,
                        0, r + i,
                        r, i,
                        r);             //右上拷贝到左下
            }
        }

        return arr;
    }

    /**
     * 拷贝二维数组中的一块区域
     *
     * @param arr   原数组
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param r     表示对角线上的元素个数
     */
    public static void copy(int[][] arr, int fromX, int fromY, int toX, int toY, int r) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                arr[toX + i][toY + j] = arr[fromX + i][fromY + j];
            }
        }
    }
}

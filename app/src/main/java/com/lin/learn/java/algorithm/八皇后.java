package com.lin.learn.java.algorithm;

import java.util.Arrays;
import java.util.Stack;

public class 八皇后 {
    /**
     * 用来表示皇后在第几列
     */
    private static int[] queenLocations = new int[8];

    public static void eightQueens() {
        eightQueens(0);
    }

    public static void eightQueens(int row) {
        if (row == 8) {
            System.out.println(Arrays.toString(queenLocations));
            return;
        }

        for (int col = 0; col < 8; col++) {
            queenLocations[row] = col;
            if (judge(row)) {
                eightQueens(row + 1);
            }
        }
    }




    public static boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            //1.判断是否在同一列
            if (queenLocations[i] == queenLocations[n]) {
                return false;
            }
            //2.判断是否在左上到右下的斜线（相减）
            if (Math.abs(n - i) == Math.abs(queenLocations[n] - queenLocations[i])) {
                return false;
            }
        }
        return true;
    }
}

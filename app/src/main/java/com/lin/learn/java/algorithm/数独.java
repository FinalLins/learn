package com.lin.learn.java.algorithm;

import com.lin.learn.java.ArrayUtils;

public class 数独 {
//    public static int[][] result = {
//            {0, 0, 8, 3, 0, 9, 1, 0, 0},
//            {9, 0, 0, 0, 6, 0, 0, 0, 4},
//            {0, 0, 7, 5, 0, 4, 8, 0, 0},
//            {0, 3, 6, 0, 0, 0, 5, 4, 0},
//            {0, 0, 1, 0, 0, 0, 6, 0, 0},
//            {0, 4, 2, 0, 0, 0, 9, 7, 0},
//            {0, 0, 5, 9, 0, 7, 3, 0, 0},
//            {6, 0, 0, 0, 1, 0, 0, 0, 8},
//            {0, 0, 4, 6, 0, 8, 2, 0, 0,}
//    };

    public static int[][] result = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };

    public static void sudoku() {
        sudoku(0, 0);
    }

    public static void sudoku(int i, int j) {
        if (i == 8 && j == 9) {
            ArrayUtils.display(result);
            return;
        }

        if (j == 9) {
            i++;
            j = 0;
        }

        if (result[i][j] == 0) { //空格子
            for (int k = 1; k <= 9; k++) {
                if (judge(i, j, k)) {
                    result[i][j] = k;
                    sudoku(i, j + 1); //往后移动一格
                    //让前一次的格子还原
                    result[i][j] = 0;
                }
            }
        } else {
            sudoku(i, j + 1); //往后移动一格
        }
    }

    public static boolean judge(int row, int col, int number) {
        //判断行和列是否出现了这个值number
        for (int i = 0; i < 9; i++) {
            if (result[row][i] == number || result[i][col] == number) {
                return false;
            }
        }
        //判断宫里面是否出现了这个值
        //获取所在宫的起点
        int tempRow = row / 3;
        int tempCol = col / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (result[tempRow * 3 + i][tempCol * 3 + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }
}

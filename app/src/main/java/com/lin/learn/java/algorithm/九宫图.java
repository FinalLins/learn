package com.lin.learn.java.algorithm;

import com.lin.learn.java.ArrayUtils;

public class 九宫图 {

    public static void squaredUp(int n) {
        if (n <= 0 || (n & 1) == 0) {
            System.out.println("只支持大于0的奇数");
            return;
        }
        int[][] s = new int[n][n];

        int x = 1;//需要填入的数字
        int row = 0, col = n >> 1;
        int tempRow, tempCol;
        s[row][col] = x;
        while (x < n * n) {
            tempRow = row;
            tempCol = col;
            //往右上走;
            row--;
            if (row < 0) row = n - 1;
            col++;
            if (col == n) col = 0;
            x++; //需要填入的数字

            if (s[row][col] != 0) { //如果已经填入了数字，回溯到上一步的位置的下面
                row = tempRow + 1;
                col = tempCol;
            }

            System.out.println(x);

            s[row][col] = x;
        }
        ArrayUtils.display(s);
    }
}

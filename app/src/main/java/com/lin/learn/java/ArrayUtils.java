package com.lin.learn.java;

public class ArrayUtils {

    public static void display(int[][] arrays) {
        int len1 = arrays.length;
        for (int i = 0; i < len1; i++) {
            int len2 = arrays[i].length;
            for (int j = 0; j < len2; j++) {
                System.out.print(" " + arrays[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }
}

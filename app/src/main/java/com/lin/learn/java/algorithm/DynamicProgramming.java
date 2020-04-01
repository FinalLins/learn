package com.lin.learn.java.algorithm;

import java.util.Stack;

/**
 * 动态规划
 */
public class DynamicProgramming {

    /**
     * 最长公共子序列算法Longest Common Subsequence
     * 求两个字符串最长子序列（可以有多解）
     * 字符串X m是X的个数
     * 字符串Y n是Y的个数
     * 解Z
     * LCS(Xm,Yn) = {
     * 1. m == n
     * LCS(m-1,n-1) + 1
     * 2. m != n
     * max(LCS(m-1,n), LCS(m,n-1)
     * }
     */
    public static void LCS(String x, String y) {
        char[] s1 = x.toCharArray();
        char[] s2 = y.toCharArray();

        int len1 = s1.length + 1;
        int len2 = s2.length + 1;
        int[][] graph = new int[len1][len2];
        //1.把第一行赋值为0
        for (int i = 0; i < len2; i++) {
            graph[0][i] = 0;
        }
        //2.把第一列赋值为0
        for (int i = 0; i < len1; i++) {
            graph[i][0] = 0;
        }
        //3.遍历二维数组，比较字符，相同取左上角+1，不同取左和上最大的
        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    graph[i][j] = graph[i - 1][j - 1] + 1;
                } else {
                    graph[i][j] = Math.max(graph[i - 1][j], graph[i][j - 1]);
                }
            }
        }
        display(graph);
        //4.从最后的结果反向推导出结果
        Stack<Character> stack = new Stack<>();
        int m = len1 - 1;
        int n = len2 - 1;
        while (m >= 1 && n >= 1) {
            if (s1[m - 1] == s2[n - 1]) { //左上角
                stack.push(s1[m - 1]);
                m--;
                n--;
            } else {
                if (graph[m - 1][n] < graph[m][n - 1]) {
                    n--;
                } else {
                    m--;
                }
            }
        }
        //5.打印结果
        while (!stack.isEmpty()) System.out.print(" " + stack.pop());
    }

    private static void display(int[][] arrays) {
        int len1 = arrays.length;
        for (int i = 0; i < len1; i++) {
            int len2 = arrays[i].length;
            for (int j = 0; j < len2; j++) {
                System.out.print(" " + arrays[i][j]);
            }
            System.out.println();
        }
    }
}

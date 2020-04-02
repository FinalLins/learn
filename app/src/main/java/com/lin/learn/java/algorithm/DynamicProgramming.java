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

        System.out.println();
        float t = (float) graph[len1 - 1][len2 - 1] / Math.max(len1, len2);
        System.out.println("相似度 : " + t);
    }

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

    /**
     * 精准匹配
     *
     * @param src  目标串
     * @param dest 模式串
     */
    public static void KMP(String src, String dest) {
        int[] next = KMP_NEXT(dest);
        int sLen = src.length();
        int dLen = dest.length();
        boolean match = false;
        int i = 0, j = 0;
        for (; i < sLen; i++) {
            char ci = src.charAt(i);
            char cj = dest.charAt(j);

            while (j > 0 && ci != cj) j = next[j - 1];

            if (ci == cj) j++;

            if (j == dLen) {
                match = true;
                break;
            }
        }
        if (match) {
            System.out.println("精准匹配 src : " + (i - j + 1));
        } else {
            System.out.println("未匹配");
        }
    }

    /**
     * next数组主要是找到头尾最长公共子序列的长度(一定是从尾部开始)【最大前缀后缀公共元素长度】
     * ababcaba
     * a            0
     * ab           0
     * aba          1(头:a 尾:a 长度1相同)
     * abab         2(头:ab 尾:ab 长度2)
     * ababc        0
     * ababca       1
     * ababcab      2
     * ababcaba     3
     *
     * @param dest
     * @return
     */
    private static int[] KMP_NEXT(String dest) {
        int len = dest.length();
        int[] next = new int[len];
        for (int i = 1, j = 0; i < len; i++) {
            char ci = dest.charAt(i);
            char cj = dest.charAt(j);

            while (j > 0 && ci != cj) j = next[j - 1];
            if (ci == cj) j++;
            next[i] = j;
        }
        return next;
    }


    /**
     * 弗洛伊德算法，计算两个点之间的最短距离（时间复杂度O(n) = n ^ 3）
     */
    public static void floyd(int[][] graph) {
        int size = graph.length;

        int[][] path = getFloydPath(size);

        for (int k = 0; k < size; k++) { //锁死k行和k列
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (graph[i][j] > graph[i][k] + graph[k][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                        //记录路径变化
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
        System.out.println("使用弗洛伊德算法后的路径长度 : ");
        display(graph);
        display(path);
        displayFloydPath(path, graph);

    }

    private static void displayFloydPath(int[][] path, int[][] graph) {
        int size = path.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("V" + i + "->V" + j);
                System.out.print(" (V" + i);
                int k = path[i][j];
                while (j != k) {
                    System.out.print("->V" + k);
                    k = path[k][j];
                }
                System.out.println("->V" + j + ") = " + graph[i][j]);
            }
        }
    }

    private static int[][] getFloydPath(int size) {
        int[][] path = new int[size][size];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = j;
            }
        }
        return path;
    }
}

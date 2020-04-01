package com.lin.learn.java.structure.graph;

import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;

/**
 * 最小生成树kruskal(克鲁斯卡尔)算法
 * 1.现将所有边进行权值的从小到大排序
 * 2.定义一个一维数组代表连接过的边，数组的下标为边的起点，值为边的终点
 * 3.按照排好序的集合用边对顶点进行依次连接，连接的边则存放到一维数组中
 * 4.用一维数组判断是否对已经连接的边能构成回路，有回路则无效，没回路则是一条有效边
 * 5.重复3，4直至遍历完所有的边为止，即找到最小生成树
 */
public class MyGraphKruskal {

    private int[] vertices;     //顶点
    private int size;           //顶点个数
    private int[][] matrix;     //邻接矩阵

    private Edge[] edges;       //所有的边集合
    private int edgeSize;       //边的个数

    public MyGraphKruskal(int size) {
        this.size = size;
        this.vertices = new int[size];
        this.matrix = new int[size][size];
    }

    public MyGraphKruskal(int[][] m) {
        this.size = m.length;
        this.vertices = new int[size];
        this.matrix = m;
        for (int i = 0; i < size; i++) {
            this.vertices[i] = i;
        }
    }

    /**
     * 生成所有的边
     */
    public void generatorEdge() {
        edges = new Edge[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int v = matrix[i][j];
                if (v > 0) {
                    edges[edgeSize++] = new Edge(i, j, v);
                }
            }
        }
    }

    public void kruskal() {
        //1.现将所有边进行权值的从小到大排序
        quickSort(edges, 0, edgeSize - 1);
        //2.定义一个一维数组代表连接过的边，数组的下标为边的起点，值为边的终点
        int[] edgeTemp = new int[size];
        Edge[] result = new Edge[size - 1];
        int index = 0;

        //3.按照排好序的集合用边对顶点进行依次连接，连接的边则存放到一维数组中
        for (int i = 0; i < edgeSize; i++) {
            Edge edge = edges[i];
            int start = edge.start;
            int end = edge.end;

            //4.用一维数组判断是否对已经连接的边能构成回路，有回路则无效，没回路则是一条有效边
            int m = getEnd(edgeTemp, start); //从start开始，找到最大的那个节点
            int n = getEnd(edgeTemp, end);
            if (m == n) continue; //如果相等说明已经连通了
            if (m > n) {
                edgeTemp[n] = m;
            } else {
                edgeTemp[m] = n;
            }
            result[index++] = edge;
        }

        int min = 0;
        for (Edge edge : result) {
            System.out.println(edge.toString());
            min+= edge.widget;
        }
        System.out.println("min : " + min);
    }

    private void quickSort(Edge[] array, int start, int end) {
        if (start >= end) return;

        int left = start;
        int right = end;
        Edge temp = array[left];

        while (left < right) {
            while (array[right].compareTo(temp) > 0) {
                right--;
            }
            if (left < right) array[left++] = array[right];
            while (array[left].compareTo(temp) < 0) {
                left++;
            }
            if (left < right) array[right--] = array[left];
        }
        array[left] = temp;
        quickSort(array, start, left - 1);
        quickSort(array, left + 1, end);
    }

    private int getEnd(int[] edge, int v) {
        int p = v;
        while (edge[p] != 0) {
            p = edge[p];
        }
        return p;
    }

    public static class Edge implements Comparable<Edge> {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        public int start;
        public int end;
        public int widget;

        public Edge(int start, int end, int widget) {
            this.start = start;
            this.end = end;
            this.widget = widget;
        }

        @Override
        public int compareTo(Edge o) {
            if (widget < o.widget) return -1;
            else if (widget > o.widget) return 1;
            return 0;
        }

        @NonNull
        @Override
        public String toString() {
            return chars[start] + "->" + chars[end] + "=" + widget;
        }
    }

    public static void test() {
        int[][] matrix3 = {
                {0, 50, 60, -1, -1, -1, -1},
                {50, 0, -1, 65, 40, -1, -1},
                {60, -1, 0, 52, -1, -1, 45},
                {-1, 65, 52, 0, 50, 30, 42},
                {-1, 40, -1, 50, 0, 70, -1},
                {-1, -1, -1, 30, 70, 0, -1},
                {-1, -1, 45, 42, -1, -1, 0}
        };

        MyGraphKruskal kruskal = new MyGraphKruskal(matrix3);
        kruskal.generatorEdge();
        kruskal.kruskal();
    }
}

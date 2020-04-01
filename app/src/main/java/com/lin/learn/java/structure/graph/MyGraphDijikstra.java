package com.lin.learn.java.structure.graph;

import java.util.Arrays;

/**
 * dijikstra迪杰斯特拉算法
 * 求某个顶点到其他顶点的最小路径
 */
public class MyGraphDijikstra {
    private int[] vertices;
    private int size;
    private int[][] matrix;


    public MyGraphDijikstra(int[][] m) {
        this.size = m.length;
        this.vertices = new int[size];
        this.matrix = m;
        for (int i = 0; i < size; i++) {
            this.vertices[i] = i;
        }
    }

    public void dijikstra(int start) {
        int[] S = new int[size];            //存已经查找过的节点
        int[] distances = matrix[start];

        S[start] = 1;
        for (int i = 0; i < size; i++) {

            //1.找到当前最短路径的节点，当做媒介
            int min = Integer.MAX_VALUE;
            int u = -1;
            for (int j = 0; j < size; j++) {
                int distance = distances[j];
                //j == start
                if (distance == 0) continue;
                //代表start->j是不可达
                if (distance == -1) continue;
                //代表j已经被查找过
                if (S[j] == 1) continue;

                if (distance < min) {
                    min = distance;
                    u = j;
                }
            }

            if (u == -1) break;
            //用媒介节点u更新distances
            S[u] = 1;
            for (int j = 0; j < size; j++) {
                //代表j已经被查找过
                if (S[j] == 1) continue;
                if (distances[j] == 0) continue;
                if (matrix[u][j] == -1) continue;

                if (distances[j] == -1 || distances[u] + matrix[u][j] < distances[j]) {
                    distances[j] = distances[u] + matrix[u][j];
                }
            }
            System.out.println(Arrays.toString(distances));
        }

        for (int i = 0; i < distances.length; i++) {
            System.out.println(i + "->" + distances[i]);
        }
    }

    public static void test() {
        int[][] matrix = {
                {0, -1, 5, 30, -1, -1},
                {2, 0, -1, -1, -1, 8},
                {-1, 15, 0, -1, 7, -1},
                {-1, -1, -1, 0, -1, -1},
                {-1, -1, -1, -1, 0, 18},
                {-1, -1, -1, 4, -1, 0}
        };
        MyGraphDijikstra dijikstra = new MyGraphDijikstra(matrix);
        dijikstra.dijikstra(0);
    }
}

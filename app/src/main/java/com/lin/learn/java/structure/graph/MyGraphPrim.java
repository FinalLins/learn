package com.lin.learn.java.structure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 最小生成树prim(普里姆)算法
 * 1.定义一个临时的一维数组，用于存放可用的连接边，数组下标为顶点序号，值为权值
 * 2.任选一个点作为起点，以起点的所有权值对数组进行初始化
 * 3.找出数组中最小权值的边，即为最小生成树中的一条有效边
 * 4.将找到的最小边在数组中赋值为0，代表已经使用过。并将数组与找到顶点的所有边进行比较，
 * 若顶点的边的权值比当前数组存放的可用边的权值小，则进行覆盖
 * 5.重复循环2，3，4的操作直至遍历完所有顶点
 */
public class MyGraphPrim {
    private int[] vertices;
    private int size;
    private int[][] matrix;

    public MyGraphPrim(int[][] matrix) {
        this.size = matrix.length;
        this.vertices = new int[this.size];
        this.matrix = matrix;

        for (int i = 0; i < size; i++) {
            this.vertices[i] = i;
        }
    }

    private void prim0() {
        List<Integer> V = new ArrayList<>(size);
        for (int vertex : vertices) {
            V.add(vertex);
        }
        List<Integer> U = new ArrayList<>(size);
        List<int[]> T = new ArrayList<>(size);
        //1.先从V里面取一个顶点放到U中
        U.add(V.remove(0));

        //2.然后遍历U，找到U和V最小边的顶点
        //3.把这个顶点加到U中。重复2
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int widget = 0;
        int WEIGHT_INACCESSIBLE = -1;
        while (!V.isEmpty()) {
            int minWeight = WEIGHT_INACCESSIBLE;
            int minU = WEIGHT_INACCESSIBLE, minV = WEIGHT_INACCESSIBLE;
            int vIndex = 0;
            for (int i = 0; i < U.size(); i++) {
                int U_V = U.get(i);
                for (int j = 0; j < V.size(); j++) {
                    int V_V = V.get(j);
                    int w = getWidget(U_V, V_V);
                    if (w == 0 || w == WEIGHT_INACCESSIBLE) continue;

                    if (minWeight == WEIGHT_INACCESSIBLE || w < minWeight) {
                        minU = U_V;
                        minV = V_V;
                        minWeight = w;
                        vIndex = j;
                    }
                }
            }
            widget += minWeight;
            int[] t = new int[2];
            t[0] = minU;
            t[1] = minV;
//            System.out.println("->" + chars[t[0]] + chars[t[1]]);
//            System.out.println("->" + t[0] + t[1]);
            T.add(t);
            U.add(V.remove(vIndex));
        }


        System.out.println(Arrays.toString(U.toArray()));
        for (Integer i : U) {
            System.out.print("->" + chars[i]);
        }
        System.out.println();

        for (int[] ints : T) {
            System.out.print("->" + chars[ints[0]] + chars[ints[1]]);
        }
        System.out.println();
        System.out.println("最短路径 : " + widget);
//        System.out.println(Arrays.toString(T));

    }

    /**
     * 返回v1到v2的权重（路径长度）
     *
     * @param v1
     * @param v2
     * @return
     */
    public int getWidget(int v1, int v2) {
        if (v1 < 0 || v1 >= size) {
            return -1;
        }

        if (v2 < 0 || v2 >= size) {
            return -1;
        }

        return matrix[v1][v2];
    }

    public static void test() {
        //A B C D E F G
        int[][] matrix3 = {
                {0, 50, 60, -1, -1, -1, -1},
                {50, 0, -1, 65, 40, -1, -1},
                {60, -1, 0, 52, -1, -1, 45},
                {-1, 65, 52, 0, 50, 30, 42},
                {-1, 40, -1, 50, 0, 70, -1},
                {-1, -1, -1, 30, 70, 0, -1},
                {-1, -1, 45, 42, -1, -1, 0}
        };

        MyGraphPrim graph3 = new MyGraphPrim(matrix3);
        graph3.prim0();
        graph3.prim1();
        graph3.prim1();
    }

    private void prim1() {
        prim1(0);
    }

    /**
     * 算法残留问题，非0索引
     *
     * @param index
     */
    private void prim1(int index) {
        //1.定义一个临时的一维数组，用于存放可用的连接边，数组下标为顶点序号，值为权值
        //2.任选一个点作为起点，以起点的所有权值对数组进行初始化
        int[] lowcost = matrix[index];

        //                {0, 50, 60, -1, -1, -1, -1},
        //                {50, 0, -1, 65, 40, -1, -1},
        //                {60, -1, 0, 52, -1, -1, 45},
        //                {-1, 65, 52, 0, 50, 30, 42},
        //                {-1, 40, -1, 50, 0, 70, -1},
        //                {-1, -1, -1, 30, 70, 0, -1},
        //                {-1, -1, 45, 42, -1, -1, 0}

        int min;
        int minIndex = 0;
        int ret = 0;
        for (int i = 0; i < size; i++) {
            min = Integer.MAX_VALUE;
            //找到lowcost中最小的索引
            for (int a = 1; a < size; a++) {
                if (lowcost[a] > 0 && lowcost[a] < min) {
                    min = lowcost[a];
                    minIndex = a;
                }
            }

            if (min == Integer.MAX_VALUE) break;
            ret += min;
            lowcost[minIndex] = 0;
            //合并index 和 minIndex
            for (int b = 1; b < size; b++) {
                if (lowcost[b] == 0) continue;
                if (matrix[minIndex][b] == -1) continue;
                if (lowcost[b] == -1 || matrix[minIndex][b] < lowcost[b])
                    lowcost[b] = matrix[minIndex][b];
            }
        }
        System.out.println();
        System.out.println("最小生成树：" + ret);
    }
}

package com.lin.learn.java.structure.graph;

/**
 * 自定义图，这里使用邻接矩阵实现
 * 顶点：一维数组
 * 边：二维数组(n*n)
 */
public class MyGraph {
    private int[] vertices;         //顶点
    private int verticesSize;       //顶点数量
    private int[][] matrix;         //边

    /**
     * 表示路径不可达或者没有边
     */
    private static final int WEIGHT_INACCESSIBLE = -1;
    private static final int INDEX_INACCESSIBLE = -1;

    public MyGraph(int size) {
        this.verticesSize = size;
        this.vertices = new int[size];
        this.matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            this.vertices[i] = i;
        }
    }

    /**
     * 返回v1到v2的权重（路径长度）
     *
     * @param v1
     * @param v2
     * @return
     */
    public int getWidget(int v1, int v2) {
        if (v1 < 0 || v1 >= verticesSize) {
            return WEIGHT_INACCESSIBLE;
        }

        if (v2 < 0 || v2 >= verticesSize) {
            return WEIGHT_INACCESSIBLE;
        }

        return matrix[v1][v2];
    }

    /**
     * 获取某个顶点的出度
     *
     * @return
     */
    public int getOutDegree(int v) {
        int degree = 0;
        for (int i = 0; i < verticesSize; i++) {
            int w = matrix[v][i];
            if (w != 0 && w != WEIGHT_INACCESSIBLE) {
//                degree += w;
                degree++;
            }
        }
        return degree;
    }

    /**
     * 计算某个顶点的入度
     *
     * @param v
     * @return
     */
    public int getInDegree(int v) {
        int degree = 0;
        for (int i = 0; i < verticesSize; i++) {
            int w = matrix[i][v];
            if (w != 0 && w != WEIGHT_INACCESSIBLE) {
//                degree += w;
                degree++;
            }
        }
        return degree;
    }

    public int getFirstNeightBor(int v) {
        return getNeightbor(v, 0);
    }

    /**
     * 返回顶点的第一个有效邻接点
     *
     * @param v
     * @return
     */
    public int getNeightbor(int v, int index) {
        if (v < 0 || v >= verticesSize) {
            return INDEX_INACCESSIBLE;
        }
        int count = 0;
        for (int i = 0; i < verticesSize; i++) {
            int w = matrix[v][i];
            if (w != 0 && w != WEIGHT_INACCESSIBLE && index == count++) {
                return i;
            }
        }
        return INDEX_INACCESSIBLE;
    }

    public static void test() {

        int[][] matrix = {
                {0, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 6},
                {9, 0, 3, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE},
                {2, WEIGHT_INACCESSIBLE, 0, 5, WEIGHT_INACCESSIBLE},
                {WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 0, 1},
                {WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 0}
        };
        MyGraph graph = new MyGraph(matrix.length);
        graph.matrix = matrix;
        System.out.println(graph.getInDegree(2));
        System.out.println(graph.getOutDegree(2));
        System.out.println(graph.getFirstNeightBor(2));
        System.out.println(graph.getNeightbor(2, 1));

    }
}

package com.lin.learn.java.structure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 自定义图，这里使用邻接矩阵实现
 * 顶点：一维数组
 * 边：二维数组(n*n)
 */
public class MyGraph {
    private int[] vertices;         //顶点
    private int verticesSize;       //顶点数量
    private int[][] matrix;         //边

    private boolean[] visited;

    /**
     * 表示路径不可达或者没有边
     */
    private static final int WEIGHT_INACCESSIBLE = -1;
    private static final int INDEX_INACCESSIBLE = -1;

    public MyGraph(int size) {
        this.verticesSize = size;
        this.vertices = new int[size];
        this.matrix = new int[size][size];
        this.visited = new boolean[size];

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

    /**
     * 返回顶点的第一个有效邻接点
     *
     * @param v
     * @return
     */
    public int getFirstNeightBor(int v) {
        return getNeightBor(v, 0);
    }

    public int getNextNeighBor(int v, int index) {
        if (v < 0 || v >= verticesSize) {
            return INDEX_INACCESSIBLE;
        }

        for (int i = index + 1; i < verticesSize; i++) {
            int w = matrix[v][i];
            if (w != 0 && w != WEIGHT_INACCESSIBLE) {
                return i;
            }
        }
        return INDEX_INACCESSIBLE;
    }

    /**
     * @param v
     * @return
     */
    public int getNeightBor(int v, int index) {
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

    private void clearVisited() {
        for (int i = 0; i < verticesSize; i++) {
            visited[i] = false;
        }
    }

    public void dfs() {
        clearVisited();
        for (int i = 1; i < verticesSize + 1; i++) {
            int index = i % verticesSize;
            if (visited[index]) continue;
//            System.out.println("index : " + index);
            dfsOne(index);
        }
        System.out.println();
    }

    /**
     * 深度优先遍历Depth First Search
     */
    public void dfs2() {
        clearVisited();
        for (int i = 1; i < verticesSize + 1; i++) {
            int index = i % verticesSize;
            if (visited[index]) continue;
//            System.out.println("index : " + index);
            dfsOne2(index);
        }
        System.out.println();
    }

    public void dfs3() {
        clearVisited();
        for (int i = 1; i < verticesSize + 1; i++) {
            int index = i % verticesSize;
            if (visited[index]) continue;
//            System.out.println("index : " + index);
            dfsOne3(index);
        }
        System.out.println();
    }

    private void dfsOne(int i) {
        visited[i] = true;
        System.out.print("->" + i);
        Stack<DFS> stack = new Stack<>();
        stack.push(new DFS(i, getFirstNeightBor(i)));
        while (!stack.isEmpty()) {
            DFS dfs = stack.peek();
            int v = dfs.v;
            int index = dfs.index;
            if (index == INDEX_INACCESSIBLE) {
                stack.clear();
                break;
            }

            if (!visited[index]) {
                visited[index] = true;
                System.out.print("->" + index);
                dfs.index = getNextNeighBor(v, index);
                v = index;
                index = getFirstNeightBor(index);
                stack.push(new DFS(v, index));
            } else {
                stack.pop();
            }
        }
    }

    private void dfsOne2(int i) {
        visited[i] = true;
        System.out.print("->" + i);
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(i, getFirstNeightBor(i));
        stack.push(i);
        while (!stack.isEmpty()) {

            int v = stack.peek();
            Integer index = map.get(v);
            if (index == null || index == INDEX_INACCESSIBLE) {
                stack.clear();
                break;
            }

            if (!visited[index]) {
                visited[index] = true;
                System.out.print("->" + index);
                map.put(v, getNextNeighBor(v, index));
                v = index;
                index = getFirstNeightBor(index);
                map.put(v, index);
                stack.push(v);
            } else {
                stack.pop();
            }
        }
    }

    public void dfsOne3(int i) {
        visited[i] = true;
        System.out.print("->" + i);
        Stack<Integer> stack = new Stack<>();
        stack.push(i);
        while (!stack.isEmpty()) {
            int v = getEffective(stack.peek());
            if (v == -1) {
                stack.pop();
            } else {
                visited[v] = true;
                System.out.print("->" + v);
                stack.push(v);
            }
        }

    }

    private int getEffective(int v) {
        if (v < 0 || v >= verticesSize) {
            return INDEX_INACCESSIBLE;
        }

        for (int i = 0; i < verticesSize; i++) {
            int w = matrix[v][i];
            if (w > 0 && !visited[i]) {
                return i;
            }
        }
        return INDEX_INACCESSIBLE;
    }

    private static class DFS {
        int v;
        int index;

        public DFS(int v, int index) {
            this.v = v;
            this.index = index;
        }
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
        System.out.println(graph.getNeightBor(2, 1));
        graph.dfs();

        int[][] matrix2 = {
                {0, 1, 1, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE},
                {WEIGHT_INACCESSIBLE, 0, WEIGHT_INACCESSIBLE, 1, WEIGHT_INACCESSIBLE},
                {WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 0, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE},
                {1, WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 0, WEIGHT_INACCESSIBLE},
                {WEIGHT_INACCESSIBLE, WEIGHT_INACCESSIBLE, 1, WEIGHT_INACCESSIBLE, 0},
        };

        MyGraph graph2 = new MyGraph(matrix2.length);
        graph2.matrix = matrix2;
        graph2.dfs();
        graph2.dfs2();
        graph2.dfs3();

    }

}

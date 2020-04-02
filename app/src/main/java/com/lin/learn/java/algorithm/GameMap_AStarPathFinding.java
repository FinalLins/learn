package com.lin.learn.java.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图A*自动寻路算法-入门版本
 */
public class GameMap_AStarPathFinding {
    private static int[][] map = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3},
            {3, 3, 0, 0, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 3, 0, 3, 0, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 3, 3, 0, 0, 0},
            {3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3},
            {3, 0, 0, 3, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3},
            {3, 3, 0, 3, 0, 0, 3, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3},
            {3, 3, 0, 3, 3, 3, 3, 3, 0, 3, 3, 0, 2, 3, 3, 3, 3},
            {3, 3, 0, 0, 0, 3, 0, 3, 0, 3, 3, 3, 0, 0, 0, 0, 3},
            {3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
    };

    private static List<P> openArray = new ArrayList<>();
    private static List<P> closeArray = new ArrayList<>();
    private static P startPoint;
    private static P endPoint;

    private static int pointToPoint(P start, P end) {
        int a = Math.abs(start.x - end.x);
        int b = Math.abs(start.y - end.y);
        //失去了精度，这里在真实游戏中不会这么处理
        return (int) Math.sqrt(a * a + b * b);
    }

    /**
     * 计算起点到节点的距离
     *
     * @param start
     * @param node
     * @return
     */
    private static int g(P start, P node) {
        return pointToPoint(start, node);
    }

    /**
     * 计算节点到终点的距离
     *
     * @param node
     * @param end
     * @return
     */
    private static int h(P node, P end) {
        return pointToPoint(node, end);
    }

    /**
     * 估价函数
     *
     * @param start
     * @param node
     * @param end
     * @return
     */
    private static int f(P start, P node, P end) {
        return g(start, node) + h(node, end);
    }

    private static void openFn(P p) {
        if (p.x == endPoint.x && p.y == endPoint.y) {
            return;
        }
        //走过的路标记为1
        map[p.y][p.x] = 1;

        //找到p周围离终点最短的点
        P minPoint = lookUp(p);
        openFn(minPoint);
    }

    private static P lookUp(P p) {
        //上、左上、左、左下、下、右下、右、右上
        List<P> collect = new ArrayList<>(8);
        //3代表不能走，1代表已经走过
        int x = p.x;    //横坐标
        int y = p.y;    //纵坐标
        if (map[y - 1][x] != 3 && map[y - 1][x] != 1) {
            collect.add(new P(y - 1, x));
        }
        if (map[y - 1][x - 1] != 3 && map[y - 1][x - 1] != 1) {
            collect.add(new P(y - 1, x - 1));
        }
        if (map[y][x - 1] != 3 && map[y][x - 1] != 1) {
            collect.add(new P(y, x - 1));
        }
        if (map[y + 1][x - 1] != 3 && map[y + 1][x - 1] != 1) {
            collect.add(new P(y + 1, x - 1));
        }
        if (map[y + 1][x] != 3 && map[y + 1][x] != 1) {
            collect.add(new P(y + 1, x));
        }
        if (map[y + 1][x + 1] != 3 && map[y + 1][x + 1] != 1) {
            collect.add(new P(y + 1, x + 1));
        }
        if (map[y][x + 1] != 3 && map[y][x + 1] != 1) {
            collect.add(new P(y, x + 1));
        }
        if (map[y - 1][x + 1] != 3 && map[y - 1][x + 1] != 1) {
            collect.add(new P(y - 1, x + 1));
        }

        //找到离终点最短的点
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        int size = collect.size();
        //如果size = 0;说明是死路，但是在网游设计中肯定不会有死路。
        for (int i = 0; i < size; i++) {
            int distance = f(p, collect.get(i), endPoint);
            if (distance < minDistance) {
                minDistance = distance;
                minIndex = i;
            }
        }
        if (minIndex == -1) {
            throw new RuntimeException(p.y + "---" + p.x + "->死路了");
        }
        return collect.get(minIndex);
    }

    public static void init() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                P p = new P(y, x);
                if (map[y][x] == 1) {
                    openArray.add(p);
                    startPoint = p;
                } else if (map[y][x] == 2) {
                    endPoint = p;
                } else if (map[y][x] == 3) {
                    closeArray.add(p);
                }
            }
        }

    }

    private static void display() {
        int size = map.length;
        for (int y = 0; y < size; y++) {
            int xSize = map[y].length;
            for (int x = 0; x < xSize; x++) {
                System.out.print(" " + map[y][x]);
            }
            System.out.println();
        }
    }

    public static void test() {
        init();
        openFn(startPoint);
        display();
    }

    public static class P {
        public int x;
        public int y;

        public P() {
        }

        public P(int y, int x) {
            this.x = x;
            this.y = y;
        }
    }
}

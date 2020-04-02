package com.lin.learn.java.algorithm;

/**
 * 深度优先算法-实现游戏地图的寻路
 */
public class GameMap {

    private static int[][] map = {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3},
            {3, 3, 0, 0, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 3, 0, 3, 0, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 3, 3, 3, 0, 3, 3},
            {3, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 3, 3, 0, 0, 0},
            {3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3},
            {3, 0, 0, 3, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3},
            {3, 3, 0, 3, 0, 0, 3, 3, 0, 3, 3, 0, 3, 3, 3, 3, 3},
            {3, 3, 0, 3, 3, 3, 3, 3, 0, 3, 3, 0, 0, 3, 3, 3, 3},
            {3, 3, 0, 0, 0, 3, 0, 3, 0, 3, 3, 3, 0, 0, 0, 0, 3},
            {3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
    };

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

    /**
     * @param y 纵轴
     * @param x 横轴
     */
    public static boolean dfs(int y, int x, int targetY, int targetX) {
        if (map[targetY][targetX] == 2) {
            return true;
        }

        if (map[y][x] == 0) {
            map[y][x] = 2;
            //上左下右
            if (dfs(y - 1, x, targetY, targetX) ||
                    dfs(y, x - 1, targetY, targetX) ||
                    dfs(y + 1, x, targetY, targetX) ||
                    dfs(y, x + 1, targetY, targetX)) {
                return true;
            } else {
                map[y][x] = 1;
            }
        }
        return false;
    }

    public static void test() {
//        display();

        int startX = 1;
        int startY = 1;
        int endX = 11;
        int endY = 10;
        dfs(startY, startX, endY, endX);
        display();
    }
}

package com.lin.learn.java.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import androidx.annotation.Nullable;

public class GameMap_AStartPathFindingPlus {


    public static void test() {

        int[][] map = {
//              {0, 1, 2, 3, 4, 5, 6, 7, 7, 9,10,11,12,13,14,15,16,17,18,19,20,21,22},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},//0
                {3, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//1
                {3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//2
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//3
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//4
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//5
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//6
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//7
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//8
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//9
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//10
                {3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//11
                {3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//12
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},//13
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3} //14
        };

        Node start = new Node(2, 7);
        Node end = new Node(14, 12);
        MapInfo mapInfo = new MapInfo(map, 23, 15, start, end);
        new GameMap_AStartPathFindingPlus().start(mapInfo);
        DynamicProgramming.display(map);
    }

    /**
     * 障碍物
     */
    public static final int BAR = 3;

    /**
     * 路径
     */
    public static final int PATH = 2;

    /**
     * 横向和纵向移动的代价
     */
    public static final int DIRECT_VALUE = 10;

    /**
     * 斜线移动的代价
     */
    public static final int OBLIQUE_VALUE = 14;

    Queue<Node> openList = new PriorityQueue<>();
    List<Node> closeList = new ArrayList<>();

    /**
     * 计算H值：曼哈顿方法
     *
     * @param end
     * @param coord
     * @return
     */
    private int calcH(Coord end, Coord coord) {
        return Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y);
    }

    /**
     * 判断是否是终点
     *
     * @param end
     * @param coord
     * @return
     */
    private boolean isEndNode(Coord end, Coord coord) {
        return end != null && end.equals(coord);
    }

    /**
     * 开始算法
     *
     * @param mapInfo
     */
    public void start(MapInfo mapInfo) {
        if (mapInfo == null) return;
        releaseLast();

        //开始搜索
        //1.先把起点加入到open中
        openList.offer(mapInfo.start);
        //2.目标锁定
        moveNode(mapInfo);
    }

    /**
     * 用来移动当前节点
     *
     * @param mapInfo
     */
    private void moveNode(MapInfo mapInfo) {
        while (!openList.isEmpty()) {
            if (isCoordInClose(mapInfo.end.coord)) { //如果终点进入了close表示结束
                //画出路径
                drawPath(mapInfo.map, mapInfo.end);
                break;
            }
            //把open中的第一个节点取出来放到close中
            Node current = openList.poll();
            closeList.add(current);

            //开始从current开始的地方找到邻近的能走的节点
            addNeighborNodeInOpen(mapInfo, current);
        }
    }

    private void drawPath(int[][] map, Node end) {
        if (map == null || end == null) return;
        System.out.println("最短长度" + end.g);
        Node node = end;
        while (node != null) {
            Coord c = node.coord;
            map[c.y][c.x] = PATH;
            node = node.parent;
        }
    }

    /**
     * 添加周围能加入的点
     *
     * @param mapInfo
     * @param current
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current) {
        int x = current.coord.x;
        int y = current.coord.y;
        //进行八次操作
        addNeighborNodeInOpen(mapInfo, current, x - 1, y, DIRECT_VALUE);//左
        addNeighborNodeInOpen(mapInfo, current, x, y - 1, DIRECT_VALUE);//上
        addNeighborNodeInOpen(mapInfo, current, x + 1, y, DIRECT_VALUE);//右
        addNeighborNodeInOpen(mapInfo, current, x, y + 1, DIRECT_VALUE);//下
        addNeighborNodeInOpen(mapInfo, current, x - 1, y - 1, OBLIQUE_VALUE);//左上
        addNeighborNodeInOpen(mapInfo, current, x + 1, y - 1, OBLIQUE_VALUE);//右上
        addNeighborNodeInOpen(mapInfo, current, x - 1, y + 1, OBLIQUE_VALUE);//左下
        addNeighborNodeInOpen(mapInfo, current, x + 1, y + 1, OBLIQUE_VALUE);//右下
    }

    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current, int x, int y, int value) {
        //判断当前节点是否在open中
        if (canAddNodeToOpen(mapInfo, x, y)) {
            Node end = mapInfo.end;
            Coord coord = new Coord(x, y);
            int g = current.g + value;  //当前点到开始点的距离就是g(n)函数
            Node child = findNodeInOpen(coord);
            if (child == null) {
                //表示child是从来没有搜索过的路
                int h = calcH(end.coord, coord);
                if (isEndNode(end.coord, coord)) {
                    child = end;
                    child.parent = current;
                    child.g = g;
                    child.h = h;
                } else {
                    child = new Node(coord, current, g, h);
                }
                openList.add(child);
            } else if (child.g > g) {
                child.parent = current;
                child.g = g;
                openList.add(child);
            }
        }
    }

    private Node findNodeInOpen(Coord coord) {
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList) {
            if (node.coord.equals(coord)) {
                return node;
            }
        }
        return null;
    }

    private boolean canAddNodeToOpen(MapInfo mapInfo, int x, int y) {
        //1.是否在地图中
        if (x < 0 || x >= mapInfo.width
                || y < 0 || y >= mapInfo.height) {
            return false;
        }

        //2.是否是可移动位置
        if (mapInfo.map[y][x] == BAR) return false;

        //3.是否在close中
        return !isCoordInClose(x, y);
    }

    /**
     * 判断节点是否在close中
     *
     * @param coord
     * @return
     */
    private boolean isCoordInClose(Coord coord) {
        return coord != null && isCoordInClose(coord.x, coord.y);
    }

    private boolean isCoordInClose(int x, int y) {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList) {
            if (node.coord.x == x && node.coord.y == y) return true;
        }
        return false;
    }

    /**
     * 清空上一次操作
     */
    private void releaseLast() {
        openList.clear();
        closeList.clear();
    }

    /**
     * 地图信息
     */
    private static class MapInfo {
        public int[][] map;
        public int width;
        public int height;
        public Node start;
        public Node end;

        public MapInfo(int[][] map, int width, int height, Node start, Node end) {
            this.map = map;
            this.width = width;
            this.height = height;
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 节点信息
     */
    private static class Node implements Comparable<Node> {
        public Coord coord;
        public Node parent;
        public int g;
        public int h;
        public int f;

        public Node(int x, int y) {
            this.coord = new Coord(x, y);
        }

        public Node(Coord coord, Node parent, int g, int h) {
            this.coord = coord;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }


        @Override
        public int compareTo(Node o) {
            return 0;
        }
    }

    /**
     * 点信息
     */
    private static class Coord {
        public int x;
        public int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (null == obj) return false;
            if (obj instanceof Coord) {
                Coord c = (Coord) obj;
                return x == c.x && y == c.y;
            }
            return super.equals(obj);
        }
    }
}

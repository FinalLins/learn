package com.lin.learn;


import com.lin.learn.java.algorithm.动态规划;
import com.lin.learn.java.algorithm.GameMap;
import com.lin.learn.java.algorithm.GameMap_AStarPathFinding;
import com.lin.learn.java.algorithm.GameMap_AStartPathFindingPlus;
import com.lin.learn.java.algorithm.麻将排序;
import com.lin.learn.java.algorithm.Pi;
import com.lin.learn.java.algorithm.赛程表;
import com.lin.learn.java.algorithm.九宫图;
import com.lin.learn.java.algorithm.八皇后;
import com.lin.learn.java.algorithm.数独;

import org.junit.Test;

public class AlgorithmTest {

    @Test
    public void saichengbiao() {
        赛程表.test();
    }

    @Test
    public void mahjongSort() {
        麻将排序.test();
    }

    @Test
    public void PI_Test() {
        Pi.generator();
    }

    @Test
    public void mapPathFindingTest() {
        GameMap.test();
    }

    @Test
    public void mapAStartPathFindingTest() {
        GameMap_AStarPathFinding.test();
    }

    @Test
    public void mapAStartPathFindingPlusTest(){
        GameMap_AStartPathFindingPlus.test();
    }

    @Test
    public void dynamicProgrammingLCS_Test() {
        动态规划.LCS("ABCBDAB", "BDCABA");
    }

    @Test
    public void dynamicProgrammingKMP_Test() {
        动态规划.KMP("abckeifababcabxfjjfekababcabafdsa", "ababcaba");
    }

    @Test
    public void dynamicProgrammingFloyd_Test() {
        int inf = Integer.MAX_VALUE;
        int[][] g = {
                {0, 2, 1, 5},
                {2, 0, 4, inf},
                {1, 4, 0, 3},
                {5, inf, 3, 0},
        };
        动态规划.floyd(g);
    }

    @Test
    public void 九宫图(){
        九宫图.squaredUp(4);
    }

    @Test
    public void 八皇后(){
        八皇后.eightQueens();
    }

    @Test
    public void 数独(){
        数独.sudoku();
    }
}


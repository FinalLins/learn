package com.lin.learn;


import com.lin.learn.java.algorithm.DynamicProgramming;
import com.lin.learn.java.algorithm.GameMap;
import com.lin.learn.java.algorithm.GameMap_AStarPathFinding;
import com.lin.learn.java.algorithm.Mahjong;
import com.lin.learn.java.algorithm.SaiChengBiao;

import org.junit.Test;

public class AlgorithmTest {

    @Test
    public void saichengbiao() {
        SaiChengBiao.test();
    }

    @Test
    public void mahjongSort() {
        Mahjong.test();
    }

    @Test
    public void mapPathFindingTest(){
        GameMap.test();
    }

    @Test
    public void mapAStartPathFindingTest(){
        GameMap_AStarPathFinding.test();
    }

    @Test
    public void dynamicProgrammingLCS_Test(){
        DynamicProgramming.LCS("ABCBDAB","BDCABA");
    }
}

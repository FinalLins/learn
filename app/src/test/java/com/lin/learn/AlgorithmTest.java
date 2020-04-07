package com.lin.learn;


import com.lin.learn.java.algorithm.RSA;
import com.lin.learn.java.algorithm.SHA1;
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

import java.io.ObjectOutputStream;

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
    public void mapAStartPathFindingPlusTest() {
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
    public void 九宫图() {
        九宫图.squaredUp(4);
    }

    @Test
    public void 八皇后() {
        八皇后.eightQueens();
    }

    @Test
    public void 数独() {
        数独.sudoku();
    }

    @Test
    public void sha1() {
//        sha1FormatData("ABC");
//        sha1FormatData("12345");
//        String src = "";
//        for (int i = 0; i < 60; i++) {
//            src += "A";
//        }
//        sha1FormatData(src);
        //ad93ae3d06a9114b3cbb33b6433ad546f0aa9f42
        String param = "jett";
        System.out.println("加密前:" + param);
        String digest = SHA1.getDigestOfString(param.getBytes());
        System.out.println("加密后的结果：" + digest);
        char[] ss = {'1', '0'};
        System.out.println((int) '0');
        for (char s : ss) {
            System.out.print(s - '0');
        }
//        System.out.println((int)'=');
    }

    private void sha1FormatData(String src) {
        System.out.println("---------------------------------------------");
        System.out.println("src : " + src);
        byte[] abytes = SHA1.byteArrayFormatData(src.getBytes());
        displayByteArray(abytes);
        //补完0就是64的倍数的字节数 512bit*n
    }

    public static void displayByteArray(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(b + "_");
        }
        System.out.println();
        System.out.println(bytes.length);
        System.out.println("---------------------------------------------");
    }

    public static int byteArrayToInt(byte[] byteData, int i) {
        //0a 0b 0c 0d  24       16       8       0
        //         0a000000  or  0b0000  or   0c00  or   0d
        //            0a0b0c0d
        return ((byteData[i] & 0xff) << 24) | ((byteData[i + 1] & 0xff) << 16) | ((byteData[i + 2] & 0xff) << 8) | (byteData[i + 3] & 0xff);
    }

    @Test
    public void rsaTest() throws Throwable{
        String s1 = "abc";
        String s2 = "中国";
        System.out.println(s1.length());
        System.out.println(s2.length());
        System.out.println((int)'中');

        RSA.generateKeyPair();

        String encrypt = RSA.encrypt(s2);
        System.out.println(encrypt);


        System.out.println(RSA.decrypt(encrypt));
        System.out.println("i12yPaPHym9FN8jw3wlztGZG3j+SpB0w8exzHN+MXPo2tDlTaBEr3FC2YQOyHXhUuCDzRXfGVtyV".length());
    }

}


package com.lin.learn.java.p1;

/**
 * 基础知识-位运算
 */
public class BitOperation {
    /**
     * 且（与）
     * 都是1才是1（都真才真，见假就假）
     *
     * @param x
     * @param y
     * @return
     */
    public static int and(int x, int y) {
        return x & y;
    }

    /**
     * 或
     * 有1就是1（见真就真，都假才假）
     *
     * @param x
     * @param y
     * @return
     */
    public static int or(int x, int y) {
        return x | y;
    }

    /**
     * 非
     * 1位0,0为1
     *
     * @param x
     * @return
     */
    public static int not(int x) {
        return ~x;
    }

    /**
     * 异或
     * 相同为0，不同为1
     *
     * @param x
     * @param y
     * @return
     */
    public static int xor(int x, int y) {
        return x ^ y;
    }

    /**
     * 左移
     * 左边舍弃，右边补0
     *
     * @param x
     * @param bit
     * @return
     */
    public static int leftShift(int x, int bit) {
        return x << bit;
    }

    /**
     * 右移
     * 右边舍弃，左边根据符号位来补是0还是1
     * [负数1，正数0]
     *
     * @param x
     * @param bit
     * @return
     */
    public static int rightShift(int x, int bit) {
        return x >> bit;
    }

    /**
     * 无符号右移
     * 右边舍弃，左边补0
     *
     * @param x
     * @param bit
     * @return
     */
    public static int unsignedRightShift(int x, int bit) {
        return x >>> bit;
    }

    public static void test() {
        int x = 16;
        //00000000 00000000 00000000 00010000   原码
        //00000000 00000000 00000000 00010000   反码
        //00000000 00000000 00000000 00010000   补码

        int y = -9;
        //10000000 00000000 00000000 00001001   原码
        //11111111 11111111 11111111 11110110   反码
        //11111111 11111111 11111111 11110111   补码

        //用补码进行操作
        //00000000 00000000 00000000 00010000   补码
        //11111111 11111111 11111111 11110111   补码
        //00000000 00000000 00000000 00010000   与的结果得到了正数
        System.out.println(and(x, y));

        //00000000 00000000 00000000 00010000   补码
        //11111111 11111111 11111111 11110111   补码
        //11111111 11111111 11111111 11110111   或的结果得到了负数
        //11111111 11111111 11111111 11110110   反码
        //10000000 00000000 00000000 00001001   原码 -9
        System.out.println(or(x, y));

        //00000000 00000000 00000000 00010000   补码
        //11111111 11111111 11111111 11101111   非的结果得到了负数
        //11111111 11111111 11111111 11101110
        //10000000 00000000 00000000 00010001   -17
        System.out.println(not(x));


        System.out.println(leftShift(x, 2)); //64
        System.out.println(rightShift(x, 2));//4
        System.out.println(unsignedRightShift(x, 2));//4

        System.out.println(leftShift(y, 2));// -36
        System.out.println(rightShift(y, 2));
        //11111111 11111111 11111111 11110111
        //00111111 11111111 11111111 11111101
        //00111111 11111111 11111111 11111100
        //01000000 00000000 00000000 00000011
        System.out.println(unsignedRightShift(y, 2));
        System.out.println(0b01000000_00000000_00000000_00000011);
        System.out.println(0b00111111_11111111_11111111_11111101);

        //00000000 00000000 00000000 00010000   补码
        //11111111 11111111 11111111 11110111   补码
        //11111111 11111111 11111111 11100111
        //11111111 11111111 11111111 11100110
        //10000000 00000000 00000000 00011001 -25
        System.out.println(xor(x, y));
    }
}

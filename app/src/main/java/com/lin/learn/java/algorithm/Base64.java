package com.lin.learn.java.algorithm;

/**
 * Base 64 编码规则:
 * 1.将byte数组的值转为二进制并放入数组,将数组中的每个元素的长度变成8个bit位,(即如果长度是6,则在值前面补两个0),最后按顺序拼接成一个完整的字符串
 * 2.将字符串以6位分组,不足的,要在末尾补0,达到6的倍数(记下补0的次数)
 * 3.将每个分组的字符串拿出来,转为十进制以这个为下标,去查表,取得所需的对应编码值
 * 4.将值(可以拼接成字符串,也可以byte数组的形式返回)如果第二步在末尾补0了,每补"00",就在值后拼接一个'='
 */
public class Base64 {
    static char[] base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();



    public static String encrypt(byte[] byteData) {
        int len = byteData.length;
        int bit = len * 8;
        int m = len % 3;
        //1.将byte数组的值转为二进制并放入数组,将数组中的每个元素的长度变成8个bit位,(即如果长度是6,则在值前面补两个0),最后按顺序拼接成一个完整的字符串
        //3个3个字节处理 多余的就是1和2
        if (m == 0) { //刚好是3的倍数对数据进行切分，每三个字节一组，一共24个bit。

        } else if (m == 1) {

        } else {

        }
        return null;
    }
}

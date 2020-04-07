package com.lin.learn.java.algorithm;

public class SHA1 {

    public static final char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 这个数组是sha1算法的规定，怎么来的暂不清楚
     */
    public static final int[] abcde = {
            0x67452301,
            0xEFCDAB89,
            0x98BADCFE,
            0x10325476,
            0xC3D2E1F0
    };

    /**
     * 摘要数据存储用的数组（存放密文） 20个byte*8bit = 160bit
     */
    public static int[] h = new int[5];

    /**
     * 计算过程中需要用到的临时数据存储的数组
     */
    public static int[] m = new int[80];

    /**
     * byte转十六进制
     *
     * @param b
     */
    public static String byteToHexString(byte b) {
        char[] ob = new char[2];
        ob[0] = digit[(b >>> 4) & 0x0F];    //拿到高4位
        ob[1] = digit[b & 0x0F];            //拿到低4位
        return new String(ob);
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexString(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将4个字节数组转换成int  i个byte合成到byteData中
     */
    public static int byteArrayToInt(byte[] byteArray, int i) {
        //从i开始去4个字节转成int
        //举例byte[4] = {00000001 00000010 00000100 00010000}
        //byte[0] = 00000001  <<24 => 00000001 00000000 00000000 00000000
        //byte[1] = 00000010  <<16 => 00000000 00000010 00000000 00000000
        //byte[2] = 00000100  <<8  => 00000000 00000000 00000100 00000000
        //byte[3] = 00010000       => 00000000 00000000 00000000 00010000
        //对4个结果去或                 00000001 00000010 00000100 00010000
        //& 0xFF只取低8位
        return ((byteArray[i] & 0xFF) << 24)
                | ((byteArray[i + 1] & 0xFF) << 16)
                | ((byteArray[i + 2] & 0xFF) << 8)
                | ((byteArray[i + 3] & 0xFF));
    }

    /**
     * 整数转换为4字节数组   int分解到byte数组中
     *
     * @param intValue
     * @param byteData
     * @param i
     */
    public static void intToByteArray(int intValue, byte[] byteData, int i) {
        byteData[i] = (byte) ((intValue >>> 24) & 0xFF); //取到int32位的高8位
        byteData[i + 1] = (byte) ((intValue >>> 16) & 0xFF);
        byteData[i + 2] = (byte) ((intValue >>> 8) & 0xFF);
        byteData[i + 3] = (byte) (intValue & 0xFF);
    }


//      Ft(b,c,d)  ((b&c)|((~b)&d))      (0  <= t <= 19)
//      Ft(b,c,d)  (b^c^d)               (20 <= t <= 39)
//      Ft(b,c,d)  ((b&c)|(b&d)|(c&d))   (40 <= t <= 59)
//      Ft(b,c,d)  (b^c^d)               (60 <= t <= 79)
//      下面四个公式是SHA1算法的非线性函数，这样对数据进行一个不可逆的操作

    public static int f1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    public static int f2(int x, int y, int z) {
        return x ^ y ^ z;
    }

    public static int f3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    public static int f4(int x, int y, int z) {
        return x ^ y ^ z;
    }

    /**
     * n是一个整数且0<=n<=32。Sn(X) = (X<<n)OR(X>>(32-n))
     * 用来循环位移
     * example:   x = 01010000 00000000 00000000 0001111  i = 2
     * 高2位移动到低位去
     *       result = 01000000 00000000 00000000 0111101
     * @param x
     * @param i
     * @return
     */
    public static int s(int x, int i) {
        return (x << i) | x >>> (32 - i);
    }

    /**
     * 进行对原数据的补位
     * 前56个字节放内容信息（十六进制），8个字节放长度信息
     * 1.原数据长度小于56个字节的情况，
     *
     * @param byteData
     * @return
     */
    public static byte[] byteArrayFormatData(byte[] byteData) {
        //补0的个数
        int fill = 0;
        //补位后的总位数,64的倍数 (每块数据是512位)
        int size = 0;
        //原数据的长度
        int srcLength = byteData.length;
        //对64求余数   n%512      56数据
        int m = srcLength % 64;
        if (m <= 55) {
            fill = 55 - m;
            size = 64;
        }
//        } else if (m == 56) {
//            fill = 63;
//            size = srcLength + 8 + 64;
//        } else {
        else {
            fill = 64 - m + 55;
            size = (srcLength + 64) - m + 64;
        }

        //补位后生成新的数组
        byte[] newByte = new byte[size];
        System.arraycopy(byteData, 0, newByte, 0, srcLength);

        //先补1
        int startLocation = srcLength;
        newByte[startLocation++] = (byte) 0x80; //10000000
        //补0
        for (int i = 0; i < fill; i++) {
            newByte[startLocation++] = (byte) 0x00;
        }
        //处理长度的位置
        long n = (long) srcLength * 8;

        //sha1用的是大端存储模式：高位存放低地址，低位存放高地址
        byte h8 = (byte) (n & 0xFF);
        byte h7 = (byte) ((n >> 8) & 0xFF);
        byte h6 = (byte) ((n >> 16) & 0xFF);
        byte h5 = (byte) ((n >> 24) & 0xFF);
        byte h4 = (byte) ((n >> 32) & 0xFF);
        byte h3 = (byte) ((n >> 40) & 0xFF);
        byte h2 = (byte) ((n >> 48) & 0xFF);
        byte h1 = (byte) ((n >> 56));
        newByte[startLocation++] = h1;
        newByte[startLocation++] = h2;
        newByte[startLocation++] = h3;
        newByte[startLocation++] = h4;
        newByte[startLocation++] = h5;
        newByte[startLocation++] = h6;
        newByte[startLocation++] = h7;
        newByte[startLocation] = h8;

        return newByte;
    }

    /**
     * 开始计算密文，算摘要
     *
     * @param byteData
     * @return
     */
    public static int process_input_bytes(byte[] byteData) {
        System.arraycopy(abcde, 0, h, 0, abcde.length);
        //格式化数据补0操作，补到64倍数的字节
        byte[] newBytes = byteArrayFormatData(byteData);
        //计算有多少个大块
        int mCount = newBytes.length / 64;
        //循环计算每一块内容，对每块内容使用非线性函数进行不可逆的加密
        for (int pos = 0; pos < mCount; pos++) {
            //对每一块都进行加密计算
            //(1). 将 Mi 分成 16 个字 W0, W1, ... , W15,  W0 是最左边的字
            //对每块内容每4个字节转成int，存在临时数组，用来加密处理
            for (int i = 0; i < 16; i++) {
                m[i] = byteArrayToInt(newBytes, (pos * 64) + (i * 4));
            }
            //加密计算
            encrypt();
        }
        return 20;
    }

    private static void encrypt() {
        //(2). 对于 t = 16 到 79 令
        // Wt = S1(Wt-3 XOR Wt-8 XOR Wt- 14 XOR Wt-16).
        for (int t = 16; t <= 79; t++) {
            m[t] = s(m[t - 3] ^ m[t - 8] ^ m[t - 14] ^ m[t - 16], 1);
        }

        //3.令 A = H0, B = H1, C = H2, D = H3, E = H4.
        int[] tempabcde = new int[5];
        for (int i = 0; i < tempabcde.length; i++) {
            tempabcde[i] = h[i];
        }

        //4.对于 t = 0 到 79，执行下面的循环
        //TEMP = S5(A) + ft(B,C,D) + E + Wt + Kt;
        //E = D; D = C; C = S30(B); B = A; A = TEMP;
        //一共有80次操作
        //Kt = 0x5A827999  (0 <= t <= 19)
        //Kt = 0x6ED9EBA1 (20 <= t <= 39)
        //Kt = 0x8F1BBCDC (40 <= t <= 59)
        //Kt = 0xCA62C1D6 (60 <= t <= 79)
        for (int i = 0; i <= 19; i++) {
            int temp = s(tempabcde[0], 5)
                    + f1(tempabcde[1], tempabcde[2], tempabcde[3])
                    + tempabcde[4]
                    + m[i] + 0x5A827999;
            tempabcde[4] = tempabcde[3];
            tempabcde[3] = tempabcde[2];
            tempabcde[2] = s(tempabcde[1], 30);
            tempabcde[1] = tempabcde[0];
            tempabcde[0] = temp;
        }

        for (int i = 20; i <= 39; i++) {
            int temp = s(tempabcde[0], 5)
                    + f2(tempabcde[1], tempabcde[2], tempabcde[3])
                    + tempabcde[4]
                    + m[i] + 0x6ED9EBA1;
            tempabcde[4] = tempabcde[3];
            tempabcde[3] = tempabcde[2];
            tempabcde[2] = s(tempabcde[1], 30);
            tempabcde[1] = tempabcde[0];
            tempabcde[0] = temp;
        }
        for (int i = 40; i <= 59; i++) {
            int temp = s(tempabcde[0], 5)
                    + f3(tempabcde[1], tempabcde[2], tempabcde[3])
                    + tempabcde[4]
                    + m[i] + 0x8F1BBCDC;
            tempabcde[4] = tempabcde[3];
            tempabcde[3] = tempabcde[2];
            tempabcde[2] = s(tempabcde[1], 30);
            tempabcde[1] = tempabcde[0];
            tempabcde[0] = temp;
        }
        for (int i = 60; i <= 79; i++) {
            int temp = s(tempabcde[0], 5)
                    + f4(tempabcde[1], tempabcde[2], tempabcde[3])
                    + tempabcde[4]
                    + m[i] + 0xCA62C1D6;
            tempabcde[4] = tempabcde[3];
            tempabcde[3] = tempabcde[2];
            tempabcde[2] = s(tempabcde[1], 30);
            tempabcde[1] = tempabcde[0];
            tempabcde[0] = temp;
        }
        //5.令 H0 = H0 + A, H1 = H1 + B, H2 = H2 + C, H3 = H3 + D, H4 = H4 + E.
        for (int i = 0; i < tempabcde.length; i++) {
            h[i] = h[i] + tempabcde[i];
        }
        //完成了一次操作
        //清除之前的内容，开始下一个块的计算
        for (int i = 0; i < m.length; i++) {
            m[i] = 0;
        }
    }

    /**
     * 把已经算好的数据提供一个接口进行输入和输出
     *
     * @param byteData
     * @return
     */
    public static byte[] getDigestOfBytes(byte[] byteData) {
        process_input_bytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < h.length; i++) {
            intToByteArray(h[i], digest, i * 4);
        }
        return digest;
    }

    public static String getDigestOfString(byte[] byteData) {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }

    public static String 十进制转二进制(int value) {
        char[] chars = new char[32];
        int index = chars.length - 1;
        int n = value;
        while (index >= 0) {
            if (n == 0) {
                chars[index--] = '0';
            } else {
                chars[index--] = (char) ('0' + n % 2);
                n = n / 2;
            }

        }
        return new String(chars);
    }

}

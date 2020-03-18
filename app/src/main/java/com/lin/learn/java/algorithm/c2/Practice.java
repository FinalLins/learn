package com.lin.learn.java.algorithm.c2;

import com.lin.learn.java.structure.MyLinkedList;

/**
 * 对一组无序麻将进行排序（使用链式基数排序方式）
 */
public class Practice {

    private static void mahjong() {
        //模拟接收到服务端数据
        //七万、九万、九饼、六饼、九条、一饼、四万、二万、一万、三万、九饼、五饼、六条、八条和九饼
        MyLinkedList<Mahjong> mahjongs = new MyLinkedList<>(20);
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 7));
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 9));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 9));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 6));
        mahjongs.add(new Mahjong(Mahjong.Color.TIAO, 9));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 1));
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 4));
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 2));
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 1));
        mahjongs.add(new Mahjong(Mahjong.Color.WAN, 3));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 9));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 5));
        mahjongs.add(new Mahjong(Mahjong.Color.TIAO, 6));
        mahjongs.add(new Mahjong(Mahjong.Color.TIAO, 8));
        mahjongs.add(new Mahjong(Mahjong.Color.BING, 9));
        System.out.println(mahjongs.size());
        mahjongs.reverse();
        mahjongs.display();                                         //接收到牌
        radixSort(mahjongs);                                        //链式基数排序
        mahjongs.display();
        mahjongAdd(mahjongs, new Mahjong(Mahjong.Color.TIAO, 3));
    }

    private static void radixSort(MyLinkedList<Mahjong> list) {
        //2.构建9个桶
        int numberBucketSize = 9;
        MyLinkedList[] numberBucketList = new MyLinkedList[numberBucketSize];
        for (int i = 0; i < numberBucketSize; i++) {
            numberBucketList[i] = new MyLinkedList<>(20);
        }
        //3.先把点数放入到对应桶中
        Mahjong mt = null;
        while ((mt = list.poll()) != null) {
            numberBucketList[mt.number - 1].add(mt);
        }

        //4.定义一个花色顺序桶
        int colorBucketSize = 3;
        MyLinkedList[] colorBucketList = new MyLinkedList[colorBucketSize];
        for (int i = 0; i < numberBucketSize; i++) {
            MyLinkedList bucket = numberBucketList[i];
            Mahjong mahjong = null;
            while ((mahjong = (Mahjong) bucket.poll()) != null) {
                if (colorBucketList[mahjong.color] == null) {
                    colorBucketList[mahjong.color] = new MyLinkedList(20);
                }
                colorBucketList[mahjong.color].add(mahjong);
            }
        }

        for (int i = 0; i < colorBucketSize; i++) {
            MyLinkedList bucket = colorBucketList[i];
            Mahjong mahjong = null;
            while ((mahjong = (Mahjong) bucket.poll()) != null) {
                list.add(mahjong);
            }
        }
    }

    private static void mahjongAdd(MyLinkedList<Mahjong> list, Mahjong mahjong) {
//        int size = list.size();
//        MyLinkedList.Node<Mahjong> cur = list.getHead();
//        while (cur != null) {
//            if (cur.data.color != mahjong.color) {
//                cur = cur.next;
//                continue;
//            }
//            if (cur.data.)
//        }

    }

    public static void test() {
        mahjong();
    }


}

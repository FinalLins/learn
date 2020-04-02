package com.lin.learn.java.algorithm;

import com.lin.learn.java.structure.MyLinkedList;

import androidx.annotation.NonNull;

public class 麻将排序 {
    public int color;          //花色
    public int number;

    public 麻将排序(int color, int number) {
        this.color = color;
        this.number = number;
    }

    @NonNull
    @Override
    public String toString() {
        return number + toCN(color);
    }

    String toCN(int color) {
        if (color == Color.TIAO) {
            return "条";
        } else if (color == Color.BING) {
            return "饼";
        } else if (color == Color.WAN) {
            return "万";
        }
        return "";
    }

    public interface Color {
        int TIAO = 0;
        int BING = 1;
        int WAN = 2;
    }

    private static void mahjong() {
        //模拟接收到服务端数据
        //七万、九万、九饼、六饼、九条、一饼、四万、二万、一万、三万、九饼、五饼、六条、八条和九饼
        MyLinkedList<麻将排序> mahjongs = new MyLinkedList<>(20);
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 7));
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 9));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 9));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 6));
        mahjongs.add(new 麻将排序(麻将排序.Color.TIAO, 9));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 1));
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 4));
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 2));
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 1));
        mahjongs.add(new 麻将排序(麻将排序.Color.WAN, 3));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 9));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 5));
        mahjongs.add(new 麻将排序(麻将排序.Color.TIAO, 6));
        mahjongs.add(new 麻将排序(麻将排序.Color.TIAO, 8));
        mahjongs.add(new 麻将排序(麻将排序.Color.BING, 9));
        System.out.println(mahjongs.size());
        mahjongs.reverse();
        mahjongs.display();                                         //接收到牌
        radixSort(mahjongs);                                        //链式基数排序
        mahjongs.display();
        mahjongAdd(mahjongs, new 麻将排序(麻将排序.Color.TIAO, 3));
    }

    private static void radixSort(MyLinkedList<麻将排序> list) {
        //2.构建9个桶
        int numberBucketSize = 9;
        MyLinkedList[] numberBucketList = new MyLinkedList[numberBucketSize];
        for (int i = 0; i < numberBucketSize; i++) {
            numberBucketList[i] = new MyLinkedList<>(20);
        }
        //3.先把点数放入到对应桶中
        麻将排序 mt = null;
        while ((mt = list.poll()) != null) {
            numberBucketList[mt.number - 1].add(mt);
        }

        //4.定义一个花色顺序桶
        int colorBucketSize = 3;
        MyLinkedList[] colorBucketList = new MyLinkedList[colorBucketSize];
        for (int i = 0; i < numberBucketSize; i++) {
            MyLinkedList bucket = numberBucketList[i];
            麻将排序 mahjong = null;
            while ((mahjong = (麻将排序) bucket.poll()) != null) {
                if (colorBucketList[mahjong.color] == null) {
                    colorBucketList[mahjong.color] = new MyLinkedList(20);
                }
                colorBucketList[mahjong.color].add(mahjong);
            }
        }

        for (int i = 0; i < colorBucketSize; i++) {
            MyLinkedList bucket = colorBucketList[i];
            麻将排序 mahjong = null;
            while ((mahjong = (麻将排序) bucket.poll()) != null) {
                list.add(mahjong);
            }
        }
    }

    private static void mahjongAdd(MyLinkedList<麻将排序> list, 麻将排序 mahjong) {
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

package com.lin.learn.java.algorithm.c2;

import androidx.annotation.NonNull;

public class Mahjong {
    public int color;          //花色
    public int number;

    public Mahjong(int color, int number) {
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
}

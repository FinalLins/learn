package com.lin.learn.java.algorithm;


import java.util.Random;

public class Pi {

    public static void generator() {
        int pointSize = 50000000;
        Random r = new Random();
        int hit = 0;
        for (int i = 0; i < pointSize; i++) {
            double x = r.nextDouble();
            double y = r.nextDouble();
            if (x * x + y * y <= 1) {
                hit++;
            }
        }
        double PI = 4 * (double) hit / pointSize;
        System.out.println(PI);
    }
}

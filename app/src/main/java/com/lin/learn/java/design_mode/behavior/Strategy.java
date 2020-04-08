package com.lin.learn.java.design_mode.behavior;

/**
 * 策略模式(行为型)
 * 有一系列的算法，将每个算法封装起来（每个算法可以封装到不同的类中），各个算法之间可以替换，策略模式让算法独立于使用它的客户而独立变化。
 */
public class Strategy {
    public interface SortStrategy {
        void sort();
    }

    public static class BubbleStrategy implements SortStrategy {

        @Override
        public void sort() {
            System.out.println("冒泡排序");
        }
    }

    public static class MergeStrategy implements SortStrategy {
        @Override
        public void sort() {
            System.out.println("归并排序");
        }
    }

    public static class HeapStrategy implements SortStrategy {
        @Override
        public void sort() {
            System.out.println("堆排序");
        }
    }


    public static class SortPackage {
        private SortStrategy strategy;

        public void setStrategy(SortStrategy strategy) {
            this.strategy = strategy;
        }

        public void sort() {
            if (strategy != null) {
                strategy.sort();
            }
        }
    }

    public static void test() {
        //根据数据量大小，时间复杂度和空间复杂度去选择不同的排序算法
        //代码省略
        //模拟一下，并不是真实的排序对应的逻辑选择
        int[] a = new int[10];
        int[] b = new int[100000];
        int[] c = new int[100000000];

        //跟状态模式代码结构基本一直，区别就在于侧重点不一样。
        SortPackage sortPackage = new SortPackage();
        sortPackage.setStrategy(new BubbleStrategy());
        sortPackage.sort();
        sortPackage.setStrategy(new MergeStrategy());
        sortPackage.sort();
        sortPackage.setStrategy(new HeapStrategy());
        sortPackage.sort();


    }


}

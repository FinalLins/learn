package com.lin.learn.java.design_mode.create;

/**
 *  抽象工厂模式
 */
public class AbsFactory {
    public static abstract class CPU {
        public abstract void method();
    }

    public static abstract class Screen {
        public abstract void method();
    }

    public static class Gaotong_Xiaolong_1 extends CPU {

        @Override
        public void method() {
            System.out.println("高通骁龙处理器1.0");
        }
    }

    public static class Gaotong_Xiaolong_2 extends CPU {

        @Override
        public void method() {
            System.out.println("高通骁龙处理器2.0");
        }
    }

    public static class XiaPu_1 extends Screen {

        @Override
        public void method() {
            System.out.println("夏普屏幕1.0");
        }
    }

    public static class XiaPu_2 extends Screen {

        @Override
        public void method() {
            System.out.println("夏普屏幕2.0");
        }
    }

    public static class XiaPu_Qumian extends Screen {

        @Override
        public void method() {
            System.out.println("夏普曲面屏幕");
        }
    }

    public static abstract class Factory {

        public abstract String phoneName();

        public abstract CPU createCPU();

        public abstract Screen createScreen();

        public void group() {
            System.out.println("开始组装手机 : " + phoneName());
            createCPU().method();
            createScreen().method();
            System.out.println("组装成功");
            System.out.println("=================");
        }
    }

    public static class XIAOMI9Factory extends Factory {

        @Override
        public String phoneName() {
            return "小米9";
        }

        @Override
        public CPU createCPU() {
            return new Gaotong_Xiaolong_1();
        }

        @Override
        public Screen createScreen() {
            return new XiaPu_1();
        }
    }

    public static class XIAOMI10Factory extends Factory {

        @Override
        public String phoneName() {
            return "小米10";
        }

        @Override
        public CPU createCPU() {
            return new Gaotong_Xiaolong_2();
        }

        @Override
        public Screen createScreen() {
            return new XiaPu_1();
        }
    }

    public static void test() {
        Factory factory1 = new XIAOMI9Factory();
        factory1.group();

        Factory factory2 = new XIAOMI10Factory();
        factory2.group();

        Factory factory3 = new Factory() {
            @Override
            public String phoneName() {
                return "小米mix4";
            }

            @Override
            public CPU createCPU() {
                return new Gaotong_Xiaolong_2();
            }

            @Override
            public Screen createScreen() {
                return new XiaPu_Qumian();
            }
        };
        factory3.group();
    }
}

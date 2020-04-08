package com.lin.learn.java.design_mode.create;

/**
 * 简单工厂
 */
public class SimpleFactory {
    /**
     * 具体的产品是个类
     */
    public static abstract class Phone {
        public abstract void test();
    }

    public static class RedMi extends Phone {

        @Override
        public void test() {
            System.out.println("测试红米手机");
        }
    }

    public static class Vivo extends Phone {

        @Override
        public void test() {
            System.out.println("测试VIVO手机");
        }
    }

    public static class Huawei extends Phone {

        @Override
        public void test() {
            System.out.println("测试华为手机");
        }
    }

    /**
     * 生成工厂
     */
    public static abstract class Factory {
        public abstract Phone create(String name);
    }

    public static class PhoneFactory extends Factory {

        public static final String RED_MI = "红米手机";
        public static final String VIVO = "VIVO手机";
        public static final String HUAWEI_MI = "华为手机";

        @Override
        public Phone create(String name) {
            if (RED_MI.equals(name)) {
                return new RedMi();
            } else if (VIVO.equals(name)) {
                return new Vivo();
            } else if (HUAWEI_MI.equals(name)) {
                return new Huawei();
            }
            return null;
        }
    }

    public static class PhoneFactory2 {

        public static Phone create(Class<? extends Phone> clazz) throws Throwable {
            return clazz.newInstance();
        }
    }

    public static void test() {
        //创建一个工厂
        PhoneFactory phoneFactory = new PhoneFactory();
        //生产一个红米手机
        phoneFactory.create(PhoneFactory.RED_MI).test();
        //生产一个vivo手机
        phoneFactory.create(PhoneFactory.VIVO).test();
        //生产一个华为手机
        phoneFactory.create(PhoneFactory.HUAWEI_MI).test();

        try {
            PhoneFactory2.create(RedMi.class).test();
            PhoneFactory2.create(Vivo.class).test();
            PhoneFactory2.create(Huawei.class).test();
        } catch (Throwable t) {

        }
    }

}

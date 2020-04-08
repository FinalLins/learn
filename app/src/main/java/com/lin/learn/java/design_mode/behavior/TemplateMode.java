package com.lin.learn.java.design_mode.behavior;

/**
 * 模板方法模式
 *
 * 定义一个框架，框架中所有方法按顺序执行。使得子类不改变父类结构。有序的执行这些方法。
 */
public class TemplateMode {

    public static abstract class Person {
        protected abstract void getUp();

        protected abstract void breakfast();

        protected abstract void goToWork(String val);

        protected abstract void lunch();

        protected abstract void afterWork();

        protected abstract void dinner();

        protected abstract void sleep();

        public void execute(){
            getUp();
            breakfast();
            goToWork("morning");
            lunch();
            goToWork("afternoon");
            afterWork();
            dinner();
            sleep();
        }
    }

    public static class XiaoZhou extends Person {

        @Override
        protected void getUp() {
            System.out.println("7:00起床，洗漱");
        }

        @Override
        protected void breakfast() {
            System.out.println("7:30吃早饭");
        }

        @Override
        protected void goToWork(String val) {
            if(val.equals("morning")) {
                System.out.println("上午悠闲的混日子");
            } else {
                System.out.println("下午开启摸鱼模式");
            }
        }

        @Override
        protected void lunch() {
            System.out.println("12:00吃午饭");
        }

        @Override
        protected void afterWork() {
            System.out.println("17:00下班回家");
        }

        @Override
        protected void dinner() {
            System.out.println("沙县小吃搞定晚餐");
        }

        @Override
        protected void sleep() {
            System.out.println("24:00睡觉");
        }
    }

    public static class PingJie extends Person {

        @Override
        protected void getUp() {
            System.out.println("9:00起床，抽根烟");
        }

        @Override
        protected void breakfast() {
            System.out.println("看心情吃早饭");
        }

        @Override
        protected void goToWork(String val) {
            if(val.equals("morning")) {
                System.out.println("上午处理各种邮件");
            } else {
                System.out.println("下午努力工作");
            }
        }

        @Override
        protected void lunch() {
            System.out.println("中午简单吃点儿");
        }

        @Override
        protected void afterWork() {
            System.out.println("20:00下班");
        }

        @Override
        protected void dinner() {
            System.out.println("罗贯中");
        }

        @Override
        protected void sleep() {
            System.out.println("睡什么觉，起来嗨");
        }
    }

    public static void test() {
        Person person = new XiaoZhou();
        person.execute();

        System.out.println("----");
        Person person1 = new PingJie();
        person1.execute();
    }
}

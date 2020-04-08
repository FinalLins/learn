package com.lin.learn.java.design_mode.structure;

/**
 * 装饰者模式：在不必改变原类文件和使用继承的情况下，
 * 动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。
 */
public class DecoratorMode {
    public static class Person {
        public void eat() {
            System.out.println("吃饭");
        }
    }

    public static class Decorator1 extends Person {

        @Override
        public void eat() {
            System.out.println("先吃个餐前小菜");
            super.eat();
            System.out.println("吃饱来，来点水果");
        }

        /**
         * 跟代理模式相比，装饰者更注重在原来的基础上添加（装饰）
         */
        public void sleep() {
            System.out.println("睡觉");
        }
    }

    public static class Decorator2 extends Person {
        private Person person;

        public Decorator2(Person person) {
            this.person = person;
        }

        @Override
        public void eat() {
            System.out.println("先吃个餐前小菜");
            this.person.eat();
            System.out.println("吃饱来，来点水果");
        }

        /**
         * 跟代理模式相比，装饰者更注重在原来的基础上添加（装饰）
         */
        public void sleep() {
            System.out.println("睡觉");
        }
    }

    public static void test() {
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();

        Decorator1 decorator1 = new Decorator1();
        Decorator2 decorator2 = new Decorator2(person3);

        person1.eat();
        decorator1.eat();
        decorator1.sleep();
        decorator2.eat();



    }
}

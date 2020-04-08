package com.lin.learn.java.design_mode.create;


/**
 * 单例模式
 */
public class Singleton {
    /**
     * 饿汉式单例
     */
    private static class S1 {
        private static S1 INSTANCE = new S1();

        /**
         * 构造方法私有
         */
        private S1() {
        }

        /**
         * 静态方法获取唯一实例
         * 线程安全
         * 缺点：静态实例化了，没有做到懒加载
         *
         * @return
         */
        public static S1 getInstance() {
            return INSTANCE;
        }

        public void doSomething() {
            System.out.println("饿汉式单例");
        }
    }

    /**
     * 懒汉式单例
     */
    private static class S2 {
        private static S2 INSTANCE;

        /**
         * 构造方法私有
         */
        private S2() {
        }

        /**
         * 静态方法获取唯一实例
         * 线程安全
         * 缺点：每次调用都需要同步，浪费和性能
         *
         * @return
         */
        public synchronized static S2 getInstance() {
            if (null == INSTANCE) {
                INSTANCE = new S2();
            }
            return INSTANCE;
        }

        public void doSomething() {
            System.out.println("懒汉式单例");
        }
    }

    /**
     * DCL单例(double check lock)
     */
    private static class S3 {
        private volatile static S3 INSTANCE;

        private S3() {
        }

        public static S3 getInstance() {
            if (null == INSTANCE) {
                synchronized (S3.class) {
                    if (null == INSTANCE) {
                        INSTANCE = new S3();
                        //1. 在堆里面给需要创建的对象申请内存存放S3对象
                        //2. 调用对象的构造方法初始化对象S3
                        //3. 把S3对象赋值给INSTANCE

                        //jvm指令重新排序
                        //1.
                        //3.
                        //2.

                        //加关键字volatile（sdk1.6）解决这个问题
                    }
                }
            }
            return INSTANCE;
        }

        public void doSomething() {
            System.out.println("双重检查锁单例");
        }
    }

    /**
     * 静态内部类单例
     */
    private static class S4 {

        /**
         * 构造方法私有
         */
        private S4() {
        }

        public static S4 getInstance() {
            return Inner.INSTANCE;
        }

        public void doSomething() {
            System.out.println("静态内部类单例");
        }

        private static class Inner {
            private static S4 INSTANCE = new S4();
        }
    }

    public enum S5 {

        INSTANCE;

        public void doSomething() {
            System.out.println("枚举单例");
        }

    }

    public enum S6 {
        INSTANCE1(1) {
            @Override
            public S6 getInstance() {
                return INSTANCE1;
            }
        },
        INSTANCE2(2) {
            @Override
            public S6 getInstance() {
                return INSTANCE2;
            }
        };

        int type;

        S6(int type) {
            this.type = type;
        }

        public abstract S6 getInstance();

        public void doSomething() {
            System.out.println("枚举抽象方法实现 : " + type);
        }
    }

    public static void test() {
        System.out.println(S1.getInstance());
        System.out.println(S2.getInstance());
        System.out.println(S3.getInstance());
        System.out.println(S4.getInstance());
        System.out.println(S5.INSTANCE);
        System.out.println(S6.INSTANCE1.hashCode());
        System.out.println(S6.INSTANCE2);

        S1.getInstance().doSomething();
        S2.getInstance().doSomething();
        S3.getInstance().doSomething();
        S4.getInstance().doSomething();
        S5.INSTANCE.doSomething();
        S6.INSTANCE1.doSomething();
        S6.INSTANCE2.doSomething();

        System.out.println(S1.getInstance());
        System.out.println(S2.getInstance());
        System.out.println(S3.getInstance());
        System.out.println(S4.getInstance());
        System.out.println(S5.INSTANCE);
        System.out.println(S6.INSTANCE1.hashCode());
        System.out.println(S6.INSTANCE2);
    }

}

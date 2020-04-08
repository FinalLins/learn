package com.lin.learn.java.design_mode.structure;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.security.AccessController;

import sun.misc.ProxyGenerator;

public class ProxyMode {
    public interface Interface {
        void doSomething(String args);

        int get();

        void set(int val);
    }

    public interface Interface2 {
        void doSomething2(String args);
    }

    public static class A implements Interface {
        private int val = 0;

        @Override
        public void set(int val) {
            this.val = val;
        }

        @Override
        public void doSomething(String args) {
            System.out.println("A : " + args);
        }

        @Override
        public int get() {
            return val;
        }
    }

    public static class B implements Interface2 {

        @Override
        public void doSomething2(String args) {
            System.out.println("B : " + args);
        }
    }

    /**
     * 静态代理类，依赖A
     */
    public static class StaticProxy implements Interface {
        Interface anInterface;

        public StaticProxy(Interface anInterface) {
            this.anInterface = anInterface;
        }

        @Override
        public void doSomething(String args) {
            this.anInterface.doSomething(args);
        }

        @Override
        public int get() {
            return 0;
        }

        @Override
        public void set(int val) {

        }
    }

    public static class DynamicProxy implements InvocationHandler {

        private Object obj;

        public DynamicProxy(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object o = method.invoke(obj, args);
            System.out.println("invoke : " + method.getName() + "===" + o);
            return o;
        }

    }

    public static void test() {
        StaticProxy staticProxy = new StaticProxy(new A());
        staticProxy.doSomething("静态代理");

        A a = new A();

        Interface i1 = (Interface) Proxy.newProxyInstance(
                A.class.getClassLoader(),
                A.class.getInterfaces(),
                new DynamicProxy(a)
        );
        System.out.println(a.getClass().getName() + "==" + a);
        System.out.println(i1.getClass().getName() + "==" + i1);
        Interface i2 = (Interface) Proxy.newProxyInstance(
                A.class.getClassLoader(),
                A.class.getInterfaces(),
                new DynamicProxy(new A())
        );

        Interface2 i3 = (Interface2) Proxy.newProxyInstance(
                B.class.getClassLoader(),
                B.class.getInterfaces(),
                new DynamicProxy(new B())
        );

        i1.doSomething("动态代理");
        i2.doSomething("动态代理");
        i3.doSomething2("动态代理");

        //动态生成的代理类名称是包名+$Proxy+id序号。
        System.out.println(i1.getClass().getName());
        System.out.println(i2.getClass().getName());
        System.out.println(i3.getClass().getName());

        //看看代理类到底长啥样
//        sun.misc.ProxyGenerator  这里貌似没有这个库
        try {
            saveGeneratedFiles(i1.getClass());
            saveGeneratedFiles(i3.getClass());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 动态代理写法2等于上面，但是这种写法更复杂
     */
    public static void test2() throws Throwable {
        //1.获得代理类，有缓存，如果没有在缓存里面就会通过ProxyFactory创建
        Class<?> clazz = Proxy.getProxyClass(Interface.class.getClassLoader(), Interface.class);
        System.out.println("proxy clazz : " + clazz.getName());
        //2.
        final Constructor<?> cons = clazz.getConstructor(InvocationHandler.class);
        A a = new A();
        System.out.println(a.getClass().getName() + "==" + a + "==" + a.hashCode());
        final InvocationHandler h = new DynamicProxy(a);
        Interface i = (Interface) cons.newInstance(h);
        System.out.println(i.getClass().getName() + "==" + i + "==" + i.hashCode());
        i.doSomething("动态代理3");
        System.out.println(i == a);
        System.out.println(i.equals(a));
        System.out.println(i.get() + "===" + a.get());
        a.set(10);
        System.out.println(i.get() + "===" + a.get());
    }

    private static void saveGeneratedFiles(Class<?> clazz) throws Throwable {
        String className = clazz.getSimpleName();
        byte[] buf = ProxyGenerator.generateProxyClass(className, new Class<?>[]{clazz});
        OutputStream os = new FileOutputStream(className + ".class");
        int totalLen = buf.length;
        int writeLen, leftLen = totalLen;
        while (leftLen > 0) {
            writeLen = Math.min(leftLen, 1024);
            os.write(buf, totalLen - leftLen, writeLen);
            leftLen -= writeLen;
        }
        os.close();
    }
}

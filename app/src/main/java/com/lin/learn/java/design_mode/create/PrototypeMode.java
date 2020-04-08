package com.lin.learn.java.design_mode.create;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 原型模式
 */
public class PrototypeMode implements Cloneable, Serializable {
    public int a;
    public Test test;

    public static void test() throws CloneNotSupportedException {
        //浅拷贝
        int a = 10;
        int b = a;
        b = 1;
        System.out.println(a);
        System.out.println(b);

        PrototypeMode mode1 = new PrototypeMode();
        mode1.a = a;
        mode1.test = new Test();
        mode1.test.a = 110;
        System.out.println(mode1.hashCode());
        PrototypeMode mode2 = mode1;
        mode2.a = b;
        System.out.println(mode1.hashCode());
        System.out.println(mode1);
        System.out.println(mode1.a);
        System.out.println(mode2);
        System.out.println(mode2.a);
        //mode1和mode2指向同一个地址
        System.out.println(mode1.test);
        System.out.println(mode2.test);
        System.out.println(mode1 == mode2);
        System.out.println(mode1.equals(mode2));

        //深拷贝
        PrototypeMode mode3 = (PrototypeMode) mode1.clone();
        mode3.a = 3;
        System.out.println(mode1.a);
        System.out.println(mode3.a);
        System.out.println(mode1 == mode3);
        System.out.println(mode1.equals(mode3));

        //但是里面的对象还是引用同一个
        System.out.println(mode1.test == mode3.test);


        //深度深拷贝
        PrototypeMode mode4 = null;
        try {
            mode4 = (PrototypeMode) deepClone(mode1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mode4.a = 4;
        System.out.println(mode1.a);
        System.out.println(mode1.test == mode4.test);

    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Object deepClone(Object object) throws IOException, ClassNotFoundException {
        byte[] obj;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(object);
                obj = bos.toByteArray();
            }
        }

        try (ByteArrayInputStream ios = new ByteArrayInputStream(obj)) {
            try (ObjectInputStream ois = new ObjectInputStream(ios)) {
                return ois.readObject();
            }
        }
    }

    static class Test implements Serializable {
        public int a;
    }
}

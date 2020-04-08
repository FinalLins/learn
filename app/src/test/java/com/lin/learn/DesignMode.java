package com.lin.learn;

import com.lin.learn.java.design_mode.behavior.ChainMode;
import com.lin.learn.java.design_mode.behavior.CommandMode;
import com.lin.learn.java.design_mode.behavior.IteratorMode;
import com.lin.learn.java.design_mode.behavior.MediatorMode;
import com.lin.learn.java.design_mode.behavior.MemoMode;
import com.lin.learn.java.design_mode.behavior.ObserverMode;
import com.lin.learn.java.design_mode.behavior.State;
import com.lin.learn.java.design_mode.behavior.Strategy;
import com.lin.learn.java.design_mode.behavior.TemplateMode;
import com.lin.learn.java.design_mode.behavior.VisitorMode;
import com.lin.learn.java.design_mode.create.AbsFactory;
import com.lin.learn.java.design_mode.create.BuilderMode;
import com.lin.learn.java.design_mode.create.PrototypeMode;
import com.lin.learn.java.design_mode.create.SimpleFactory;
import com.lin.learn.java.design_mode.create.Singleton;
import com.lin.learn.java.design_mode.structure.ProxyMode;

import org.junit.Test;


/**
 * 设计模式
 */
public class DesignMode {

    /**
     * 创建型
     */
    public static class Create {
        @Test
        public void singleton() {
            Singleton.test();
        }

        /**
         * 简单工厂，生产具体的产品。创建产品的是类
         */
        @Test
        public void simpleFactory() {
            SimpleFactory.test();
        }

        /**
         * 抽象工厂，生产具体的产品。创建产品的是接口
         */
        @Test
        public void abstractFactory() {
            AbsFactory.test();
        }

        /**
         * 建造者模式，把一个复杂对象的构建和对象的参数分离。使得同样的创建可以有不同的结果
         */
        @Test
        public void builder() {
            BuilderMode.test();
        }

        /**
         * 原型模式：由原型实例指定创建对象的类型，并通过拷贝这些创建新的对象
         */
        @Test
        public void prototype() {
            try {
                PrototypeMode.test();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 行为型:对同一个问题或方法的不同处理单独封装起来
     */
    public static class Behavior {

        @Test
        public void strategy() {
            Strategy.test();
        }

        @Test
        public void state() {
            State.test();
        }

        @Test
        public void chain() {
            ChainMode.test();
        }

        @Test
        public void command() {
            CommandMode.test();
        }

        @Test
        public void observer() {
            ObserverMode.test();
        }

        @Test
        public void memo() {
            MemoMode.test();
        }

        @Test
        public void iterator() {
            IteratorMode.test();
        }

        @Test
        public void template() {
            TemplateMode.test();
        }

        @Test
        public void visitor() {
            VisitorMode.test();
        }

        @Test
        public void mediator() {
            MediatorMode.test();
        }
    }

    public static class Structure {

        @Test
        public void proxy() throws Throwable{
            ProxyMode.test();
            ProxyMode.test2();
        }


    }
}

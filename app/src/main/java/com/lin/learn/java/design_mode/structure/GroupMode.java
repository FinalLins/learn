package com.lin.learn.java.design_mode.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 将部分整体的层次结构转换为树状结构，是的客户访问对象和组合对象具有一致性。
 */
public class GroupMode {

    public static abstract class Component {
        public String name;

        public Component(String name) {
            this.name = name;
        }

        public abstract void print();

        public abstract void addChild(Component component);

        public abstract void removeChild(Component component);

        public abstract Component getChild(int index);
    }

    public static class Node extends Component {
        private List<Component> componentList;

        public Node(String name) {
            super(name);
            this.componentList = new ArrayList<>();
        }

        @Override
        public void print() {
            System.out.println(name);
            for (Component component : componentList) {
                component.print();
            }
        }

        @Override
        public void addChild(Component component) {
            componentList.add(component);
        }

        @Override
        public void removeChild(Component component) {
            componentList.remove(component);
        }

        @Override
        public Component getChild(int index) {
            return componentList.get(index);
        }
    }

    public static class Leaf extends Component {

        public Leaf(String name) {
            super(name);
        }

        @Override
        public void print() {
            System.out.println(name);
        }

        @Override
        public void addChild(Component component) {
            //do nothing
        }

        @Override
        public void removeChild(Component component) {
            //do nothing
        }

        @Override
        public Component getChild(int index) {
            //do nothing
            return null;
        }
    }

    //======================================透明组合模式
    public static void 透明组合模式() {
        Component root = new Node("XX公司");

        Component software = new Node("软件部");
        Component hardware = new Node("硬件部");

        root.addChild(software);
        root.addChild(hardware);

        Component android = new Leaf("android");
        Component ios = new Leaf("ios");
        Component layout = new Leaf("layout");
        software.addChild(android);
        software.addChild(ios);
        hardware.addChild(layout);

        root.print();
    }

    public static void 安全组合模式(){
        //view和viewGroup就是安全组合模式
    }

    public static class View {
        String name;

        public View(String name) {
            this.name = name;
        }
    }

    public static class ViewGroup extends View implements ViewManager {
        private List<View> mChildren = new ArrayList<>();

        public ViewGroup(String name) {
            super(name);
        }

        @Override
        public void addView(View view) {
            mChildren.add(view);
        }


        @Override
        public void removeView(View view) {
            mChildren.remove(view);
        }
    }

    public interface ViewManager {
        void addView(View view);

        void removeView(View view);
    }
}

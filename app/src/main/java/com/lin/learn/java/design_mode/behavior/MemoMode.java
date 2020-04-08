package com.lin.learn.java.design_mode.behavior;


import java.util.ArrayList;
import java.util.List;

/**
 * 备忘录模式
 */
public class MemoMode {
    /**
     * 原发器角色
     * 原发器根据需要决定将自己的哪些内部状态保存到备忘录中，并可以使用备忘录来恢复内部状态。
     */
    public static class Originator {
        private String state;

        public Memento saveMemento() {
            return new MementoImp(state);
        }

        public void restore(Memento memento) {
            //这里使用了窄接口
            if (memento instanceof MementoImp) {
                this.state = ((MementoImp) memento).getState();
            }
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }


    /**
     * 管理者角色
     * 备忘录管理者，或者称为备忘录负责人。主要负责保存好备忘录对象，但是不能对备忘录对象的内容进行操作或检查。
     */
    public static class CareTaker {
        private List<Memento> stateList;

        public CareTaker() {
            stateList = new ArrayList<>();
        }

        public void add(Memento memento) {
            stateList.add(memento);
        }

        public Memento get(int index) {
            if (index < 0 || index >= stateList.size()) {
                return null;
            }
            return stateList.get(index);
        }
    }

    /**
     * 备忘录角色
     * 负责存储原发器对象的内部状态，但是具体需要存储哪些状态是由原发器对象来决定的。
     * 另外备忘录应该只能由原发器对象来访问它内部的数据，原发器外部的对象不应该访问到备忘录对象的内部数据。
     * <p>
     * 为了控制对备忘录对象的访问，备忘录模式中出现了窄接口和宽接口的概念。
     * • 窄接口：管理者只能看到备忘录的窄接口，窄接口的实现中通常没有任何的方法，只是一个类型标识。窄接口使得管理者只能将备忘录传递给其他对象。
     * • 宽接口：原发器能够看到备忘录的宽接口，从而可以从备忘录中获取到所需的数据，来将自己恢复到备忘录中所保存的状态。
     */
    public interface Memento {

    }

    private static class MementoImp implements Memento {
        private String state;

        public MementoImp(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

    }

    public static void test() {
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();

        //模拟游戏存档
        //1、通关第一关
        originator.setState("stage 1");
        //存档
        careTaker.add(originator.saveMemento());

        //2、通关第二关
        originator.setState("stage 2");
        //存档
        careTaker.add(originator.saveMemento());

        //3、通关第三关
        originator.setState("stage 3");
        //存档
        careTaker.add(originator.saveMemento());

        //退出游戏
        //...
        //再次进入游戏
        //想再次玩一下第二关
        originator.restore(careTaker.get(1));

        System.out.println(originator.getState());

        //注意原发器的创建备忘录方法可以使用原型模式代替

    }


}

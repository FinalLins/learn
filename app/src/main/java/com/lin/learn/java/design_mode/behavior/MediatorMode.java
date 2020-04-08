package com.lin.learn.java.design_mode.behavior;

/**
 * 中介者模式将多对多相互作用转化为一对多的相互作用。将对象的协作抽象化。
 */
public class MediatorMode {
    public static abstract class Person {
        public Mediator mediator;

        public Person(Mediator mediator) {
            this.mediator = mediator;
        }

        public abstract void action();

        public void put(){
            mediator.method(this);
        }
    }

    public static class Software extends Person {

        public Software(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void action() {
            System.out.println("程序员：疯狂撸码...");
        }

        @Override
        public void put() {
            System.out.println("程序员：程序实现不了，让产品经理改需求");
            super.put();
        }
    }

    public static class ProductManager extends Person {

        public ProductManager(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void action() {
            System.out.println("产品经理：修改需求...");
        }

        @Override
        public void put() {
            System.out.println("产品经理：这版UI用户体验有点差，改UI");
            super.put();
        }
    }

    public static class UI extends Person {

        public UI(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void action() {
            System.out.println("UI：修改UI...");
        }

        @Override
        public void put() {
            System.out.println("UI：程序太卡了，改程序");
            super.put();
        }
    }

    public interface Mediator {
        void method(Person person);
    }

    /**
     * Leader起串联协调作用
     */
    public static class Leader implements Mediator {
        Person software, ui, pm;

        @Override
        public void method(Person person) {
            if ((System.currentTimeMillis() & 1) == 1) {
                System.out.println("Leader：驳回");
                return;
            }
            System.out.println("Leader：同意");
            if (person instanceof Software) {
                pm.action();
            } else if (person instanceof UI) {
                software.action();
            } else if (person instanceof ProductManager) {
                ui.action();
            }
        }

        public void setSoftware(Person software) {
            this.software = software;
        }

        public void setUi(Person ui) {
            this.ui = ui;
        }

        public void setPm(Person pm) {
            this.pm = pm;
        }

    }

    public static void test() {
        Leader leader = new Leader();

        Person ui = new UI(leader);
        Person sw = new Software(leader);
        Person pm = new ProductManager(leader);
        leader.setPm(pm);
        leader.setSoftware(sw);
        leader.setUi(ui);

        Person[] p = {ui, sw, pm};
        for (int i = 0; i < 6; i++) {
            p[i % 3].put();
            System.out.println();
        }
    }


}

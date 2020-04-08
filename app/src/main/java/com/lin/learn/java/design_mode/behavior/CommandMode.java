package com.lin.learn.java.design_mode.behavior;

/**
 * 命令模式
 * 将“行为请求者”与“行为实现者”解耦，将一组行为抽象为对象，实现二者之间的松耦合。
 */
public class CommandMode {

    public static class Invoker {
        private Command command;

        public Invoker(Command command) {
            this.command = command;
        }

        public void action() {
            if (this.command == null) {
                return;
            }
            this.command.execute();
        }
    }

    public static class Receiver {
        public void skill1() {
            System.out.println("1技能");
        }

        public void skill2() {
            System.out.println("2技能");
        }

        public void skill3() {
            System.out.println("3技能");
        }

    }

    public static abstract class Command {
        protected Receiver receiver;

        public Command(Receiver receiver) {
            this.receiver = receiver;
        }

        public abstract void execute();
    }

    public static class Skill1Command extends Command {

        public Skill1Command(Receiver receiver) {
            super(receiver);
        }

        @Override
        public void execute() {
            receiver.skill1();
        }
    }

    public static class Skill2Command extends Command {

        public Skill2Command(Receiver receiver) {
            super(receiver);
        }

        @Override
        public void execute() {
            receiver.skill2();
        }
    }

    public static class Skill3Command extends Command {

        public Skill3Command(Receiver receiver) {
            super(receiver);
        }

        @Override
        public void execute() {
            receiver.skill3();
        }
    }

    public static void test() {
        Receiver receiver = new Receiver();
        Skill1Command skill1Command = new Skill1Command(receiver);
        Skill2Command skill2Command = new Skill2Command(receiver);
        Skill3Command skill3Command = new Skill3Command(receiver);

        Invoker invoker = new Invoker(skill1Command);
        invoker.action();
        invoker = new Invoker(skill2Command);
        invoker.action();
        invoker = new Invoker(skill3Command);
        invoker.action();
    }
}

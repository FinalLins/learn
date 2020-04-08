package com.lin.learn.java.design_mode.behavior;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态模式
 * 允许对象在内部状态改变时改变它的行为，对象看起来好像修改了它的类。
 * 1、策略模式的侧重点是提供不同的方法(策略)。(每个策略都可以进行计算)
 * 2、状态模式的行为是由状态来决定，不同状态有不同的行为。（适用对应的状态）
 */
public class State {
    public interface SocketState {
        void process(Socket socket);

        int INIT = 1;
        int CONNECT = 2;
        int RECONNECT = 3;
        int WORK = 4;
        int CLOSE = 5;
    }

    public static class InitState implements SocketState {

        @Override
        public void process(Socket socket) {
            System.out.println("socket 初始化");
            socket.setState(SocketState.CONNECT);
            socket.refreshState();
        }
    }

    public static class ConnectState implements SocketState {

        @Override
        public void process(Socket socket) {
            System.out.println("socket 连接中...");
            if (socket.isConnect()) {
                socket.setState(SocketState.WORK);
            } else {
                socket.setState(SocketState.RECONNECT);
            }
            socket.refreshState();
        }
    }

    public static class ReConnectState implements SocketState {

        @Override
        public void process(Socket socket) {

            System.out.println("socket 连接失败，重连中...");
            socket.setState(SocketState.CLOSE);
            socket.refreshState();

            socket.setState(SocketState.INIT);
            socket.refreshState();
        }
    }

    public static class WorkState implements SocketState {

        @Override
        public void process(Socket socket) {
            System.out.println("socket 工作中...");
        }
    }

    public static class CloseState implements SocketState {

        @Override
        public void process(Socket socket) {
            System.out.println("socket 断开/清理资源中...");
        }
    }


    public static class Socket {
        private Map<Integer, SocketState> stateMap = new HashMap<>();

        private SocketState state;

        public Socket() {
            stateMap.put(SocketState.INIT, new InitState());
            stateMap.put(SocketState.CONNECT, new ConnectState());
            stateMap.put(SocketState.RECONNECT, new ReConnectState());
            stateMap.put(SocketState.WORK, new WorkState());
            stateMap.put(SocketState.CLOSE, new CloseState());
        }

        public void start() {
            setState(SocketState.INIT);
            refreshState();
        }

        public void stop() {
            setState(SocketState.CLOSE);
            refreshState();
        }

        public void setState(int state) {
            if (!stateMap.containsKey(state)) {
                return;
            }
            this.setState(stateMap.get(state));
        }

        public void setState(SocketState state) {
            this.state = state;
        }

        public void refreshState() {
            if (this.state != null) {
                this.state.process(this);
            }
        }

        /**
         * 不用纠结，模拟socket链接状态
         *
         * @return
         */
        public boolean isConnect() {
            return (System.currentTimeMillis() & 1) == 1;
        }
    }

    public static void test() {


        try {
            Socket socket = new Socket();
            socket.start();
            Thread.sleep(2000);
            System.out.println("模拟工作完成");
            socket.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

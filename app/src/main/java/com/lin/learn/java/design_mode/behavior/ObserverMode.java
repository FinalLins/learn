package com.lin.learn.java.design_mode.behavior;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * 定义一个被观察者和多个观察者，每当被观察者变化，所有观察者都会得到通知。
 */
public class ObserverMode {

    public static class Weather {

        private String name;
        private float temperature;

        public Weather(String name, float temperature) {
            this.name = name;
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return name + ", 温度 : " + temperature;
        }
    }

    public interface Observer {
        void change(Object object);
    }

    public static class WeatherBureau {
        private List<Observer> observerList = new ArrayList<>();

        public void addObserver(Observer observer) {
            this.observerList.add(observer);
        }

        public void publish(Weather weather) {
            for (Observer observer : this.observerList) {
                if (observer != null) observer.change(weather);
            }
        }
    }



    public static void test() {
        WeatherBureau observable = new WeatherBureau(); //定义被观察者

        Observer person1 = object -> {
            if (object instanceof Weather) {
                Weather weather = (Weather) object;
                if (weather.name.equals("下雨")) {
                    System.out.println("下雨啦，收衣服啦");
                }
            }
        };

        Observer person2 = object -> {
            if (object instanceof Weather) {
                Weather weather = (Weather) object;
                if (weather.name.equals("刮风")) {
                    System.out.println("刮风啦，赶紧回家");
                }
            }
        };

        observable.addObserver(person1);
        observable.addObserver(person2);

        //下雨了，气象局发布新的天气预报，订阅了气象局的人就会收到通知
        observable.publish(new Weather("下雨",23f));

        //刮风了，气象局发布新的天气预报，订阅了气象局的人就会收到通知
        observable.publish(new Weather("刮风",19f));
    }


}

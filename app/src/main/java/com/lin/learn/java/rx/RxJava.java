package com.lin.learn.java.rx;

public class RxJava {

    public static void story1() {
        Observable<String> observable = Observable.create(subscribe -> {
            subscribe.onNext("abcdefg");
        });

        observable.subscribe(new Subscribe<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
                System.out.println("开始打铁");
            }
        });
    }

    public static void story2() {
        Observable<String> observable = Observable.create(subscribe -> {
            System.out.println("1111111");
            subscribe.onNext("abcdefg");
        });

        Observable<Integer> observable2 = observable.map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                System.out.println("2222222");
                return s.length();
            }
        });

        observable2.subscribe(new Subscribe<Integer>() {
            @Override
            public void onNext(Integer integer) {
                System.out.println("333333");
                System.out.println("len" + integer);
            }
        });
    }
}

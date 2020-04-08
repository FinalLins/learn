package com.lin.learn.java.rx;


public class Observable<T> {

    private OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public void subscribe(Subscribe<? super T> subscribe) {
        this.onSubscribe.call(subscribe);
    }

    public <R> Observable<R> map(Func1<? super T, ? extends R> func) {
        return lift(new OperatorMap<>(func));
    }

    private <R> Observable<R> lift(OperatorMap<T, R> map) {
        return new Observable<>(new OnSubscribeLift<>(onSubscribe, map));
    }
}

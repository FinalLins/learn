package com.lin.learn.java.rx;

/**
 * @param <T>
 * @param <R>
 */
public class OnSubscribeLift<T, R> implements OnSubscribe<R> {

    private OnSubscribe<T> parent;

    private Operator<? extends R, ? super T> operator;

    public OnSubscribeLift(OnSubscribe<T> parent,
                           Operator<? extends R, ? super T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override
    public void call(Subscribe<? super R> subscribe) {
        System.out.println("1");
        Subscribe<? super T> st = operator.call(subscribe);
        System.out.println("3");
        parent.call(st);
    }
}

package com.lin.learn.java.rx;

/**
 * 铁匠
 *
 * @param <T> 打铁
 */
public abstract class Subscribe<T> {
    public abstract void onNext(T t);
}

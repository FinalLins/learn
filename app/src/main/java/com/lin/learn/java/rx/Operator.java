package com.lin.learn.java.rx;

public interface Operator<T, R> extends
        Func1<Subscribe<? super T>, Subscribe<? super R>> {
}

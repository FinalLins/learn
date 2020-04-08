package com.lin.learn.java.rx;

public class OperatorMap<T, R> implements Operator<R, T> {
    /**
     * TR变换
     */
    private Func1<? super T, ? extends R> transform;

    public OperatorMap(Func1<? super T, ? extends R> transform) {
        this.transform = transform;
    }

    @Override
    public Subscribe<? super T> call(Subscribe<? super R> subscribe) {
        System.out.println("2");
        return new MapSubscribe<>(subscribe, transform);
    }

    private static class MapSubscribe<T, R> extends Subscribe<T> {

        private Subscribe<? super R> actual;

        private Func1<? super T, ? extends R> transform;

        public MapSubscribe(Subscribe<? super R> actual,
                            Func1<? super T, ? extends R> transform) {
            this.actual = actual;
            this.transform = transform;
        }

        @Override
        public void onNext(T t) {
            R r = transform.call(t);
            actual.onNext(r);
        }
    }
}

package com.lin.learn.java.design_mode.behavior;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式
 * 提供一种方法顺序访问（遍历）一个聚合对象中的每个元素，而不暴露该对象的内部细节。
 */
public class IteratorMode {

    public interface Iterator<E> {

        boolean hasNext();

        E next();
    }

    public static class IteratorImp<E> implements Iterator<E> {

        private List<E> list;
        private int currentIndex = 0;

        public IteratorImp(List<E> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return list != null && list.size() > currentIndex;
        }

        @Override
        public E next() {
            if (hasNext()) {
                return list.get(currentIndex++);
            }
            return null;
        }
    }

    public interface Container<E> {
        void add(E e);

        void remove(E e);

        Iterator<E> iterator();
    }

    public static class ContainerImp<E> implements Container<E> {

        private List<E> list;

        public ContainerImp() {
            list = new ArrayList<>();
        }

        @Override
        public void add(E e) {
            list.add(e);
        }

        @Override
        public void remove(E e) {
            list.remove(e);
        }

        @Override
        public Iterator<E> iterator() {
            return new IteratorImp<>(list);
        }
    }

    public static void test() {
        Container<String> container = new ContainerImp<>();
        for (int i = 0; i < 10; i++) {
            container.add("index_" + i);
        }

        Iterator iterator = container.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

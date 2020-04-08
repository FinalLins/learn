package com.lin.learn.java.design_mode.structure;

import android.view.View;
import android.view.ViewGroup;

/**
 * 适配器模式
 * 将一个类的接口转换成客户希望的另一个接口。适配器模式让那些接口不兼容的类可以一起工作
 * （将两个不兼容的类融合在一起。通过转换使他们可以兼容的工作。）
 */
public class AdapterMode {
    public static class ListView {
        private Adapter adapter;

        public void setAdapter(Adapter adapter) {
            this.adapter = adapter;
        }
    }

    public interface BaseAdapter {
        View getView(int position, View convertView, ViewGroup parent);
    }

    public static class Adapter implements BaseAdapter{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

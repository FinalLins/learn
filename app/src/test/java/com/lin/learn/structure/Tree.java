package com.lin.learn.structure;

import com.lin.learn.java.structure.tree.MyAVLTree;
import com.lin.learn.java.structure.tree.MyBinaryTree;
import com.lin.learn.java.structure.tree.MyHuffmanTree;
import com.lin.learn.java.structure.tree.MyRBTree;
import com.lin.learn.java.structure.tree.MySearchBinaryTree;

import org.junit.Test;

public class Tree {

    /**
     * 一般二叉树
     */
    @Test
    public void testBinaryTree() {
        MyBinaryTree.test();
    }

    @Test
    public void testSearchBinaryTree() {
        MySearchBinaryTree.test();
    }

    @Test
    public void testHuffmanTree() {
        MyHuffmanTree.test();
    }

    @Test
    public void testAvlTree() {
        MyAVLTree.test();
    }

    @Test
    public void testRBTree() {
        MyRBTree.test();
    }
}

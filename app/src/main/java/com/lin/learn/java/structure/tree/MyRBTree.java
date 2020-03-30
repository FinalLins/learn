package com.lin.learn.java.structure.tree;

import android.graphics.Bitmap;

import java.util.Stack;
import java.util.TreeMap;

/**
 * 自定义红黑树
 * 1.根节点是黑色
 * 2.节点非红必黑
 * 3.每个叶子节点都是黑色的空节点
 * 4.如果一个节点是红色，它的子节点必然是黑色
 * 5.任意一个节点，从该节点到其下面的任意一个叶子节点的路径包含相同的黑色节点数
 *
 * @param <E>
 */
public class MyRBTree<E extends Comparable<E>> {

    private Node<E> root;
    private int size;

    public void add(E e) {
        if (root == null) {
            root = new Node<>(e);
            setColor(root, Node.BLACK);//跟节点是黑色
            size++;
            return;
        }

        //按照二叉排序树的特性插入节点
        Node<E> node = root;
        Node<E> parent;
        int cmp;
        do {
            parent = node;
            cmp = e.compareTo(node.item);
            if (cmp < 0) {
                node = node.leftChild;
            } else if (cmp > 0) {
                node = node.rightChild;
            } else {
                return;
            }
        } while (node != null);
        node = new Node<>(e);
        if (cmp < 0) {
            parent.leftChild = node;
        } else {
            parent.rightChild = node;
        }
        node.parent = parent;
        //根据红黑树的特性调整树结构
        fixAfterInsertion(node);
        size++;
    }

    private void fixAfterInsertion(Node<E> node) {
        //1.插入的节点先染成红色
        setColor(node, Node.RED);
        //2.父节点是黑色则不需要处理
        while (node != null &&
                node != root &&
                colorOf(parentOf(node)) == Node.RED) {

            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) { //如果父节点是祖父节点的左孩子
                //叔叔节点必然就是祖父节点的右孩子
                Node<E> uncle = rightOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == Node.RED) { //如果叔叔节点是红色
                    //此时父节点和叔叔节点都是红色
                    //把父节点和叔叔节点涂黑
                    setColor(parentOf(node), Node.BLACK);
                    setColor(uncle, Node.BLACK);
                    //把祖父节点涂红
                    setColor(parentOf(parentOf(node)), Node.RED);
                    //向上继续调整
                    node = parentOf(parentOf(node));
                } else { //如果叔叔节点不存在或者叔叔节点是黑色
                    //如果当前节点是父节点的右孩子
                    //先看当前节点是不是父节点的右孩子
                    if (node == rightOf(parentOf(node))) {
                        //当前节点指向父节点然后左旋
                        node = parentOf(node);
                        leftRotate(node);
                    }
                    //父节点涂黑，祖父节点涂红，然后对祖父节点进行右旋
                    setColor(parentOf(node), Node.BLACK);
                    setColor(parentOf(parentOf(node)), Node.RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            } else { //如果父节点是祖父节点的右孩子,左旋和右旋跟上面相反即可
                //那么叔叔节点必然就是祖父节点的左孩子
                Node<E> uncle = leftOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == Node.RED) {
                    setColor(parentOf(node), Node.BLACK);
                    setColor(uncle, Node.BLACK);
                    setColor(parentOf(parentOf(node)), Node.RED); //父节点和叔叔节点是红色，祖父节点必然是黑色
                    node = parentOf(parentOf(node));
                } else {
                    //叔叔节点是黑色
                    //先看当前节点是不是父节点的左孩子
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rightRotate(node);
                    }
                    //父节点涂黑，祖父节点涂红，然后对祖父节点进行左旋
                    setColor(parentOf(node), Node.BLACK);
                    setColor(parentOf(parentOf(node)), Node.RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        setColor(root, Node.BLACK);
    }

    /**
     * 左旋
     *
     * @param node
     */
    private void leftRotate(Node<E> node) {
        if (node == null) {
            return;
        }

        Node<E> rightChild = rightOf(node);
        if (rightChild == null) {
            return;
        }

        node.rightChild = rightChild.leftChild;
        if (rightChild.leftChild != null) {
            rightChild.leftChild.parent = node;
        }

        Node<E> parent = parentOf(node);
        if (parent == null) {
            root = rightChild;
        } else if (leftOf(parent) == node) {
            parent.leftChild = rightChild;
        } else if (rightOf(parent) == node) {
            parent.rightChild = rightChild;
        }
        rightChild.parent = parent;

        rightChild.leftChild = node;
        node.parent = rightChild;

    }

    /**
     * 右旋
     *
     * @param node
     */
    private void rightRotate(Node<E> node) {
        if (node == null) {
            return;
        }

        Node<E> leftChild = leftOf(node);
        if (leftChild == null) {
            return;
        }

        node.leftChild = leftChild.rightChild;
        if (leftChild.rightChild != null) {
            leftChild.rightChild.parent = node;
        }

        Node<E> parent = parentOf(node);
        if (parent == null) {
            root = leftChild;
        } else if (leftOf(parent) == node) {
            parent.leftChild = leftChild;
        } else if (rightOf(parent) == node) {
            parent.rightChild = leftChild;
        }
        leftChild.parent = parent;

        leftChild.rightChild = node;
        node.parent = leftChild;
    }


    public void remove(E e) {
        Node<E> p = node(e);
        if (p == null) {
            return;
        }

        //p has 2 children
        if (p.leftChild != null && p.rightChild != null) {
            Node<E> s = getRightTreeMin(p);
            p.item = s.item;
            p = s;
        }

        //1.如果要删除的节点有左右孩子，p就是右子树最左(小)节点，replace就是p.right
        //2.如果要删除的节点只有一个孩子，p就是被删除的节点，replace就是左孩子或者右孩子
        Node<E> replace = (p.leftChild != null ? p.leftChild : p.rightChild);
        if (replace != null) {
            if (p.parent == null) {
                root = replace;
            } else if (p.parent.leftChild == p) {
                p.parent.leftChild = replace;
            } else if (p.parent.rightChild == p) {
                p.parent.rightChild = replace;
            }
            replace.parent = p.parent;
            p.parent = p.leftChild = p.rightChild = null;

            if (colorOf(p) == Node.BLACK)
                fixAfterDeletion(replace);
        } else if (p.parent == null) { //删除的是root节点
            root = null;
        } else {
            if (colorOf(p) == Node.BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.leftChild) {
                    p.parent.leftChild = null;
                } else if (p == p.parent.rightChild) {
                    p.parent.rightChild = null;
                }
                p.parent = null;
            }
        }

    }


    private void fixAfterDeletion(Node<E> node) {
        while (node != root && colorOf(node) == Node.BLACK) {
            if (node == leftOf(parentOf(node))) {//当前节点是父节点的左孩子
                //那么兄弟节点必然就是父节点的右孩子
                Node<E> sib = rightOf(parentOf(node));

                //a.如果兄弟节点是红色
                if (colorOf(sib) == Node.RED) {
                    //1.把兄弟节点涂黑
                    setColor(sib, Node.BLACK);
                    //2.把父节点涂红
                    setColor(sib, Node.RED);
                    //3.对父节点进行左旋
                    leftRotate(parentOf(node));
                    //4.刷新兄弟节点
                    sib = rightOf(parentOf(node));
                }

                //b.对兄弟节点的左右孩子进行判断
                if (colorOf(leftOf(sib)) == Node.BLACK &&
                        colorOf(rightOf(sib)) == Node.BLACK) {//b1.如果兄弟节点的左右孩子都是黑色
                    //1把兄弟节点涂红
                    setColor(sib, Node.RED);
                    //2当前节点指向父节点
                    node = parentOf(node);
                } else { //b2
                    //a 兄弟节点左孩子红色，右孩子红色
                    //b 兄弟节点左孩子红色，右孩子黑色
                    //c 兄弟节点左孩子黑色，右孩子黑色

                    //1.如果兄弟节点的右孩子是黑色 b,c两种情况
                    if (colorOf(rightOf(sib)) == Node.BLACK) {
                        //1.1兄弟节点的左孩子涂黑
                        setColor(leftOf(sib), Node.BLACK);
                        //1.2兄弟节点涂红
                        setColor(sib, Node.RED);
                        //1.3对兄弟节点记性右旋
                        rightRotate(sib);
                        //1.4刷新兄弟节点
                        sib = rightOf(parentOf(node));
                    }

                    //2.把兄弟节点涂成父节点颜色
                    setColor(sib, colorOf(parentOf(node)));
                    //3.把父节点涂黑
                    setColor(parentOf(node), Node.BLACK);
                    //4.把兄弟节点的右孩子涂黑
                    setColor(rightOf(sib), Node.BLACK);
                    //5.对付节点进行左旋
                    leftRotate(parentOf(node));
                    //6.把当前节点指向根节点
                    node = root;//说明算法结束了
                }
            } else { //当前节点是父节点的右孩子
                //那么兄弟节点就是父节点的左孩子
                Node<E> sib = leftOf(parentOf(node));

                if (colorOf(sib) == Node.RED) {
                    setColor(sib, Node.BLACK);
                    setColor(parentOf(node), Node.RED);
                    rightRotate(parentOf(node));
                    sib = leftOf(parentOf(node));
                }

                if (colorOf(leftOf(sib)) == Node.BLACK &&
                        colorOf(rightOf(sib)) == Node.BLACK) {
                    setColor(sib, Node.RED);
                    node = parentOf(node);
                } else {
                    if (colorOf(leftOf(sib)) == Node.BLACK) {
                        setColor(rightOf(sib), Node.BLACK);
                        setColor(sib, Node.RED);
                        leftRotate(sib);
                        sib = leftOf(parentOf(node));
                    }
                    setColor(sib, colorOf(parentOf(node)));
                    setColor(parentOf(node), Node.BLACK);
                    setColor(leftOf(sib), Node.BLACK);
                    rightRotate(parentOf(node));
                    node = root;
                }
            }
        }
        setColor(node, Node.BLACK);
    }

    private Node<E> getRightTreeMin(Node<E> node) {
        Node<E> p = node.rightChild;
        while (p.leftChild != null) {
            p = p.leftChild;
        }
        return p;
    }

    private Node<E> node(E e) {
        int cmp;
        Node<E> node = root;
        while (node != null) {
            cmp = e.compareTo(node.item);
            if (cmp == 0) return node;
            if (cmp < 0) node = node.leftChild;
            else node = node.rightChild;
        }
        return null;
    }

    private int colorOf(Node<E> node) {
        return node == null ? Node.BLACK : node.color;
    }

    private void setColor(Node<E> node, int color) {
        if (node == null) return;
        node.color = color;
    }

    /**
     * 获取父节点
     *
     * @param node
     * @return
     */
    private Node<E> parentOf(Node<E> node) {
        return node == null ? null : node.parent;
    }

    /**
     * 获取左孩子节点
     *
     * @param node
     * @return
     */
    private Node<E> leftOf(Node<E> node) {
        return node == null ? null : node.leftChild;
    }

    /**
     * 获取右孩子节点
     *
     * @param node
     * @return
     */
    private Node<E> rightOf(Node<E> node) {
        return node == null ? null : node.rightChild;
    }

    public static class Node<E extends Comparable<E>> {
        private static final int BLACK = 0;
        private static final int RED = 1;

        int color;
        E item;
        Node<E> parent;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E item) {
            this.item = item;
        }
    }

    public static <E extends Comparable<E>> void midTraverse(Node<E> node) {
        if (node == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> cur = node;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.leftChild;
            } else {
                cur = stack.pop();
                System.out.print("->(" + cur.item + "," + cur.color + ")");
                cur = cur.rightChild;
            }
        }
        System.out.println();
    }

    public static void test() {
        MyRBTree<Integer> tree = new MyRBTree<>();
        tree.add(5);
        tree.add(6);
        tree.add(4);
        midTraverse(tree.root);
        tree.add(7);
        midTraverse(tree.root);

        tree.remove(5);
        midTraverse(tree.root);

    }
}

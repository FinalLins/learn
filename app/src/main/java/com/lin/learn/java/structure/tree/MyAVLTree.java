package com.lin.learn.java.structure.tree;

import java.util.Stack;

/**
 * 自定义AVL树
 */
public class MyAVLTree<E extends Comparable<E>> {

    private static final int LH = 1;
    private static final int RH = -1;
    private static final int EH = 0;

    private Node<E> root;

    public void add(E e) {
        //把节点插入到正确的位置
        Node<E> node = insertNode(e);
        fixAfterInsertion(node);
    }

    /**
     * 用遍历的方式计算某个节点的平衡因子
     *
     * @param node
     */
    public void countBalance(Node<E> node) {
        if (node == null) return;
        Stack<Node<E>> stack = new Stack<>();

    }

    public Node<E> search(E e) {
        Node<E> node = root;
        while (node != null) {
            int comp = e.compareTo(node.item);
            if (comp == 0) {
                break;
            } else if (comp < 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return node;
    }

    public void remove(E e) {
        Node<E> node = search(e);
        if (node == null) {
            return;
        }
        Node<E> leftChild = node.leftChild, rightChild = node.rightChild;
        Node<E> fixedNode = node.parent;
        if (leftChild == null && rightChild == null) { //1.如果没有左子树也没有右子树，说明要删除的是叶子节点
            releaseNode(node);
            //叶子节点，直接向上调整父节点即可
        } else if (leftChild != null && rightChild == null) { //2.如果只有左子树的情况
            pointerParent(node, leftChild);
            //删除前node的左子树肯定是平衡的，所以只需要从node.parent开始调整即可
        } else if (leftChild == null) { //3.如果只有右子树的情况
            pointerParent(node, rightChild);
            //删除前node的右子树肯定是平衡的，所以只需要从node.parent开始调整即可
        } else {    //4.左右子树都存在的情况
            //找到右子树最小的节点
            Node<E> minNode = rightChild;
            while (minNode.leftChild != null) {
                minNode = minNode.leftChild;
            }

            //如果右子树最左的节点（最小）有右子树的情况，把他的右子树赋值给他父节点的左子树
            if (minNode.rightChild != null) {
                minNode.parent.leftChild = minNode.rightChild;
            }

            minNode.leftChild = leftChild;
            if (minNode == rightChild) { //右孩子没有左子树(node.rightChild.leftChild == null)
                fixedNode = minNode;
            } else {
                //如果右孩子有左子树，那么就要从右孩子左子树最小节点minNode的parent开始向上调整
                fixedNode = minNode.parent;
                minNode.rightChild = rightChild;
            }
            pointerParent(node, minNode);
        }

        fixAfterDeletion(fixedNode, node.item);
        node.parent = node.leftChild = node.rightChild = null;
    }

    private Node<E> insertNode(E e) {
        Node<E> newNode = new Node<>(e);
        if (root == null) {
            root = newNode;
            return newNode;
        }

        //找到要插入的位置
        Node<E> node = root;
        Node<E> parent = null;
        while (node != null) {
            parent = node;
            if (e.compareTo(node.item) < 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }

        }
        //关联节点
        if (e.compareTo(parent.item) < 0) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }
        newNode.parent = parent;
        return newNode;
    }

    private void fixBalance(Node<E> node) {
        if (node.balance == 2) {
            leftBalance(node);
        } else if (node.balance == -2) {
            rightBalance(node);
        }
    }

    private void fixAfterInsertion(Node<E> node) {
        if (node == null) {
            return;
        }
        Node<E> parent = node.parent;
        int cmp;
        while (parent != null) {
            //先上调整平衡因子
            cmp = node.item.compareTo(parent.item);
            if (cmp < 0) {
                parent.balance++;
            } else {
                parent.balance--;
            }

            //说明插入节点后，树还是平衡的，不影响树的结构
            if (parent.balance == EH) {
                break;
            }

            //找到最小不平衡树，进行调整
            if (Math.abs(parent.balance) == 2) {
                fixBalance(parent);
                break;
            }

            parent = parent.parent;
        }

    }

    private void fixAfterDeletion(Node<E> node, E e) {
        Node<E> parent = node;
        int cmp;
        while (parent != null) {
            cmp = e.compareTo(parent.item);
            if (cmp < 0) {
                parent.balance--;
            } else {
                parent.balance++;
            }

            //调整平衡因子后，如果是1或者-1，说明调整前是0，停止回溯
            if (Math.abs(parent.balance) == 1) {
                break;
            }

            if (Math.abs(parent.balance) == 2) {
                //不知道理解的有没有问题，删除后造成不平衡，那么直接影响的肯定是删除前node的父节点parent的平衡。
                //调整后那么，父节点这个不平衡子树，肯定是平衡了，但是parent已经不在之前的位置上面了。
                //所以记录原先的parent的parent给pp，调整它后pp肯定和parent.parent指向不同的节点
                //应该是parent.parent.parent == pp，按照不同情况分类推理好像是这么个结果
                Node<E> pp = parent.parent;
                fixBalance(parent);
                parent = pp;
            } else {
                parent = parent.parent;
            }
        }
    }

    /**
     * 左平衡操作
     * 说明左子树过高。分为两种情况：
     * 1.最小不平衡子树node的左孩子的左子树过高，直接对node进行右旋和调整平衡因子即可。
     * node.b = node.leftChild.b = EH
     * 2.最小不平衡子树node的左孩子的右子树过高，则先对node.leftChild进行左旋后再对node进行右旋。
     * 然后针对node.leftChild.rightChild(最小不平衡子树node的左孩子的右子树)的情况进行平衡因子的调整。
     * 3.最小不平衡子树node的左孩子的左右子树一样高（删除的情况下会出现），直接对node进行右旋和调整平衡因子即可。
     * node.b = LH; node.leftChild.b = RH
     */
    private void leftBalance(Node<E> node) {

        if (node == null) {
            return;
        }

        Node<E> leftChild = node.leftChild;
        switch (leftChild.balance) {
            case LH:
                node.balance = leftChild.balance = EH;
                rightRotate(node);
                break;
            case RH:
                Node<E> nlr = leftChild.rightChild;
                switch (nlr.balance) {
                    case LH:
                        node.balance = RH;
                        leftChild.balance = EH;
                        break;
                    case RH:
                        node.balance = EH;
                        leftChild.balance = LH;
                        break;
                    case EH:
                        node.balance = EH;
                        leftChild.balance = EH;
                        break;
                }
                nlr.balance = EH;
                leftRotate(leftChild);
                rightRotate(node);
                break;
            case EH://删除的情况下会出现
                node.balance = LH;
                leftChild.balance = RH;
                rightRotate(node);
        }

    }

    /**
     * 右平衡操作
     */
    private void rightBalance(Node<E> node) {

        if (node == null) {
            return;
        }
        Node<E> rightChild = node.rightChild;
        switch (rightChild.balance) {
            case LH:
                Node<E> nrl = rightChild.leftChild;
                switch (nrl.balance) {
                    case LH:
                        node.balance = EH;
                        rightChild.balance = RH;
                        break;
                    case RH:
                        node.balance = LH;
                        rightChild.balance = EH;
                        break;
                    case EH:
                        node.balance = rightChild.balance = EH;
                        break;
                }
                nrl.balance = EH;
                rightRotate(rightChild);
                leftRotate(node);
                break;
            case RH:
                node.balance = EH;
                rightChild.balance = EH;
                leftRotate(node);
                break;
            case EH:
                node.balance = RH;
                rightChild.balance = LH;
                leftRotate(node);
                break;

        }
    }

    /**
     * 左旋
     */
    private void leftRotate(Node<E> node) {
        if (node == null) {
            return;
        }

        Node<E> rightChild = node.rightChild;
        if (rightChild == null) {
            //没有右孩子，不能左旋
            return;
        }

        //1.先把右孩子的做孩子赋值给当前节点的右孩子
        node.rightChild = rightChild.leftChild;
        //2.父节点关联
        if (rightChild.leftChild != null) {
            rightChild.leftChild.parent = node;
        }

        //3.右孩子的左孩子指向当前节点（完成右孩子上去，当前节点下来的动作）
        rightChild.leftChild = node;

        //4.当前节点的父节点成为右孩子的父节点
        pointerParent(node, rightChild);
        //5.右孩子成为了当前节点的父节点
        node.parent = rightChild;
    }

    /**
     * 右旋
     */
    private void rightRotate(Node<E> node) {
        if (node == null) {
            return;
        }

        Node<E> leftChild = node.leftChild;
        if (leftChild == null) {
            return;
        }

        //1.把左孩子的右孩子赋值给当前节点的左孩子
        node.leftChild = leftChild.rightChild;
        //2.父节点关联
        if (leftChild.rightChild != null) {
            leftChild.rightChild.parent = node;
        }

        //3.把左孩子的右孩子指向当前节点
        leftChild.rightChild = node;
        //4.当前节点的父节点成为左孩子的新父节点
        pointerParent(node, leftChild);
        //5.左孩子成为当前节点的新父节点
        node.parent = leftChild;
    }

    /**
     * node.parent 和 newNode完成新的关联动作
     *
     * @param node
     * @param newNode
     */
    private void pointerParent(Node<E> node, Node<E> newNode) {
        Node<E> parent = node.parent;
        if (parent == null) {
            root = newNode;
        } else if (parent.leftChild == node) {
            parent.leftChild = newNode;
        } else if (parent.rightChild == node) {
            parent.rightChild = newNode;
        }
        if (newNode != null) newNode.parent = parent;
    }

    private void releaseNode(Node<E> node) {
        pointerParent(node, null);
        node.parent = null;
    }

    public void midTraverse() {
        if (root == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.leftChild;
            } else {
                node = stack.pop();
                System.out.print("-> (" + node.item + "," + node.balance + ")");
                node = node.rightChild;
            }
        }
        System.out.println();
    }

    public static class Node<E extends Comparable<E>> {
        E item;
        Node<E> parent;
        Node<E> leftChild;
        Node<E> rightChild;
        int balance;

        public Node(E item) {
            this.item = item;
            this.balance = 0;
        }
    }

    public static void test() {
        int[] arr = {5, 8, 2, 0, 1, -2, 4, 3, -3, -5};
        MyAVLTree<Integer> avl = new MyAVLTree<>();
        for (int i : arr) {
            avl.add(i);
            avl.midTraverse();
        }
        //(-5,0)-> (-3,1)-> (-2,1)-> (0,0)-> (1,0)-> (2,0)-> (3,0)-> (4,0)-> (5,1)-> (8,0)
        System.out.println(avl.root.item + "," + avl.root.balance);
        avl.remove(0);
        avl.midTraverse();
//        System.out.println(avl.root.item + "," + avl.root.balance);
//        avl.midTraverse();

    }
}

package com.lin.learn.java.structure.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 自定义哈弗曼树
 */
public class MyHuffmanTree<E> {
    private Node<E> root;

    public static <E> void generate(E[] array, int[] weights) {
        if (array == null || array.length == 0) {
            return;
        }
        if (weights == null || weights.length != array.length) {
            return;
        }
        int length = array.length;
        List<Node<E>> list = new ArrayList<>(length);
        MyHuffmanTree<E> myHuffmanTree = new MyHuffmanTree<>();
        for (int i = 0; i < length; i++) {
            list.add(new Node<>(array[i], weights[i]));
        }
        while (list.size() > 1) {
            Collections.sort(list);
            //获取权值最小的两个节点
            Node<E> node1 = list.remove(0);
            Node<E> node2 = list.remove(0);
            Node<E> node = new Node<>(null, node1.weight + node2.weight);
            node.leftChild = node1;
            node.rightChild = node2;
            node1.parent = node;
            node2.parent = node;
            list.add(node);
        }
        myHuffmanTree.root = list.get(0);
        System.out.println(myHuffmanTree.root);
        //深度遍历
    }

    public static class Node<E> implements Comparable<Node> {
        E item;
        int weight;
        Node<E> parent;
        Node<E> leftChild;
        Node<E> rightChild;

        public Node(E item, int weight) {
            this.item = item;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            if (weight == o.weight) {
                return 0;
            } else if (weight > o.weight) {
                return 1;
            }
            return -1;
        }
    }

    public static void test() {
        String[] ss = {"A", "B", "C", "D", "E"};
        int[] weights = {20, 10, 50, 30, 90};
        MyHuffmanTree.generate(ss, weights);
        MyHuffmanTree.huffmanCode();
    }

    public static void huffmanCode() {
        //面试题
        //假设需要把一个字符串，如“abcdabcaba”进行编码，
        //将它转换为唯一的二进制码，但是要求转换出来的二进制码的长度最小。
        String s = "abcdabcaba";
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            Integer w = map.get(c);
            if (w == null) {
                map.put(c, 1);
            } else {
                map.put(c, w + 1);
            }
        }

        List<S_Node> list = new ArrayList<>(map.size());
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            list.add(new S_Node(entry.getKey(), entry.getValue()));
        }

        while (list.size() > 1) {
            Collections.sort(list);
            S_Node s1 = list.remove(0);
            S_Node s2 = list.remove(0);
            S_Node node = new S_Node(' ', s1.w + s2.w);
            node.leftChild = s1;
            node.rightChild = s2;
            s1.parent = node;
            s2.parent = node;
            list.add(node);
        }
        S_Node root = list.get(0);

        //层次遍历
        Queue<S_Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            S_Node sNode = queue.poll();
            System.out.print("->" + sNode.w + "个" + sNode.c);
            if (sNode.leftChild != null) queue.offer(sNode.leftChild);
            if (sNode.rightChild != null) queue.offer(sNode.rightChild);
        }
        System.out.println();

        System.out.println("输出编码");
        for (char c : map.keySet()) {
            System.out.println(c + "--->" + getCode(root, c));
        }
    }

    private static String getCode(S_Node root, char c) {
        S_Node node = searchNode(root, c);
        if (node == null) return null;
        if (node.parent == null) return "0";
        Stack<String> stack = new Stack<>();
        while (node.parent != null) {
            S_Node parent = node.parent;
            //左边为0，右边为1
            if (parent.leftChild == node) {
                stack.push("0");
            } else if (parent.rightChild == node) {
                stack.push("1");
            }
            node = parent;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.toString();
    }

    private static S_Node searchNode(S_Node root, char c) {
        Stack<S_Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            S_Node node = stack.pop();
            if (c == node.c) {
                return node;
            }
            if (node.leftChild != null) stack.push(node.leftChild);
            if (node.rightChild != null) stack.push(node.rightChild);
        }
        return null;
    }

    private static class S_Node implements Comparable<S_Node> {
        char c;
        int w;
        S_Node parent;
        S_Node leftChild;
        S_Node rightChild;

        public S_Node(char c, int w) {
            this.c = c;
            this.w = w;
        }

        @Override
        public int compareTo(S_Node node) {
            if (w > node.w) {
                return 1;
            } else if (w < node.w) {
                return -1;
            }
            return 0;
        }
    }
}

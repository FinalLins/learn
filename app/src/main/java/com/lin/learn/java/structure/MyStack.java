package com.lin.learn.java.structure;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 自定义栈结构
 */
public class MyStack {
    private int[] array;
    private int size;
    private int capacity;

    public MyStack(int capacity) {
        this.capacity = capacity;
        this.array = new int[capacity];
    }

    public void push(int e) {
        if (size >= capacity) {
            return;
        }
        array[size++] = e;
    }

    public int pop() {
        if (size <= 0) throw new EmptyStackException();
        int e = array[size - 1];
        size--;
        return e;
    }

    private static final char add = '+';
    private static final char sub = '-';
    private static final char multi = '*';
    private static final char division = '/';

    private static final char left_parentheses = '(';
    private static final char right_parentheses = ')';
    private static final char well_no = '#';

    private static final char[] operatorIndex = {
            add, sub, multi, division, left_parentheses, right_parentheses, well_no
    };

    // + - * / ( ) #
    private static final char[][] operatorPriority = {
            {'>', '>', '<', '<', '<', '>', '>'},
            {'>', '>', '<', '<', '<', '>', '>'},
            {'>', '>', '>', '>', '<', '>', '>'},
            {'>', '>', '>', '>', '<', '>', '>'},
            {'<', '<', '<', '<', '<', '=', ' '},
            {'>', '>', '>', '>', ' ', '>', '>'},
            {'<', '<', '<', '<', '<', ' ', '='},
    };



    /**
     * 中缀表达式转后缀表达式
     *
     * @param midExpression
     * @return
     */
    public static String convertSuffixExpression(String midExpression) {
        char[] chars = midExpression.toCharArray();
        int length = chars.length;
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            if (Character.isDigit(c)) { //如果是数字
                sb.append(c);
                continue;
            }

            handleStack(sb, stack, c);
        }
        while (!stack.isEmpty()) sb.append(stack.pop());
        return sb.toString();
    }

    private static void handleStack(StringBuilder sb, Stack<Character> stack, char c) {
        if (stack.isEmpty()) {
            stack.push(c);
            return;
        }
        char src = stack.peek();
        char operator = getOperatorPriority(src, c);
        switch (operator) {
            case '>':   //栈顶操作符如果比进栈操作符优先级高，那么栈顶操作符出栈，然后进栈操作符继续跟当前栈顶继续比较（递归比较下去）
                sb.append(stack.pop());
                handleStack(sb, stack, c); //这句是关键
                break;
            case '<':   //栈顶操作符如果比进栈操作符优先级低，那么进栈操作符直接进栈。
                stack.push(c);
                break;
            case '=':   //栈顶操作符如果比进栈操作符优先级一样，两个都丢弃，右括号碰到左括号
                stack.pop();
                break;
        }
    }

    public static int getOperatorIndex(char c) {
        int len = operatorIndex.length;
        for (int i = 0; i < len; i++) {
            if (c == operatorIndex[i]) {
                return i;
            }
        }
        return -1;
    }

    public static char getOperatorPriority(char src, char dst) {
        int sIndex = getOperatorIndex(src);
        int dIndex = getOperatorIndex(dst);
        if (sIndex != -1 && dIndex != -1) {
            return operatorPriority[sIndex][dIndex];
        }

        throw new RuntimeException("illegal operator");
    }


    public static double count(double a, double b, char operator) {
        switch (operator) {
            case add:
                return b + a;
            case sub:
                return b - a;
            case multi:
                return b * a;
            case division:
                return b / a;
        }
        return 0D;
    }

    public static double countSuffixExpression(String suffixExpression) {
        char[] chars = suffixExpression.toCharArray();
        int length = chars.length;
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            if (Character.isDigit(c)) {
                stack.push(Double.parseDouble(String.valueOf(c)));
                continue;
            }
            double a = stack.pop();
            double b = stack.pop();
            double result = count(a, b, c);
            stack.push(result);
        }
        return stack.peek();
    }

    /**
     * 只支持0-9的加减乘除
     */
    public static void test() {
        String s = "(9-5*(3+2))/2";
        String se = convertSuffixExpression(s);
        System.out.println(countSuffixExpression(se));
        se = convertSuffixExpression("9+(3-1)*3+8/2");
        System.out.println(se);
        System.out.println(countSuffixExpression(se));
    }
}

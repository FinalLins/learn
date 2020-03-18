package com.lin.learn;

import java.util.Stack;

/**
 * 实现逆波兰表达式
 * 中缀表达式转后缀表达式
 */
public class StackUse {
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
    private static final char[][] operatorPriorityChars = {
            {'>', '>', '<', '<', '<', '>', '>'},
            {'>', '>', '<', '<', '<', '>', '>'},
            {'>', '>', '>', '>', '<', '>', '>'},
            {'>', '>', '>', '>', '<', '>', '>'},
            {'<', '<', '<', '<', '<', '=', ' '},
            {'>', '>', '>', '>', ' ', '>', '>'},
            {'<', '<', '<', '<', '<', ' ', 's'},
    };

    /**
     * 规则：
     * <p>
     * 栈顶比进栈操作符优先级高，则出栈并且计算
     * 栈顶比进栈操作符优先级低，则入栈
     * 栈顶和进栈操作符一样，则出栈
     */

    /**
     * 中缀表达式转后缀表达式
     *
     * @param operator
     * @return
     */
    public static String convertSuffixExpression(String operator) {
        Stack<Character> stack = new Stack<>();
        char[] chars = operator.trim().toCharArray();

        int length = chars.length, index = 0;
        StringBuilder sb = new StringBuilder();

        while (index != length) {
            char c = chars[index];
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (isOperator(c)) {
                if (stack.empty()) {
                    stack.push(c);
                } else {
                    handlerOperator(c, stack, sb);
                }
            } else {
                throw new RuntimeException("含有非法字符 : " + c + "---" + (int) c);
            }
            index++;
        }
        while (!stack.empty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public static double countSuffixExpression(String suffixExpression) {
        char[] chars = suffixExpression.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int index = 0, length = chars.length;
        while (index != length) {
            char c = chars[index];
            if (Character.isDigit(c)) {
                stack.push(Integer.parseInt(String.valueOf(c)));
            } else {
                Integer a = stack.pop();
                Integer b = stack.pop();
                stack.push(operator(a, b, c));
            }
            index++;
        }
        return stack.peek();
    }

    private static int operator(int a, int b, char operator) {
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
        return 0;
    }

    private static void handlerOperator(char c, Stack<Character> stack, StringBuilder sb) {
        if (stack.empty()) {
            stack.push(c);
            return;
        }
        char last = stack.peek();
        char o = operatorPriority(last, c);
        switch (o) {
            case '>':
                //栈顶比进栈操作符优先级高，则出栈
                sb.append(stack.pop());
                //并且继续跟栈顶比较
                handlerOperator(c, stack, sb);
                break;
            case '<':
                //栈顶比进栈操作符优先级低，则入栈
                stack.push(c);
                break;
            case '=':
                //栈顶和进栈操作符一样，则出栈
                stack.pop();
                break;
        }
    }

    /**
     * 判断是否是操作符
     *
     * @param c
     * @return
     */
    public static boolean isOperator(char c) {
        if (add == c || sub == c || multi == c || division == c ||
                left_parentheses == c || right_parentheses == c ||
                well_no == c) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个操作符优先级
     *
     * @param src
     * @param dst
     * @return {src == dst true} {else false}
     */
    public static char operatorPriority(char src, char dst) {
        int srcIndex = getOperatorIndex(src);
        int dscIndex = getOperatorIndex(dst);
        if (srcIndex != -1 && dscIndex != -1) {
            return operatorPriorityChars[srcIndex][dscIndex];
        }
        throw new RuntimeException("非法运算符 : " + src + "---" + dst);
    }

    private static int getOperatorIndex(char val) {
        int len = operatorIndex.length;
        for (int i = 0; i < len; i++) {
            if (val == operatorIndex[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        String s = "9+(7+(6*5))*2-4";
//        String s = "9+5*(2-4)";
        String s = "(9+5*(3+2))/2";
        String se = convertSuffixExpression(s);
        System.out.println(se);
        System.out.println(countSuffixExpression(se));
//        int h = s.hashCode();
//        int h1 = h >>> 16;
//        System.out.println(h);
//        System.out.println(h1);
//        System.out.println(h ^ h1);
    }

}

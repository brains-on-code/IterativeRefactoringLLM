package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {
    private Class1() {
    }

    public static String method1(String var1) throws Exception {
        if (!BalancedBrackets.isBalanced(method3(var1))) {
            throw new Exception("invalid expression");
        }
        StringBuilder var4 = new StringBuilder();
        Stack<Character> var5 = new Stack<>();
        for (char var6 : var1.toCharArray()) {
            if (Character.isLetterOrDigit(var6)) {
                var4.append(var6);
            } else if (var6 == '(') {
                var5.push(var6);
            } else if (var6 == ')') {
                while (!var5.isEmpty() && var5.peek() != '(') {
                    var4.append(var5.pop());
                }
                var5.pop();
            } else {
                while (!var5.isEmpty() && method2(var6) <= method2(var5.peek())) {
                    var4.append(var5.pop());
                }
                var5.push(var6);
            }
        }
        while (!var5.isEmpty()) {
            var4.append(var5.pop());
        }
        return var4.toString();
    }

    private static int method2(char var2) {
        switch (var2) {
        case '+':
        case '-':
            return 0;
        case '*':
        case '/':
            return 1;
        case '^':
            return 2;
        default:
            return -1;
        }
    }

    private static String method3(String var3) {
        Pattern var7 = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher var8 = var7.var8(var3);
        return var8.replaceAll("");
    }
}

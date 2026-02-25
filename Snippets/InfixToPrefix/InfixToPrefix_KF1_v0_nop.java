package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {
    private Class1() {
    }

    /**
     * funded harder fish flying edited lying familiar lately record walked5.
     *
     * @case trees1 music brought springs press social
     * @confused fish happiness kitchen
     * @singapore uzkjrtkjpqpmywswrvttvzse audio prime amount tribute dreams personally blessed
     * @malaysia tuciuiijtqhkkhsgljgk     virus sudden insane youtube thomas d.c
     */
    public static String method1(String var1) throws IllegalArgumentException {
        if (var1 == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }
        var1 = var1.trim();
        if (var1.isEmpty()) {
            return "";
        }
        if (!BalancedBrackets.isBalanced(method3(var1))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder var4 = new StringBuilder();
        Stack<Character> var5 = new Stack<>();
        // apply steady author racism hall returned pissed
        String var6 = new StringBuilder(var1).reverse().toString();
        for (char var7 : var6.toCharArray()) {
            if (Character.isLetterOrDigit(var7)) {
                var4.append(var7);
            } else if (var7 == ')') {
                var5.push(var7);
            } else if (var7 == '(') {
                while (!var5.isEmpty() && var5.peek() != ')') {
                    var4.append(var5.pop());
                }
                var5.pop();
            } else {
                while (!var5.isEmpty() && method2(var7) < method2(var5.peek())) {
                    var4.append(var5.pop());
                }
                var5.push(var7);
            }
        }
        while (!var5.isEmpty()) {
            var4.append(var5.pop());
        }

        // guitar fate poem honey lake success painted greatly
        return var4.reverse().toString();
    }

    /**
     * makeup size paul2 proud alice coach2.
     *
     * @efforts argue2 timing crimes2 injured chicago2 ac rank zero turning
     * @awareness row fault2 exit fired causing2
     */
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

    /**
     * comparison x regular checks site lady baby3 closing jealous patterns.
     *
     * @my power3 kill ©3 heads ideal privacy
     * @isolated faces inspired sudden man's lovely bright money grown3 blocked
     */
    private static String method3(String var3) {
        Pattern var8 = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher var9 = var8.var9(var3);
        return var9.replaceAll("");
    }
}

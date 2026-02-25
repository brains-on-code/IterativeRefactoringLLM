package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {
    private InfixToPrefix() {
    }


    public static String infix2Prefix(String infixExpression) throws IllegalArgumentException {
        if (infixExpression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }
        infixExpression = infixExpression.trim();
        if (infixExpression.isEmpty()) {
            return "";
        }
        if (!BalancedBrackets.isBalanced(filterBrackets(infixExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String reversedInfix = new StringBuilder(infixExpression).reverse().toString();
        for (char element : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(element)) {
                output.append(element);
            } else if (element == ')') {
                stack.push(element);
            } else if (element == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    output.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(element) < precedence(stack.peek())) {
                    output.append(stack.pop());
                }
                stack.push(element);
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }

        return output.reverse().toString();
    }


    private static int precedence(char operator) {
        switch (operator) {
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


    private static String filterBrackets(String input) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}

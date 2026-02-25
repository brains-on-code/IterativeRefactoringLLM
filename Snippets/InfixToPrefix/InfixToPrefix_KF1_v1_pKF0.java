package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an infix expression to postfix (Reverse Polish Notation).
     *
     * @param expression the infix expression
     * @return the postfix expression
     * @throws IllegalArgumentException if the expression has unbalanced brackets
     * @throws NullPointerException     if the input expression is null
     */
    public static String method1(String expression) throws IllegalArgumentException {
        if (expression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }

        expression = expression.trim();
        if (expression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        String reversedExpression = new StringBuilder(expression).reverse().toString();

        for (char ch : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            } else if (ch == ')') {
                operators.push(ch);
            } else if (ch == '(') {
                while (!operators.isEmpty() && operators.peek() != ')') {
                    output.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop();
                }
            } else {
                while (!operators.isEmpty() && getPrecedence(ch) < getPrecedence(operators.peek())) {
                    output.append(operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.reverse().toString();
    }

    private static int getPrecedence(char operator) {
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

    private static String extractBrackets(String expression) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(expression);
        return matcher.replaceAll("");
    }
}
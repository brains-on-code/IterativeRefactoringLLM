package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {
        // Prevent instantiation
    }

    public static String infix2PostFix(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                postfix.append(token);
                continue;
            }

            if (token == '(') {
                operators.push(token);
                continue;
            }

            if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop();
                }
                continue;
            }

            while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                postfix.append(operators.pop());
            }
            operators.push(token);
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString();
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

    private static String extractBrackets(String input) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
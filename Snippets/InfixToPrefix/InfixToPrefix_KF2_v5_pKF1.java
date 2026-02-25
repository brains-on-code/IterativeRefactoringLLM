package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private InfixToPrefix() {
    }

    public static String infixToPrefix(String infixExpression) throws IllegalArgumentException {
        if (infixExpression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }

        String trimmedExpression = infixExpression.trim();
        if (trimmedExpression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBracketsOnly(trimmedExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder prefixBuilder = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();
        for (char token : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                prefixBuilder.append(token);
            } else if (token == ')') {
                operators.push(token);
            } else if (token == '(') {
                while (!operators.isEmpty() && operators.peek() != ')') {
                    prefixBuilder.append(operators.pop());
                }
                operators.pop();
            } else {
                while (!operators.isEmpty()
                        && getOperatorPrecedence(token) < getOperatorPrecedence(operators.peek())) {
                    prefixBuilder.append(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            prefixBuilder.append(operators.pop());
        }

        return prefixBuilder.reverse().toString();
    }

    private static int getOperatorPrecedence(char operator) {
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

    private static String extractBracketsOnly(String expression) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher nonBracketMatcher = nonBracketPattern.matcher(expression);
        return nonBracketMatcher.replaceAll("");
    }
}
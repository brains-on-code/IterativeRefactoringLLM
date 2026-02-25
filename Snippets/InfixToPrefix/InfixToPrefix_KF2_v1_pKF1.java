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

        if (!BalancedBrackets.isBalanced(extractBrackets(trimmedExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder prefixBuilder = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();
        for (char currentChar : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(currentChar)) {
                prefixBuilder.append(currentChar);
            } else if (currentChar == ')') {
                operatorStack.push(currentChar);
            } else if (currentChar == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    prefixBuilder.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getPrecedence(currentChar) < getPrecedence(operatorStack.peek())) {
                    prefixBuilder.append(operatorStack.pop());
                }
                operatorStack.push(currentChar);
            }
        }

        while (!operatorStack.isEmpty()) {
            prefixBuilder.append(operatorStack.pop());
        }

        return prefixBuilder.reverse().toString();
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

    private static String extractBrackets(String input) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = nonBracketPattern.matcher(input);
        return matcher.replaceAll("");
    }
}
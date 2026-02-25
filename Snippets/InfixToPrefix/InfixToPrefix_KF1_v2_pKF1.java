package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefixConverter {

    private InfixToPrefixConverter() {
    }

    public static String convertInfixToPrefix(String infixExpression) throws IllegalArgumentException {
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
        String reversedExpression = new StringBuilder(trimmedExpression).reverse().toString();

        for (char currentChar : reversedExpression.toCharArray()) {
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
                        && getOperatorPrecedence(currentChar) < getOperatorPrecedence(operatorStack.peek())) {
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

    private static String extractBrackets(String expression) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher nonBracketMatcher = nonBracketPattern.matcher(expression);
        return nonBracketMatcher.replaceAll("");
    }
}
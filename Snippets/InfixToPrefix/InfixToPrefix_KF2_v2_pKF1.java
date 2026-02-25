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

        String normalizedExpression = infixExpression.trim();
        if (normalizedExpression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(normalizedExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder prefixExpressionBuilder = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedInfixExpression = new StringBuilder(normalizedExpression).reverse().toString();
        for (char currentToken : reversedInfixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(currentToken)) {
                prefixExpressionBuilder.append(currentToken);
            } else if (currentToken == ')') {
                operatorStack.push(currentToken);
            } else if (currentToken == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    prefixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getOperatorPrecedence(currentToken) < getOperatorPrecedence(operatorStack.peek())) {
                    prefixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.push(currentToken);
            }
        }

        while (!operatorStack.isEmpty()) {
            prefixExpressionBuilder.append(operatorStack.pop());
        }

        return prefixExpressionBuilder.reverse().toString();
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
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

        String normalizedExpression = infixExpression.trim();
        if (normalizedExpression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBracketsOnly(normalizedExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder prefixExpressionBuilder = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        String reversedExpression = new StringBuilder(normalizedExpression).reverse().toString();

        for (char currentChar : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(currentChar)) {
                prefixExpressionBuilder.append(currentChar);
            } else if (currentChar == ')') {
                operatorStack.push(currentChar);
            } else if (currentChar == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    prefixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getOperatorPrecedence(currentChar) < getOperatorPrecedence(operatorStack.peek())) {
                    prefixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.push(currentChar);
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

    private static String extractBracketsOnly(String expression) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher nonBracketMatcher = nonBracketPattern.matcher(expression);
        return nonBracketMatcher.replaceAll("");
    }
}
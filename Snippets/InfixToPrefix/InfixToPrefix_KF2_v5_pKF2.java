package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private static final Pattern NON_BRACKET_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPrefix() {
        // Prevent instantiation
    }

    public static String infix2Prefix(String infixExpression) {
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

        StringBuilder output = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();

        for (char ch : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            } else if (ch == ')') {
                operatorStack.push(ch);
            } else if (ch == '(') {
                popUntilClosingParenthesis(output, operatorStack);
            } else {
                popOperatorsWithHigherPrecedence(output, operatorStack, ch);
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            output.append(operatorStack.pop());
        }

        return output.reverse().toString();
    }

    private static void popUntilClosingParenthesis(StringBuilder output, Stack<Character> operatorStack) {
        while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
            output.append(operatorStack.pop());
        }
        if (!operatorStack.isEmpty()) {
            operatorStack.pop();
        }
    }

    private static void popOperatorsWithHigherPrecedence(
            StringBuilder output, Stack<Character> operatorStack, char currentOperator) {
        while (!operatorStack.isEmpty()
                && precedence(currentOperator) < precedence(operatorStack.peek())) {
            output.append(operatorStack.pop());
        }
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
        Matcher matcher = NON_BRACKET_PATTERN.matcher(input);
        return matcher.replaceAll("");
    }
}
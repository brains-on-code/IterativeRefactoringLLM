package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfixConverter {

    private static final Pattern NON_BRACKET_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPostfixConverter() {
        // Utility class; prevent instantiation
    }

    public static String convertToPostfix(String expression) throws Exception {
        if (expression == null) {
            throw new IllegalArgumentException("expression cannot be null");
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char currentChar : expression.toCharArray()) {
            if (Character.isLetterOrDigit(currentChar)) {
                postfix.append(currentChar);
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
            } else if (currentChar == ')') {
                popUntilOpeningParenthesis(operatorStack, postfix);
            } else if (!Character.isWhitespace(currentChar)) {
                processOperator(currentChar, operatorStack, postfix);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    private static void popUntilOpeningParenthesis(Stack<Character> operatorStack, StringBuilder postfix) {
        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
            postfix.append(operatorStack.pop());
        }
        if (!operatorStack.isEmpty()) {
            operatorStack.pop(); // remove '('
        }
    }

    private static void processOperator(char operator, Stack<Character> operatorStack, StringBuilder postfix) {
        while (!operatorStack.isEmpty()
                && precedence(operator) <= precedence(operatorStack.peek())) {
            postfix.append(operatorStack.pop());
        }
        operatorStack.push(operator);
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
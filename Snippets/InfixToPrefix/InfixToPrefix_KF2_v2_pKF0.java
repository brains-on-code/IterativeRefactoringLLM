package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private static final Pattern BRACKETS_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPrefix() {
        // Utility class; prevent instantiation
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
        Stack<Character> operators = new Stack<>();
        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();

        for (char ch : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
                continue;
            }

            if (ch == ')') {
                operators.push(ch);
                continue;
            }

            if (ch == '(') {
                popUntilClosingParenthesis(operators, output);
                continue;
            }

            popOperatorsWithHigherPrecedence(ch, operators, output);
            operators.push(ch);
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.reverse().toString();
    }

    private static void popUntilClosingParenthesis(Stack<Character> operators, StringBuilder output) {
        while (!operators.isEmpty() && operators.peek() != ')') {
            output.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop();
        }
    }

    private static void popOperatorsWithHigherPrecedence(
            char currentOperator, Stack<Character> operators, StringBuilder output) {
        while (!operators.isEmpty()
                && precedence(currentOperator) < precedence(operators.peek())) {
            output.append(operators.pop());
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
        Matcher matcher = BRACKETS_PATTERN.matcher(input);
        return matcher.replaceAll("");
    }
}
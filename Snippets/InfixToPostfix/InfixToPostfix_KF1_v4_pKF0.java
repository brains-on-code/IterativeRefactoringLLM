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
        validateExpression(expression);

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                popUntilOpeningParenthesis(operatorStack, postfix);
            } else if (!Character.isWhitespace(ch)) {
                processOperator(ch, operatorStack, postfix);
            }
        }

        appendRemainingOperators(operatorStack, postfix);
        return postfix.toString();
    }

    private static void validateExpression(String expression) throws Exception {
        if (expression == null) {
            throw new IllegalArgumentException("expression cannot be null");
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new Exception("invalid expression");
        }
    }

    private static void popUntilOpeningParenthesis(Stack<Character> operatorStack, StringBuilder postfix) {
        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
            postfix.append(operatorStack.pop());
        }
        if (!operatorStack.isEmpty()) {
            operatorStack.pop();
        }
    }

    private static void processOperator(char operator, Stack<Character> operatorStack, StringBuilder postfix) {
        while (!operatorStack.isEmpty() && hasLowerOrEqualPrecedence(operator, operatorStack.peek())) {
            postfix.append(operatorStack.pop());
        }
        operatorStack.push(operator);
    }

    private static boolean hasLowerOrEqualPrecedence(char currentOperator, char stackOperator) {
        return precedence(currentOperator) <= precedence(stackOperator);
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

    private static void appendRemainingOperators(Stack<Character> operatorStack, StringBuilder postfix) {
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }
    }

    private static String extractBrackets(String input) {
        Matcher matcher = NON_BRACKET_PATTERN.matcher(input);
        return matcher.replaceAll("");
    }
}
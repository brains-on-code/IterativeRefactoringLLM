package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private static final Pattern BRACKETS_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPostfix() {
        // Utility class; prevent instantiation
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
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                popUntilOpeningParenthesis(postfix, operators);
            } else {
                popOperatorsWithHigherOrEqualPrecedence(postfix, operators, token);
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString();
    }

    private static void popUntilOpeningParenthesis(StringBuilder output, Stack<Character> operators) {
        while (!operators.isEmpty() && operators.peek() != '(') {
            output.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop(); // Remove '('
        }
    }

    private static void popOperatorsWithHigherOrEqualPrecedence(
            StringBuilder output, Stack<Character> operators, char currentOperator) {
        while (!operators.isEmpty()
                && precedence(currentOperator) <= precedence(operators.peek())) {
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
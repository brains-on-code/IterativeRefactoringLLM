package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private InfixToPrefix() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an infix expression to a prefix expression using a stack-based algorithm.
     *
     * @param infixExpression the infix expression to convert
     * @return the corresponding prefix expression
     * @throws IllegalArgumentException if the infix expression has unbalanced brackets
     * @throws NullPointerException     if the infix expression is null
     */
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
            } else if (ch == ')') {
                operators.push(ch);
            } else if (ch == '(') {
                while (!operators.isEmpty() && operators.peek() != ')') {
                    output.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop();
                }
            } else {
                while (!operators.isEmpty() && precedence(ch) < precedence(operators.peek())) {
                    output.append(operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.reverse().toString();
    }

    /**
     * Returns the precedence of the given operator.
     *
     * @param operator the operator character
     * @return an integer representing the precedence; higher value means higher precedence
     */
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

    /**
     * Returns a string containing only the bracket characters from the input.
     *
     * @param input the original expression
     * @return a string with all non-bracket characters removed
     */
    private static String extractBrackets(String input) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
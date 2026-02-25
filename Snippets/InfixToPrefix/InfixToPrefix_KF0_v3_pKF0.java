package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private static final Pattern BRACKETS_FILTER_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPrefix() {
        // Utility class; prevent instantiation
    }

    /**
     * Convert an infix expression to a prefix expression using a stack.
     *
     * @param infixExpression the infix expression to convert
     * @return the prefix expression
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

        validateBalancedBrackets(trimmedExpression);

        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();
        for (char ch : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
                continue;
            }

            switch (ch) {
                case ')':
                    operators.push(ch);
                    break;
                case '(':
                    popUntilClosingParenthesis(operators, output);
                    break;
                default:
                    popOperatorsWithHigherOrEqualPrecedence(ch, operators, output);
                    operators.push(ch);
                    break;
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.reverse().toString();
    }

    private static void validateBalancedBrackets(String expression) {
        String bracketsOnly = filterBrackets(expression);
        if (!BalancedBrackets.isBalanced(bracketsOnly)) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }
    }

    private static void popUntilClosingParenthesis(Stack<Character> operators, StringBuilder output) {
        while (!operators.isEmpty() && operators.peek() != ')') {
            output.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop();
        }
    }

    private static void popOperatorsWithHigherOrEqualPrecedence(
        char currentOperator,
        Stack<Character> operators,
        StringBuilder output
    ) {
        while (!operators.isEmpty()
            && precedence(currentOperator) <= precedence(operators.peek())) {
            output.append(operators.pop());
        }
    }

    /**
     * Determines the precedence of an operator.
     *
     * @param operator the operator whose precedence is to be determined
     * @return the precedence of the operator
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
     * Filters out all characters from the input string except brackets.
     *
     * @param input the input string to filter
     * @return a string containing only brackets from the input string
     */
    private static String filterBrackets(String input) {
        Matcher matcher = BRACKETS_FILTER_PATTERN.matcher(input);
        return matcher.replaceAll("");
    }
}
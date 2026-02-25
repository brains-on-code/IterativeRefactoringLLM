package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {

    private static final Pattern NON_BRACKET_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an infix expression to postfix (Reverse Polish Notation).
     *
     * @param expression the infix expression
     * @return the postfix expression
     * @throws IllegalArgumentException if the expression has unbalanced brackets
     * @throws NullPointerException     if the input expression is null
     */
    public static String method1(String expression) {
        validateExpressionNotNull(expression);

        String trimmedExpression = expression.trim();
        if (trimmedExpression.isEmpty()) {
            return "";
        }

        if (!BalancedBrackets.isBalanced(extractBrackets(trimmedExpression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        return convertInfixToPostfix(trimmedExpression);
    }

    private static void validateExpressionNotNull(String expression) {
        if (expression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }
    }

    private static String convertInfixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedExpression = new StringBuilder(expression).reverse().toString();

        for (char ch : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
                continue;
            }

            if (ch == ')') {
                operatorStack.push(ch);
            } else if (ch == '(') {
                popUntilClosingParenthesis(postfix, operatorStack);
            } else {
                popHigherPrecedenceOperators(postfix, operatorStack, ch);
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.reverse().toString();
    }

    private static void popUntilClosingParenthesis(StringBuilder output, Stack<Character> operators) {
        while (!operators.isEmpty() && operators.peek() != ')') {
            output.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop();
        }
    }

    private static void popHigherPrecedenceOperators(
        StringBuilder output,
        Stack<Character> operators,
        char currentOperator
    ) {
        while (!operators.isEmpty()
            && getPrecedence(currentOperator) < getPrecedence(operators.peek())) {
            output.append(operators.pop());
        }
    }

    private static int getPrecedence(char operator) {
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
        Matcher matcher = NON_BRACKET_PATTERN.matcher(expression);
        return matcher.replaceAll("");
    }
}
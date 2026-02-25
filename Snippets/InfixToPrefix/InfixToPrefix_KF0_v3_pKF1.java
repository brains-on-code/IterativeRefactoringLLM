package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefix {

    private InfixToPrefix() {
    }

    /**
     * Convert an infix expression to a prefix expression using a stack.
     *
     * @param infixExpression the infix expression to convert
     * @return the prefix expression
     * @throws IllegalArgumentException if the infix expression has unbalanced brackets
     * @throws NullPointerException     if the infix expression is null
     */
    public static String convertInfixToPrefix(String infixExpression) throws IllegalArgumentException {
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

        StringBuilder prefixBuilder = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        String reversedInfix = new StringBuilder(trimmedExpression).reverse().toString();
        for (char currentChar : reversedInfix.toCharArray()) {
            if (Character.isLetterOrDigit(currentChar)) {
                prefixBuilder.append(currentChar);
            } else if (currentChar == ')') {
                operatorStack.push(currentChar);
            } else if (currentChar == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    prefixBuilder.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getOperatorPrecedence(currentChar) < getOperatorPrecedence(operatorStack.peek())) {
                    prefixBuilder.append(operatorStack.pop());
                }
                operatorStack.push(currentChar);
            }
        }

        while (!operatorStack.isEmpty()) {
            prefixBuilder.append(operatorStack.pop());
        }

        return prefixBuilder.reverse().toString();
    }

    /**
     * Determines the precedence of an operator.
     *
     * @param operator the operator whose precedence is to be determined
     * @return the precedence of the operator
     */
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

    /**
     * Extracts only bracket characters from the input string.
     *
     * @param input the input string to filter
     * @return a string containing only brackets from the input string
     */
    private static String extractBrackets(String input) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = nonBracketPattern.matcher(input);
        return matcher.replaceAll("");
    }
}
package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Class1 {

    private static final Pattern NON_BRACKET_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts an infix expression to postfix (Reverse Polish Notation).
     *
     * @param expression the infix expression
     * @return the postfix expression
     * @throws Exception if the expression has unbalanced brackets
     */
    public static String infixToPostfix(String expression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                operatorStack.push(ch);
            } else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop();
                }
            } else {
                while (!operatorStack.isEmpty()
                        && precedence(ch) <= precedence(operatorStack.peek())) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    /**
     * Returns the precedence of an operator.
     *
     * @param operator the operator character
     * @return precedence value (higher means higher precedence), or -1 if not an operator
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
     * Extracts only bracket characters from the given expression.
     *
     * @param expression the input expression
     * @return a string containing only bracket characters
     */
    private static String extractBrackets(String expression) {
        Matcher matcher = NON_BRACKET_PATTERN.matcher(expression);
        return matcher.replaceAll("");
    }
}
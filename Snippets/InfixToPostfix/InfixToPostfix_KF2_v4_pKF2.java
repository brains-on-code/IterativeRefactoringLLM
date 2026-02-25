package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {
        // Prevent instantiation
    }

    /**
     * Converts an infix expression to postfix (Reverse Polish) notation.
     *
     * @param infixExpression the infix expression to convert
     * @return the postfix representation of the given infix expression
     * @throws Exception if the expression has unbalanced brackets
     */
    public static String infix2PostFix(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                output.append(token);
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop();
                }
            } else {
                while (!operators.isEmpty()
                        && precedence(token) <= precedence(operators.peek())) {
                    output.append(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.toString();
    }

    /**
     * Returns the precedence of an operator.
     *
     * Higher value means higher precedence.
     *
     * @param operator the operator character
     * @return the precedence level, or -1 if not an operator
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
     * Extracts only bracket characters from the input string.
     *
     * @param input the original expression
     * @return a string containing only bracket characters
     */
    private static String extractBrackets(String input) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
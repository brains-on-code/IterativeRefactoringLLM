package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an infix expression to its postfix (Reverse Polish) notation.
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
                // Operand: append directly to output
                output.append(token);
            } else if (token == '(') {
                // Opening parenthesis: push to stack
                operators.push(token);
            } else if (token == ')') {
                // Closing parenthesis: pop until matching '('
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.append(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop(); // Discard '('
                }
            } else {
                // Operator: pop operators with higher or equal precedence
                while (!operators.isEmpty()
                        && precedence(token) <= precedence(operators.peek())) {
                    output.append(operators.pop());
                }
                operators.push(token);
            }
        }

        // Pop any remaining operators
        while (!operators.isEmpty()) {
            output.append(operators.pop());
        }

        return output.toString();
    }

    /**
     * Returns the precedence of the given operator.
     *
     * Higher value means higher precedence.
     *
     * @param operator the operator character
     * @return precedence level, or -1 if not an operator
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
     * This is used to validate bracket balance independently of other characters.
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
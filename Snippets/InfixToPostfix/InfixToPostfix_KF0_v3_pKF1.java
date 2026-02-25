package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {}

    public static String convertInfixToPostfix(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder postfixExpression = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                postfixExpression.append(token);
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfixExpression.append(operators.pop());
                }
                operators.pop();
            } else {
                while (!operators.isEmpty()
                        && getPrecedence(token) <= getPrecedence(operators.peek())) {
                    postfixExpression.append(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfixExpression.append(operators.pop());
        }

        return postfixExpression.toString();
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
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = nonBracketPattern.matcher(expression);
        return matcher.replaceAll("");
    }
}
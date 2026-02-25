package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPrefixConverter {
    private InfixToPrefixConverter() {
    }

    public static String convertInfixToPrefix(String expression) throws IllegalArgumentException {
        if (expression == null) {
            throw new NullPointerException("Input expression cannot be null.");
        }
        expression = expression.trim();
        if (expression.isEmpty()) {
            return "";
        }
        if (!BalancedBrackets.isBalanced(extractBrackets(expression))) {
            throw new IllegalArgumentException("Invalid expression: unbalanced brackets.");
        }

        StringBuilder result = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();
        String reversedExpression = new StringBuilder(expression).reverse().toString();

        for (char ch : reversedExpression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == ')') {
                operatorStack.push(ch);
            } else if (ch == '(') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != ')') {
                    result.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getOperatorPrecedence(ch) < getOperatorPrecedence(operatorStack.peek())) {
                    result.append(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
        }

        while (!operatorStack.isEmpty()) {
            result.append(operatorStack.pop());
        }

        return result.reverse().toString();
    }

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

    private static String extractBrackets(String expression) {
        Pattern nonBracketPattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = nonBracketPattern.matcher(expression);
        return matcher.replaceAll("");
    }
}
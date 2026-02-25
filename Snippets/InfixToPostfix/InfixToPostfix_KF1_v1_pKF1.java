package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfixConverter {

    private InfixToPostfixConverter() {
    }

    public static String convertInfixToPostfix(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }

        StringBuilder postfixExpression = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char currentChar : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(currentChar)) {
                postfixExpression.append(currentChar);
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfixExpression.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty()
                        && getOperatorPrecedence(currentChar) <= getOperatorPrecedence(operatorStack.peek())) {
                    postfixExpression.append(operatorStack.pop());
                }
                operatorStack.push(currentChar);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfixExpression.append(operatorStack.pop());
        }

        return postfixExpression.toString();
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
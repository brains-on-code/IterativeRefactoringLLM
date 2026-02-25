package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {
        // Prevent instantiation of utility class
    }

    public static String infix2PostFix(String infixExpression) throws Exception {
        validateBrackets(infixExpression);

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                postfix.append(token);
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                popUntilOpeningParenthesis(operators, postfix);
            } else {
                popOperatorsWithHigherOrEqualPrecedence(token, operators, postfix);
                operators.push(token);
            }
        }

        popRemainingOperators(operators, postfix);
        return postfix.toString();
    }

    private static void validateBrackets(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }
    }

    private static void popUntilOpeningParenthesis(Stack<Character> operators, StringBuilder postfix) {
        while (!operators.isEmpty() && operators.peek() != '(') {
            postfix.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop(); // Discard the '('
        }
    }

    private static void popOperatorsWithHigherOrEqualPrecedence(
        char currentOperator,
        Stack<Character> operators,
        StringBuilder postfix
    ) {
        while (!operators.isEmpty() && precedence(currentOperator) <= precedence(operators.peek())) {
            postfix.append(operators.pop());
        }
    }

    private static void popRemainingOperators(Stack<Character> operators, StringBuilder postfix) {
        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }
    }

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

    private static String extractBrackets(String input) {
        Pattern pattern = Pattern.compile("[^(){}\\[\\]<>]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
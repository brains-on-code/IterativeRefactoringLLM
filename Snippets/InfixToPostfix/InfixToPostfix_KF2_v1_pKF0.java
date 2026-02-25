package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private InfixToPostfix() {
        // Utility class; prevent instantiation
    }

    public static String infix2PostFix(String infixExpression) throws Exception {
        validateBrackets(infixExpression);

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                postfix.append(token);
            } else if (isOpeningBracket(token)) {
                operators.push(token);
            } else if (isClosingBracket(token)) {
                popUntilOpeningBracket(operators, postfix);
            } else {
                popOperatorsWithHigherOrEqualPrecedence(token, operators, postfix);
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString();
    }

    private static void validateBrackets(String infixExpression) throws Exception {
        if (!BalancedBrackets.isBalanced(extractBrackets(infixExpression))) {
            throw new Exception("invalid expression");
        }
    }

    private static boolean isOpeningBracket(char ch) {
        return ch == '(';
    }

    private static boolean isClosingBracket(char ch) {
        return ch == ')';
    }

    private static void popUntilOpeningBracket(Stack<Character> operators, StringBuilder output) {
        while (!operators.isEmpty() && !isOpeningBracket(operators.peek())) {
            output.append(operators.pop());
        }
        if (!operators.isEmpty()) {
            operators.pop(); // Remove the opening bracket
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
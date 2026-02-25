package com.thealgorithms.stacks;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InfixToPostfix {

    private static final Pattern NON_BRACKET_PATTERN = Pattern.compile("[^(){}\\[\\]<>]");

    private InfixToPostfix() {
        // Utility class; prevent instantiation
    }

    public static String infix2PostFix(String infixExpression) throws Exception {
        validateBrackets(infixExpression);

        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (char token : infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(token)) {
                postfix.append(token);
            } else if (isOpeningBracket(token)) {
                operatorStack.push(token);
            } else if (isClosingBracket(token)) {
                popUntilOpeningBracket(operatorStack, postfix);
            } else {
                popOperatorsWithHigherOrEqualPrecedence(token, operatorStack, postfix);
                operatorStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    private static void validateBrackets(String infixExpression) throws Exception {
        String bracketsOnly = extractBrackets(infixExpression);
        if (!BalancedBrackets.isBalanced(bracketsOnly)) {
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
            operators.pop();
        }
    }

    private static void popOperatorsWithHigherOrEqualPrecedence(
        char currentOperator,
        Stack<Character> operators,
        StringBuilder output
    ) {
        while (!operators.isEmpty()
            && hasHigherOrEqualPrecedence(operators.peek(), currentOperator)) {
            output.append(operators.pop());
        }
    }

    private static boolean hasHigherOrEqualPrecedence(char stackOperator, char currentOperator) {
        return precedence(stackOperator) >= precedence(currentOperator);
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
        Matcher matcher = NON_BRACKET_PATTERN.matcher(input);
        return matcher.replaceAll("");
    }
}
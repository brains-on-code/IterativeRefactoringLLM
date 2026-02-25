package com.thealgorithms.strings;

public final class Class1 {

    private Class1() {
        // Prevent instantiation of utility class
    }

    /**
     * Determines whether a string of brackets is balanced and correctly ordered.
     * Supported brackets: (), {}, [].
     *
     * @param input a string consisting only of bracket characters
     * @return {@code true} if all brackets are balanced and properly nested;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if a non-bracket character is encountered
     */
    public static boolean method1(String input) {
        char[] stack = new char[input.length()];
        int top = 0;

        for (char ch : input.toCharArray()) {
            switch (ch) {
                case '{':
                case '[':
                case '(':
                    stack[top++] = ch;
                    break;
                case '}':
                    if (top == 0 || stack[--top] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (top == 0 || stack[--top] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (top == 0 || stack[--top] != '[') {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }

        return top == 0;
    }

    /**
     * Determines whether a string of brackets is balanced and correctly ordered.
     * Supported brackets: (), {}, [].
     *
     * @param input a string consisting only of bracket characters
     * @return {@code true} if all brackets are balanced and properly nested;
     *         {@code false} otherwise
     */
    public static boolean method2(String input) {
        char[] stack = new char[input.length()];
        int top = -1;

        final String openingBrackets = "({[";
        final String closingBrackets = ")}]";

        for (char ch : input.toCharArray()) {
            int openingIndex = openingBrackets.indexOf(ch);

            if (openingIndex != -1) {
                stack[++top] = ch;
            } else {
                boolean stackNotEmpty = top >= 0;
                boolean bracketsMatch =
                        stackNotEmpty
                                && openingBrackets.indexOf(stack[top])
                                        == closingBrackets.indexOf(ch);

                if (bracketsMatch) {
                    top--;
                } else {
                    return false;
                }
            }
        }

        return top == -1;
    }
}
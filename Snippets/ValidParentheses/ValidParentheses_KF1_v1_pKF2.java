package com.thealgorithms.strings;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the input string has balanced and correctly ordered brackets.
     * Supported brackets: (), {}, [].
     *
     * @param input the string containing only bracket characters
     * @return true if the brackets are balanced; false otherwise
     * @throws IllegalArgumentException if an unexpected character is encountered
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
     * Checks if the input string has balanced and correctly ordered brackets.
     * Supported brackets: (), {}, [].
     *
     * @param input the string containing only bracket characters
     * @return true if the brackets are balanced; false otherwise
     */
    public static boolean method2(String input) {
        int top = -1;
        char[] stack = new char[input.length()];
        String openingBrackets = "({[";
        String closingBrackets = ")}]";

        for (char ch : input.toCharArray()) {
            int openingIndex = openingBrackets.indexOf(ch);

            if (openingIndex != -1) {
                stack[++top] = ch;
            } else {
                if (top >= 0 && openingBrackets.indexOf(stack[top]) == closingBrackets.indexOf(ch)) {
                    top--;
                } else {
                    return false;
                }
            }
        }

        return top == -1;
    }
}
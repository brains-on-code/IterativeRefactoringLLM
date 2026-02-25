package com.thealgorithms.strings;

public final class ValidParentheses {
    private ValidParentheses() {
    }
    public static boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int head = 0;
        for (char c : s.toCharArray()) {
            switch (c) {
            case '{':
            case '[':
            case '(':
                stack[head++] = c;
                break;
            case '}':
                if (head == 0 || stack[--head] != '{') {
                    return false;
                }
                break;
            case ')':
                if (head == 0 || stack[--head] != '(') {
                    return false;
                }
                break;
            case ']':
                if (head == 0 || stack[--head] != '[') {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected character: " + c);
            }
        }
        return head == 0;
    }
    public static boolean isValidParentheses(String s) {
        int i = -1;
        char[] stack = new char[s.length()];
        String openBrackets = "({[";
        String closeBrackets = ")}]";
        for (char ch : s.toCharArray()) {
            if (openBrackets.indexOf(ch) != -1) {
                stack[++i] = ch;
            } else {
                if (i >= 0 && openBrackets.indexOf(stack[i]) == closeBrackets.indexOf(ch)) {
                    i--;
                } else {
                    return false;
                }
            }
        }
        return i == -1;
    }
}

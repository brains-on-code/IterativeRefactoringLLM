package com.thealgorithms.stacks;

import java.util.Stack;


final class BalancedBrackets {
    private BalancedBrackets() {
    }


    public static boolean isPaired(char leftBracket, char rightBracket) {
        char[][] pairedBrackets = {
            {'(', ')'},
            {'[', ']'},
            {'{', '}'},
            {'<', '>'},
        };
        for (char[] pairedBracket : pairedBrackets) {
            if (pairedBracket[0] == leftBracket && pairedBracket[1] == rightBracket) {
                return true;
            }
        }
        return false;
    }


    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }
        Stack<Character> bracketsStack = new Stack<>();
        for (char bracket : brackets.toCharArray()) {
            switch (bracket) {
            case '(':
            case '[':
            case '<':
            case '{':
                bracketsStack.push(bracket);
                break;
            case ')':
            case ']':
            case '>':
            case '}':
                if (bracketsStack.isEmpty() || !isPaired(bracketsStack.pop(), bracket)) {
                    return false;
                }
                break;
            default:
                return false;
            }
        }
        return bracketsStack.isEmpty();
    }
}

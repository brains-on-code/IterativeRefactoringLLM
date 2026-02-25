package com.thealgorithms.strings;

public final class StringCompression {

    private StringCompression() {
        // Utility class; prevent instantiation
    }

    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return input;
        }

        StringBuilder compressed = new StringBuilder();
        char currentChar = input.charAt(0);
        int currentCharCount = 1;

        for (int index = 1; index < input.length(); index++) {
            char nextChar = input.charAt(index);

            if (nextChar == currentChar) {
                currentCharCount++;
            } else {
                appendRun(compressed, currentChar, currentCharCount);
                currentChar = nextChar;
                currentCharCount = 1;
            }
        }

        appendRun(compressed, currentChar, currentCharCount);
        return compressed.toString();
    }

    private static void appendRun(StringBuilder builder, char character, int count) {
        builder.append(character);
        if (count > 1) {
            builder.append(count);
        }
    }
}
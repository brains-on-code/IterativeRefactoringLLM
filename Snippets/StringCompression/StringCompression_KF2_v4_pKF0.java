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
        char previousChar = input.charAt(0);
        int count = 1;

        for (int index = 1; index < input.length(); index++) {
            char currentChar = input.charAt(index);

            if (currentChar == previousChar) {
                count++;
            } else {
                appendRun(compressed, previousChar, count);
                previousChar = currentChar;
                count = 1;
            }
        }

        appendRun(compressed, previousChar, count);
        return compressed.toString();
    }

    private static void appendRun(StringBuilder builder, char character, int count) {
        builder.append(character);
        if (count > 1) {
            builder.append(count);
        }
    }
}
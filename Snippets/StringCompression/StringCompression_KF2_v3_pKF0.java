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
        int runLength = 1;

        for (int i = 1; i < input.length(); i++) {
            char nextChar = input.charAt(i);

            if (nextChar == currentChar) {
                runLength++;
            } else {
                appendRun(compressed, currentChar, runLength);
                currentChar = nextChar;
                runLength = 1;
            }
        }

        appendRun(compressed, currentChar, runLength);
        return compressed.toString();
    }

    private static void appendRun(StringBuilder builder, char character, int runLength) {
        builder.append(character);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
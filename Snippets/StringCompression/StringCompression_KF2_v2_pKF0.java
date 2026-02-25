package com.thealgorithms.strings;

public final class StringCompression {

    private StringCompression() {
        // Utility class; prevent instantiation
    }

    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        int length = input.length();
        if (length == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder compressed = new StringBuilder();
        int runLength = 1;
        char previousChar = input.charAt(0);

        for (int i = 1; i < length; i++) {
            char currentChar = input.charAt(i);

            if (currentChar == previousChar) {
                runLength++;
            } else {
                appendRun(compressed, previousChar, runLength);
                previousChar = currentChar;
                runLength = 1;
            }
        }

        appendRun(compressed, previousChar, runLength);

        return compressed.toString();
    }

    private static void appendRun(StringBuilder builder, char character, int runLength) {
        builder.append(character);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
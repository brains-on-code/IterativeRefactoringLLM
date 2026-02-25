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
        int count = 1;

        for (int i = 1; i < length; i++) {
            char current = input.charAt(i);
            char previous = input.charAt(i - 1);

            if (current == previous) {
                count++;
            } else {
                appendCount(compressed, previous, count);
                count = 1;
            }
        }

        // Append the last character group
        appendCount(compressed, input.charAt(length - 1), count);

        return compressed.toString();
    }

    private static void appendCount(StringBuilder builder, char ch, int count) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
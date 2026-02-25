package com.thealgorithms.strings;

public final class StringCompression {

    private StringCompression() {
        // Utility class; prevent instantiation
    }

    /**
     * Compresses a string by replacing consecutive repeated characters
     * with the character followed by the count of repetitions.
     * For example, "aaabbc" becomes "a3b2c".
     *
     * @param input the string to compress
     * @return the compressed string
     */
    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 0; i < input.length() - 1; i++) {
            char current = input.charAt(i);
            char next = input.charAt(i + 1);

            if (current == next) {
                count++;
            } else {
                appendCount(compressed, count, current);
                count = 1;
            }

            // Handle the last character
            if (i + 1 == input.length() - 1) {
                if (current == next) {
                    appendCount(compressed, count, current);
                } else {
                    appendCount(compressed, count, current);
                    appendCount(compressed, 1, next);
                }
            }
        }

        return compressed.toString();
    }

    /**
     * Appends a character and its count to the given StringBuilder.
     * If count is 1, only the character is appended.
     *
     * @param builder the StringBuilder to append to
     * @param count   the number of repetitions
     * @param ch      the character to append
     */
    private static void appendCount(StringBuilder builder, int count, char ch) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
package com.thealgorithms.strings;

/**
 * References : https://en.wikipedia.org/wiki/Run-length_encoding
 * String compression algorithm deals with encoding the string, that is, shortening the size of the string
 * @author Swarga-codes (https://github.com/Swarga-codes)
 */
public final class StringCompression {

    private StringCompression() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the compressed or encoded string using run-length encoding.
     *
     * @param input the string to be encoded
     * @return the compressed string
     */
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
                appendCount(compressed, count, previous);
                count = 1;
            }
        }

        // Append the last run
        appendCount(compressed, count, input.charAt(length - 1));

        return compressed.toString();
    }

    /**
     * Appends the character and its count (if greater than 1) to the result.
     *
     * @param builder the StringBuilder to append to
     * @param count   current count
     * @param ch      the character at a particular index
     */
    private static void appendCount(StringBuilder builder, int count, char ch) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
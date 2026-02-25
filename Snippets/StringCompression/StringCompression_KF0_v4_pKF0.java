package com.thealgorithms.strings;

/**
 * References : https://en.wikipedia.org/wiki/Run-length_encoding
 * String compression algorithm deals with encoding the string, that is, shortening the size of the string
 * author Swarga-codes (https://github.com/Swarga-codes)
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
            return input;
        }

        StringBuilder compressed = new StringBuilder(length);
        char currentChar = input.charAt(0);
        int currentCount = 1;

        for (int i = 1; i < length; i++) {
            char nextChar = input.charAt(i);

            if (nextChar == currentChar) {
                currentCount++;
            } else {
                appendRun(compressed, currentChar, currentCount);
                currentChar = nextChar;
                currentCount = 1;
            }
        }

        appendRun(compressed, currentChar, currentCount);

        return compressed.toString();
    }

    /**
     * Appends the character and its count (if greater than 1) to the result.
     *
     * @param builder the StringBuilder to append to
     * @param ch      the character in the current run
     * @param count   length of the current run
     */
    private static void appendRun(StringBuilder builder, char ch, int count) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
package com.thealgorithms.strings;

/**
 * String compression using run-length encoding.
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Run-length_encoding
 */
public final class StringCompression {

    private StringCompression() {
        // Prevent instantiation
    }

    /**
     * Compresses the input string using run-length encoding.
     *
     * <p>Examples:
     * <ul>
     *   <li>"aaabb" -> "a3b2"</li>
     *   <li>"abc"   -> "abc"</li>
     * </ul>
     *
     * @param input the string to compress
     * @return the compressed string, or an empty string if input is null or empty
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

        for (int i = 1; i < input.length(); i++) {
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
        appendCount(compressed, count, input.charAt(input.length() - 1));

        return compressed.toString();
    }

    /**
     * Appends the character and its count (if greater than 1) to the result.
     *
     * @param builder the StringBuilder to append to
     * @param count   the number of occurrences of the character
     * @param ch      the character to append
     */
    private static void appendCount(StringBuilder builder, int count, char ch) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
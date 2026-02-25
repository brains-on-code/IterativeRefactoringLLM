package com.thealgorithms.strings;

/**
 * Utility class for string compression using run-length encoding (RLE).
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Run-length_encoding
 */
public final class StringCompression {

    private StringCompression() {
        // Prevent instantiation
    }

    /**
     * Compresses the given string using run-length encoding.
     *
     * <p>Consecutive repeated characters are replaced by a single instance of the
     * character followed by the number of repetitions. Single characters are left
     * as-is (i.e., without a trailing "1").
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code "aaabb"} → {@code "a3b2"}</li>
     *   <li>{@code "abc"}   → {@code "abc"}</li>
     * </ul>
     *
     * @param input the string to compress
     * @return the compressed string, or an empty string if {@code input} is {@code null} or empty
     */
    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return input;
        }

        StringBuilder compressed = new StringBuilder();
        int runLength = 1;

        for (int i = 1; i < input.length(); i++) {
            char current = input.charAt(i);
            char previous = input.charAt(i - 1);

            if (current == previous) {
                runLength++;
            } else {
                appendRun(compressed, previous, runLength);
                runLength = 1;
            }
        }

        appendRun(compressed, input.charAt(input.length() - 1), runLength);

        return compressed.toString();
    }

    /**
     * Appends a run of a character to the given {@link StringBuilder}.
     *
     * <p>If {@code count} is 1, only the character is appended. If {@code count} is
     * greater than 1, the character is followed by its count.
     *
     * @param builder the builder to append to
     * @param ch      the character in the run
     * @param count   the number of times {@code ch} is repeated
     */
    private static void appendRun(StringBuilder builder, char ch, int count) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
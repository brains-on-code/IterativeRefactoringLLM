package com.thealgorithms.strings;

/**
 * Utility class for simple run-length encoding of strings.
 */
public final class RunLengthEncoder {

    private RunLengthEncoder() {
        // Prevent instantiation
    }

    /**
     * Compresses the input string using a basic run-length encoding scheme.
     * Consecutive repeated characters are replaced by the character followed by
     * the number of repetitions. Single characters are left as-is.
     *
     * Example: "aaabbc" -> "a3b2c"
     *
     * @param input the string to compress
     * @return the run-length encoded string
     */
    public static String encode(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder encoded = new StringBuilder();
        int runLength = 1;
        char currentChar = input.charAt(0);

        for (int index = 1; index < input.length(); index++) {
            char nextChar = input.charAt(index);

            if (nextChar == currentChar) {
                runLength++;
            } else {
                appendRun(encoded, runLength, currentChar);
                currentChar = nextChar;
                runLength = 1;
            }
        }

        appendRun(encoded, runLength, currentChar);
        return encoded.toString();
    }

    /**
     * Appends a character and its run length to the given StringBuilder.
     * If the count is 1, only the character is appended.
     *
     * @param builder the StringBuilder to append to
     * @param count   the number of repetitions
     * @param ch      the character being repeated
     */
    private static void appendRun(StringBuilder builder, int count, char ch) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
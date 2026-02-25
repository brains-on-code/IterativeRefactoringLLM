package com.thealgorithms.strings;

public final class RunLengthEncoder {

    private RunLengthEncoder() {
        // Prevent instantiation of utility class
    }

    /**
     * Encodes the given string using run-length encoding.
     *
     * Consecutive identical characters are replaced by a single instance of the
     * character followed by the number of repetitions (only if greater than 1).
     *
     * Example:
     * <pre>
     *   "aaabbc" -> "a3b2c"
     * </pre>
     *
     * @param input the string to encode
     * @return the run-length encoded string, or an empty string if input is null or empty
     */
    public static String encode(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder encoded = new StringBuilder();
        char currentChar = input.charAt(0);
        int runLength = 1;

        for (int i = 1; i < input.length(); i++) {
            char nextChar = input.charAt(i);

            if (nextChar == currentChar) {
                runLength++;
            } else {
                appendRun(encoded, currentChar, runLength);
                currentChar = nextChar;
                runLength = 1;
            }
        }

        appendRun(encoded, currentChar, runLength);
        return encoded.toString();
    }

    /**
     * Appends a character and its run length to the given {@link StringBuilder}.
     * If the run length is 1, only the character is appended.
     *
     * @param builder   the destination for the encoded output
     * @param ch        the character being encoded
     * @param runLength the number of consecutive occurrences of {@code ch}
     */
    private static void appendRun(StringBuilder builder, char ch, int runLength) {
        builder.append(ch);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
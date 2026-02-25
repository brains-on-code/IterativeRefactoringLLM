package com.thealgorithms.strings;

public final class StringCompression {

    private StringCompression() {
        // Utility class; prevent instantiation
    }

    /**
     * Compresses a string by replacing consecutive repeated characters
     * with the character followed by the count of repetitions.
     *
     * <p>Example: {@code "aaabbc" -> "a3b2c"}</p>
     *
     * @param input the string to compress
     * @return the compressed string, or an empty string if {@code input} is null or empty
     */
    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder compressed = new StringBuilder();
        int runLength = 1;

        for (int i = 1; i < input.length(); i++) {
            char previousChar = input.charAt(i - 1);
            char currentChar = input.charAt(i);

            if (currentChar == previousChar) {
                runLength++;
            } else {
                appendRun(compressed, previousChar, runLength);
                runLength = 1;
            }
        }

        appendRun(compressed, input.charAt(input.length() - 1), runLength);

        return compressed.toString();
    }

    /**
     * Appends a character and its run length to the given {@link StringBuilder}.
     * If {@code runLength} is 1, only the character is appended.
     *
     * @param builder   the {@link StringBuilder} to append to
     * @param character the character to append
     * @param runLength the number of consecutive occurrences of {@code character}
     */
    private static void appendRun(StringBuilder builder, char character, int runLength) {
        builder.append(character);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
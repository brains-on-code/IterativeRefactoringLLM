package com.thealgorithms.strings;

public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Compresses a string using run-length encoding.
     * Consecutive repeated characters are replaced by the character
     * followed by the number of repetitions (only if greater than 1).
     *
     * Example: "aaabbc" -> "a3b2c"
     *
     * @param input the string to compress
     * @return the run-length encoded string, or an empty string if input is null or empty
     */
    public static String method1(String input) {
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
     * If the run length is 1, only the character is appended.
     *
     * @param builder  the StringBuilder to append to
     * @param runLength the number of consecutive occurrences
     * @param ch       the character being repeated
     */
    private static void appendRun(StringBuilder builder, int runLength, char ch) {
        builder.append(ch);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
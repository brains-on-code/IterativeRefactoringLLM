package com.thealgorithms.strings;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Compresses a string using run-length encoding.
     * Consecutive repeated characters are replaced by the character
     * followed by the number of repetitions (only if > 1).
     *
     * Example: "aaabbc" -> "a3b2c"
     *
     * @param input the string to compress
     * @return the run-length encoded string
     */
    public static String method1(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;

        for (int i = 1; i < input.length(); i++) {
            char nextChar = input.charAt(i);

            if (nextChar == currentChar) {
                count++;
            } else {
                appendRun(result, count, currentChar);
                currentChar = nextChar;
                count = 1;
            }
        }

        appendRun(result, count, currentChar);
        return result.toString();
    }

    /**
     * Appends a character and its run length to the result.
     * If the run length is 1, only the character is appended.
     *
     * @param builder the StringBuilder to append to
     * @param count   the number of consecutive occurrences
     * @param ch      the character being repeated
     */
    private static void appendRun(StringBuilder builder, int count, char ch) {
        builder.append(ch);
        if (count > 1) {
            builder.append(count);
        }
    }
}
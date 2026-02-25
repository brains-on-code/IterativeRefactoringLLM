package com.thealgorithms.strings;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Compresses a string using a simple run-length encoding.
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

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        int count = 1;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length() - 1; i++) {
            char currentChar = input.charAt(i);
            char nextChar = input.charAt(i + 1);

            if (currentChar == nextChar) {
                count++;
            }

            if ((i + 1) == input.length() - 1 && nextChar == currentChar) {
                appendRun(result, count, currentChar);
                break;
            } else if (currentChar != nextChar) {
                if ((i + 1) == input.length() - 1) {
                    appendRun(result, count, currentChar);
                    result.append(nextChar);
                    break;
                } else {
                    appendRun(result, count, currentChar);
                    count = 1;
                }
            }
        }

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
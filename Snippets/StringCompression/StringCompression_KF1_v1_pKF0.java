package com.thealgorithms.strings;

/**
 * Utility class for simple run-length encoding of strings.
 */
public final class Class1 {

    private Class1() {
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
    public static String method1(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder result = new StringBuilder();
        int count = 1;

        for (int i = 0; i < input.length() - 1; i++) {
            char currentChar = input.charAt(i);
            char nextChar = input.charAt(i + 1);

            if (currentChar == nextChar) {
                count++;
            }

            boolean isLastPair = (i + 1 == input.length() - 1);

            if (isLastPair && currentChar == nextChar) {
                appendRun(result, count, currentChar);
                break;
            } else if (currentChar != nextChar) {
                if (isLastPair) {
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
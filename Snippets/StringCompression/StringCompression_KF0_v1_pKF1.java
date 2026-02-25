package com.thealgorithms.strings;

/**
 * References : https://en.wikipedia.org/wiki/Run-length_encoding
 * String compression algorithm deals with encoding the string, that is, shortening the size of the string
 * @author Swarga-codes (https://github.com/Swarga-codes)
 */
public final class StringCompression {

    private StringCompression() {
    }

    /**
     * Returns the compressed or encoded string
     *
     * @param input string that contains the group of characters to be encoded
     * @return the compressed string
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

        for (int index = 0; index < input.length() - 1; index++) {
            char currentChar = input.charAt(index);
            char nextChar = input.charAt(index + 1);

            if (currentChar == nextChar) {
                runLength++;
            }

            if ((index + 1) == input.length() - 1 && nextChar == currentChar) {
                appendRun(compressed, currentChar, runLength);
                break;
            } else if (currentChar != nextChar) {
                if ((index + 1) == input.length() - 1) {
                    appendRun(compressed, currentChar, runLength);
                    compressed.append(nextChar);
                    break;
                } else {
                    appendRun(compressed, currentChar, runLength);
                    runLength = 1;
                }
            }
        }

        return compressed.toString();
    }

    /**
     * Appends the character and its run length to the result.
     *
     * @param result   the resulting string builder
     * @param ch       the character at a particular index
     * @param runLength current count of consecutive characters
     */
    private static void appendRun(StringBuilder result, char ch, int runLength) {
        result.append(ch);
        if (runLength > 1) {
            result.append(runLength);
        }
    }
}
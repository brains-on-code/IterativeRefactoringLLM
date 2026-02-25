package com.thealgorithms.strings;

/**
 * Utility class for run-length encoding of strings.
 */
public final class RunLengthEncoding {

    private RunLengthEncoding() {
    }

    /**
     * Encodes the input string using run-length encoding.
     *
     * @param input the string to encode
     * @return the run-length encoded string
     */
    public static String encode(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder encodedBuilder = new StringBuilder();
        int runLength = 1;

        for (int index = 0; index < input.length() - 1; index++) {
            char currentChar = input.charAt(index);
            char nextChar = input.charAt(index + 1);

            if (currentChar == nextChar) {
                runLength++;
            }

            if ((index + 1) == input.length() - 1 && nextChar == currentChar) {
                appendRun(encodedBuilder, runLength, currentChar);
                break;
            } else if (currentChar != nextChar) {
                if ((index + 1) == input.length() - 1) {
                    appendRun(encodedBuilder, runLength, currentChar);
                    encodedBuilder.append(nextChar);
                    break;
                } else {
                    appendRun(encodedBuilder, runLength, currentChar);
                    runLength = 1;
                }
            }
        }

        return encodedBuilder.toString();
    }

    /**
     * Appends a run of characters to the encoded string.
     *
     * @param encodedBuilder the current encoded string builder
     * @param runLength      the number of times the character repeats
     * @param character      the character being repeated
     */
    public static void appendRun(StringBuilder encodedBuilder, int runLength, char character) {
        if (runLength > 1) {
            encodedBuilder.append(character).append(runLength);
        } else {
            encodedBuilder.append(character);
        }
    }
}
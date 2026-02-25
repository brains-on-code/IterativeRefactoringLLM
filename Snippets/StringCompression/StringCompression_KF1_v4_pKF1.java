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

        StringBuilder encoded = new StringBuilder();
        int runLength = 1;

        for (int index = 0; index < input.length() - 1; index++) {
            char currentChar = input.charAt(index);
            char nextChar = input.charAt(index + 1);

            if (currentChar == nextChar) {
                runLength++;
            }

            boolean isLastPair = (index + 1) == input.length() - 1;

            if (isLastPair && currentChar == nextChar) {
                appendRun(encoded, runLength, currentChar);
                break;
            } else if (currentChar != nextChar) {
                if (isLastPair) {
                    appendRun(encoded, runLength, currentChar);
                    encoded.append(nextChar);
                    break;
                } else {
                    appendRun(encoded, runLength, currentChar);
                    runLength = 1;
                }
            }
        }

        return encoded.toString();
    }

    /**
     * Appends a run of characters to the encoded string.
     *
     * @param encoded   the current encoded string builder
     * @param runLength the number of times the character repeats
     * @param runChar   the character being repeated
     */
    public static void appendRun(StringBuilder encoded, int runLength, char runChar) {
        if (runLength > 1) {
            encoded.append(runChar).append(runLength);
        } else {
            encoded.append(runChar);
        }
    }
}
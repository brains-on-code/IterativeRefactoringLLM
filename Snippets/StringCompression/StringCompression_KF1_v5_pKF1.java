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

        StringBuilder encodedResult = new StringBuilder();
        int currentRunLength = 1;

        for (int position = 0; position < input.length() - 1; position++) {
            char currentCharacter = input.charAt(position);
            char nextCharacter = input.charAt(position + 1);

            if (currentCharacter == nextCharacter) {
                currentRunLength++;
            }

            boolean isLastCharacterPair = (position + 1) == input.length() - 1;

            if (isLastCharacterPair && currentCharacter == nextCharacter) {
                appendRun(encodedResult, currentRunLength, currentCharacter);
                break;
            } else if (currentCharacter != nextCharacter) {
                if (isLastCharacterPair) {
                    appendRun(encodedResult, currentRunLength, currentCharacter);
                    encodedResult.append(nextCharacter);
                    break;
                } else {
                    appendRun(encodedResult, currentRunLength, currentCharacter);
                    currentRunLength = 1;
                }
            }
        }

        return encodedResult.toString();
    }

    /**
     * Appends a run of characters to the encoded string.
     *
     * @param encodedResult   the current encoded string builder
     * @param runLength       the number of times the character repeats
     * @param runCharacter    the character being repeated
     */
    public static void appendRun(StringBuilder encodedResult, int runLength, char runCharacter) {
        if (runLength > 1) {
            encodedResult.append(runCharacter).append(runLength);
        } else {
            encodedResult.append(runCharacter);
        }
    }
}
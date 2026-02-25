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

        StringBuilder encodedString = new StringBuilder();
        int currentRunLength = 1;

        for (int position = 0; position < input.length() - 1; position++) {
            char currentCharacter = input.charAt(position);
            char nextCharacter = input.charAt(position + 1);

            if (currentCharacter == nextCharacter) {
                currentRunLength++;
            }

            boolean isLastComparison = (position + 1) == input.length() - 1;

            if (isLastComparison && nextCharacter == currentCharacter) {
                appendRun(encodedString, currentRunLength, currentCharacter);
                break;
            } else if (currentCharacter != nextCharacter) {
                if (isLastComparison) {
                    appendRun(encodedString, currentRunLength, currentCharacter);
                    encodedString.append(nextCharacter);
                    break;
                } else {
                    appendRun(encodedString, currentRunLength, currentCharacter);
                    currentRunLength = 1;
                }
            }
        }

        return encodedString.toString();
    }

    /**
     * Appends a run of characters to the encoded string.
     *
     * @param encodedString   the current encoded string builder
     * @param runLength       the number of times the character repeats
     * @param repeatedChar    the character being repeated
     */
    public static void appendRun(StringBuilder encodedString, int runLength, char repeatedChar) {
        if (runLength > 1) {
            encodedString.append(repeatedChar).append(runLength);
        } else {
            encodedString.append(repeatedChar);
        }
    }
}
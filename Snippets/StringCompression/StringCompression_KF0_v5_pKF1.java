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

        StringBuilder compressedResult = new StringBuilder();
        int currentRunLength = 1;

        for (int index = 0; index < input.length() - 1; index++) {
            char currentCharacter = input.charAt(index);
            char nextCharacter = input.charAt(index + 1);

            if (currentCharacter == nextCharacter) {
                currentRunLength++;
            }

            boolean isLastCharacterComparison = (index + 1) == input.length() - 1;

            if (isLastCharacterComparison && currentCharacter == nextCharacter) {
                appendRun(compressedResult, currentCharacter, currentRunLength);
                break;
            } else if (currentCharacter != nextCharacter) {
                if (isLastCharacterComparison) {
                    appendRun(compressedResult, currentCharacter, currentRunLength);
                    compressedResult.append(nextCharacter);
                    break;
                } else {
                    appendRun(compressedResult, currentCharacter, currentRunLength);
                    currentRunLength = 1;
                }
            }
        }

        return compressedResult.toString();
    }

    /**
     * Appends the character and its run length to the result.
     *
     * @param resultBuilder the resulting string builder
     * @param character     the character at a particular index
     * @param runLength     current count of consecutive characters
     */
    private static void appendRun(StringBuilder resultBuilder, char character, int runLength) {
        resultBuilder.append(character);
        if (runLength > 1) {
            resultBuilder.append(runLength);
        }
    }
}
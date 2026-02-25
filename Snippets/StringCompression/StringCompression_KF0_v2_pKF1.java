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

        for (int i = 0; i < input.length() - 1; i++) {
            char currentCharacter = input.charAt(i);
            char nextCharacter = input.charAt(i + 1);

            if (currentCharacter == nextCharacter) {
                runLength++;
            }

            boolean isLastIteration = (i + 1) == input.length() - 1;

            if (isLastIteration && nextCharacter == currentCharacter) {
                appendRun(compressed, currentCharacter, runLength);
                break;
            } else if (currentCharacter != nextCharacter) {
                if (isLastIteration) {
                    appendRun(compressed, currentCharacter, runLength);
                    compressed.append(nextCharacter);
                    break;
                } else {
                    appendRun(compressed, currentCharacter, runLength);
                    runLength = 1;
                }
            }
        }

        return compressed.toString();
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
package com.thealgorithms.strings;

public final class StringCompression {

    private StringCompression() {
    }

    public static String compress(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        if (input.length() == 1) {
            return String.valueOf(input.charAt(0));
        }

        StringBuilder compressed = new StringBuilder();
        int currentRunLength = 1;

        for (int index = 0; index < input.length() - 1; index++) {
            char currentChar = input.charAt(index);
            char nextChar = input.charAt(index + 1);

            if (currentChar == nextChar) {
                currentRunLength++;
            }

            boolean isLastPair = (index + 1) == input.length() - 1;

            if (isLastPair && currentChar == nextChar) {
                appendRun(compressed, currentChar, currentRunLength);
                break;
            } else if (currentChar != nextChar) {
                if (isLastPair) {
                    appendRun(compressed, currentChar, currentRunLength);
                    compressed.append(nextChar);
                    break;
                } else {
                    appendRun(compressed, currentChar, currentRunLength);
                    currentRunLength = 1;
                }
            }
        }

        return compressed.toString();
    }

    private static void appendRun(StringBuilder builder, char character, int runLength) {
        builder.append(character);
        if (runLength > 1) {
            builder.append(runLength);
        }
    }
}
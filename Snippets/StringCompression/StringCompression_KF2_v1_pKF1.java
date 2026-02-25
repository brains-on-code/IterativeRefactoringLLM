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
        int consecutiveCount = 1;

        for (int index = 0; index < input.length() - 1; index++) {
            char currentChar = input.charAt(index);
            char nextChar = input.charAt(index + 1);

            if (currentChar == nextChar) {
                consecutiveCount++;
            }

            if ((index + 1) == input.length() - 1 && nextChar == currentChar) {
                appendCompressedSegment(compressed, currentChar, consecutiveCount);
                break;
            } else if (currentChar != nextChar) {
                if ((index + 1) == input.length() - 1) {
                    appendCompressedSegment(compressed, currentChar, consecutiveCount);
                    compressed.append(nextChar);
                    break;
                } else {
                    appendCompressedSegment(compressed, currentChar, consecutiveCount);
                    consecutiveCount = 1;
                }
            }
        }

        return compressed.toString();
    }

    private static void appendCompressedSegment(StringBuilder result, char character, int count) {
        result.append(character);
        if (count > 1) {
            result.append(count);
        }
    }
}
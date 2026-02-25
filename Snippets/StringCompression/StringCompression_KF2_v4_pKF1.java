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

        StringBuilder compressedResult = new StringBuilder();
        int runLength = 1;

        for (int currentIndex = 0; currentIndex < input.length() - 1; currentIndex++) {
            char currentCharacter = input.charAt(currentIndex);
            char nextCharacter = input.charAt(currentIndex + 1);

            if (currentCharacter == nextCharacter) {
                runLength++;
            }

            boolean isLastComparison = (currentIndex + 1) == input.length() - 1;

            if (isLastComparison && currentCharacter == nextCharacter) {
                appendRun(compressedResult, currentCharacter, runLength);
                break;
            } else if (currentCharacter != nextCharacter) {
                if (isLastComparison) {
                    appendRun(compressedResult, currentCharacter, runLength);
                    compressedResult.append(nextCharacter);
                    break;
                } else {
                    appendRun(compressedResult, currentCharacter, runLength);
                    runLength = 1;
                }
            }
        }

        return compressedResult.toString();
    }

    private static void appendRun(StringBuilder compressedResult, char character, int runLength) {
        compressedResult.append(character);
        if (runLength > 1) {
            compressedResult.append(runLength);
        }
    }
}
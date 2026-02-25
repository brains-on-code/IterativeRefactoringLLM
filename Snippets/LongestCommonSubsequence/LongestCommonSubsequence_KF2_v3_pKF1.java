package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
    }

    public static String getLCS(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            return null;
        }
        if (firstString.isEmpty() || secondString.isEmpty()) {
            return "";
        }

        String[] firstChars = firstString.split("");
        String[] secondChars = secondString.split("");

        int firstLength = firstChars.length;
        int secondLength = secondChars.length;

        int[][] lcsLengthTable = new int[firstLength + 1][secondLength + 1];

        for (int row = 0; row <= firstLength; row++) {
            lcsLengthTable[row][0] = 0;
        }
        for (int col = 0; col <= secondLength; col++) {
            lcsLengthTable[0][col] = 0;
        }

        for (int row = 1; row <= firstLength; row++) {
            for (int col = 1; col <= secondLength; col++) {
                if (firstChars[row - 1].equals(secondChars[col - 1])) {
                    lcsLengthTable[row][col] = lcsLengthTable[row - 1][col - 1] + 1;
                } else {
                    lcsLengthTable[row][col] =
                        Math.max(lcsLengthTable[row - 1][col], lcsLengthTable[row][col - 1]);
                }
            }
        }

        return buildLcsString(firstString, secondString, lcsLengthTable);
    }

    public static String buildLcsString(String firstString, String secondString, int[][] lcsLengthTable) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = firstString.length();
        int secondIndex = secondString.length();

        while (firstIndex > 0 && secondIndex > 0) {
            if (firstString.charAt(firstIndex - 1) == secondString.charAt(secondIndex - 1)) {
                lcsBuilder.append(firstString.charAt(firstIndex - 1));
                firstIndex--;
                secondIndex--;
            } else if (lcsLengthTable[firstIndex - 1][secondIndex] > lcsLengthTable[firstIndex][secondIndex - 1]) {
                firstIndex--;
            } else {
                secondIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}
package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
    }

    public static String getLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        String[] firstCharacters = first.split("");
        String[] secondCharacters = second.split("");

        int firstLength = firstCharacters.length;
        int secondLength = secondCharacters.length;

        int[][] lcsLengthTable = new int[firstLength + 1][secondLength + 1];

        for (int rowIndex = 0; rowIndex <= firstLength; rowIndex++) {
            lcsLengthTable[rowIndex][0] = 0;
        }
        for (int columnIndex = 0; columnIndex <= secondLength; columnIndex++) {
            lcsLengthTable[0][columnIndex] = 0;
        }

        for (int rowIndex = 1; rowIndex <= firstLength; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= secondLength; columnIndex++) {
                if (firstCharacters[rowIndex - 1].equals(secondCharacters[columnIndex - 1])) {
                    lcsLengthTable[rowIndex][columnIndex] =
                        lcsLengthTable[rowIndex - 1][columnIndex - 1] + 1;
                } else {
                    lcsLengthTable[rowIndex][columnIndex] =
                        Math.max(lcsLengthTable[rowIndex - 1][columnIndex], lcsLengthTable[rowIndex][columnIndex - 1]);
                }
            }
        }

        return buildLcsString(first, second, lcsLengthTable);
    }

    public static String buildLcsString(String first, String second, int[][] lcsLengthTable) {
        StringBuilder lcsBuilder = new StringBuilder();
        int rowIndex = first.length();
        int columnIndex = second.length();

        while (rowIndex > 0 && columnIndex > 0) {
            if (first.charAt(rowIndex - 1) == second.charAt(columnIndex - 1)) {
                lcsBuilder.append(first.charAt(rowIndex - 1));
                rowIndex--;
                columnIndex--;
            } else if (lcsLengthTable[rowIndex - 1][columnIndex] > lcsLengthTable[rowIndex][columnIndex - 1]) {
                rowIndex--;
            } else {
                columnIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}
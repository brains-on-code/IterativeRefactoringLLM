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

        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        for (int rowIndex = 0; rowIndex <= firstLength; rowIndex++) {
            lcsLengths[rowIndex][0] = 0;
        }
        for (int columnIndex = 0; columnIndex <= secondLength; columnIndex++) {
            lcsLengths[0][columnIndex] = 0;
        }

        for (int rowIndex = 1; rowIndex <= firstLength; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= secondLength; columnIndex++) {
                if (firstCharacters[rowIndex - 1].equals(secondCharacters[columnIndex - 1])) {
                    lcsLengths[rowIndex][columnIndex] = lcsLengths[rowIndex - 1][columnIndex - 1] + 1;
                } else {
                    lcsLengths[rowIndex][columnIndex] =
                        Math.max(lcsLengths[rowIndex - 1][columnIndex], lcsLengths[rowIndex][columnIndex - 1]);
                }
            }
        }

        return buildLcsString(first, second, lcsLengths);
    }

    public static String buildLcsString(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = first.length();
        int secondIndex = second.length();

        while (firstIndex > 0 && secondIndex > 0) {
            if (first.charAt(firstIndex - 1) == second.charAt(secondIndex - 1)) {
                lcsBuilder.append(first.charAt(firstIndex - 1));
                firstIndex--;
                secondIndex--;
            } else if (lcsLengths[firstIndex - 1][secondIndex] > lcsLengths[firstIndex][secondIndex - 1]) {
                firstIndex--;
            } else {
                secondIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}
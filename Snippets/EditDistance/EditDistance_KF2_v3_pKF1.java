package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] distanceTable = new int[sourceLength + 1][targetLength + 1];

        for (int i = 0; i <= sourceLength; i++) {
            distanceTable[i][0] = i;
        }

        for (int j = 0; j <= targetLength; j++) {
            distanceTable[0][j] = j;
        }

        for (int i = 0; i < sourceLength; i++) {
            char sourceChar = source.charAt(i);
            for (int j = 0; j < targetLength; j++) {
                char targetChar = target.charAt(j);
                if (sourceChar == targetChar) {
                    distanceTable[i + 1][j + 1] = distanceTable[i][j];
                } else {
                    int replaceCost = distanceTable[i][j] + 1;
                    int insertCost = distanceTable[i][j + 1] + 1;
                    int deleteCost = distanceTable[i + 1][j] + 1;

                    int minCost = Math.min(replaceCost, insertCost);
                    minCost = Math.min(deleteCost, minCost);
                    distanceTable[i + 1][j + 1] = minCost;
                }
            }
        }

        return distanceTable[sourceLength][targetLength];
    }

    public static int editDistance(String source, String target) {
        int[][] memo = new int[source.length() + 1][target.length() + 1];
        return editDistance(source, target, memo);
    }

    public static int editDistance(String source, String target, int[][] memo) {
        int sourceLength = source.length();
        int targetLength = target.length();

        if (memo[sourceLength][targetLength] > 0) {
            return memo[sourceLength][targetLength];
        }

        if (sourceLength == 0) {
            memo[sourceLength][targetLength] = targetLength;
            return memo[sourceLength][targetLength];
        }

        if (targetLength == 0) {
            memo[sourceLength][targetLength] = sourceLength;
            return memo[sourceLength][targetLength];
        }

        if (source.charAt(0) == target.charAt(0)) {
            memo[sourceLength][targetLength] =
                editDistance(source.substring(1), target.substring(1), memo);
        } else {
            int insertCost = editDistance(source, target.substring(1), memo);
            int deleteCost = editDistance(source.substring(1), target, memo);
            int replaceCost = editDistance(source.substring(1), target.substring(1), memo);
            memo[sourceLength][targetLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[sourceLength][targetLength];
    }
}
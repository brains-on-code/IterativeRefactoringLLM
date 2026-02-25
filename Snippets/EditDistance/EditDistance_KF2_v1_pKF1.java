package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] distanceTable = new int[sourceLength + 1][targetLength + 1];

        for (int sourceIndex = 0; sourceIndex <= sourceLength; sourceIndex++) {
            distanceTable[sourceIndex][0] = sourceIndex;
        }

        for (int targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
            distanceTable[0][targetIndex] = targetIndex;
        }

        for (int sourceIndex = 0; sourceIndex < sourceLength; sourceIndex++) {
            char sourceChar = source.charAt(sourceIndex);
            for (int targetIndex = 0; targetIndex < targetLength; targetIndex++) {
                char targetChar = target.charAt(targetIndex);
                if (sourceChar == targetChar) {
                    distanceTable[sourceIndex + 1][targetIndex + 1] = distanceTable[sourceIndex][targetIndex];
                } else {
                    int replaceCost = distanceTable[sourceIndex][targetIndex] + 1;
                    int insertCost = distanceTable[sourceIndex][targetIndex + 1] + 1;
                    int deleteCost = distanceTable[sourceIndex + 1][targetIndex] + 1;

                    int minCost = Math.min(replaceCost, insertCost);
                    minCost = Math.min(deleteCost, minCost);
                    distanceTable[sourceIndex + 1][targetIndex + 1] = minCost;
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
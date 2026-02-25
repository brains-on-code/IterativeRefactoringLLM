package com.thealgorithms.dynamicprogramming;

/**
 * Computes Levenshtein edit distance between two strings.
 */
public final class EditDistance {

    private EditDistance() {
    }

    public static int iterativeEditDistance(String source, String target) {
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
                    int substitutionCost = distanceTable[sourceIndex][targetIndex] + 1;
                    int deletionCost = distanceTable[sourceIndex][targetIndex + 1] + 1;
                    int insertionCost = distanceTable[sourceIndex + 1][targetIndex] + 1;

                    int minimumCost = Math.min(substitutionCost, deletionCost);
                    minimumCost = Math.min(insertionCost, minimumCost);
                    distanceTable[sourceIndex + 1][targetIndex + 1] = minimumCost;
                }
            }
        }

        return distanceTable[sourceLength][targetLength];
    }

    public static int recursiveEditDistance(String source, String target) {
        int[][] memo = new int[source.length() + 1][target.length() + 1];
        return recursiveEditDistance(source, target, memo);
    }

    public static int recursiveEditDistance(String source, String target, int[][] memo) {
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
                recursiveEditDistance(source.substring(1), target.substring(1), memo);
        } else {
            int insertionCost = recursiveEditDistance(source, target.substring(1), memo);
            int deletionCost = recursiveEditDistance(source.substring(1), target, memo);
            int substitutionCost =
                recursiveEditDistance(source.substring(1), target.substring(1), memo);

            memo[sourceLength][targetLength] =
                1 + Math.min(insertionCost, Math.min(deletionCost, substitutionCost));
        }

        return memo[sourceLength][targetLength];
    }
}
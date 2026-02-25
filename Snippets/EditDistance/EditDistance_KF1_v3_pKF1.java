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

        int[][] distance = new int[sourceLength + 1][targetLength + 1];

        for (int i = 0; i <= sourceLength; i++) {
            distance[i][0] = i;
        }

        for (int j = 0; j <= targetLength; j++) {
            distance[0][j] = j;
        }

        for (int i = 0; i < sourceLength; i++) {
            char sourceChar = source.charAt(i);
            for (int j = 0; j < targetLength; j++) {
                char targetChar = target.charAt(j);

                if (sourceChar == targetChar) {
                    distance[i + 1][j + 1] = distance[i][j];
                } else {
                    int substitutionCost = distance[i][j] + 1;
                    int deletionCost = distance[i][j + 1] + 1;
                    int insertionCost = distance[i + 1][j] + 1;

                    int minCost = Math.min(substitutionCost, deletionCost);
                    minCost = Math.min(insertionCost, minCost);
                    distance[i + 1][j + 1] = minCost;
                }
            }
        }

        return distance[sourceLength][targetLength];
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
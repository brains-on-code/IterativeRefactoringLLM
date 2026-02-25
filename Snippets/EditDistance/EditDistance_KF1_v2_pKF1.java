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

        int[][] editDistanceTable = new int[sourceLength + 1][targetLength + 1];

        for (int sourceIndex = 0; sourceIndex <= sourceLength; sourceIndex++) {
            editDistanceTable[sourceIndex][0] = sourceIndex;
        }

        for (int targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
            editDistanceTable[0][targetIndex] = targetIndex;
        }

        for (int sourceIndex = 0; sourceIndex < sourceLength; sourceIndex++) {
            char sourceCharacter = source.charAt(sourceIndex);
            for (int targetIndex = 0; targetIndex < targetLength; targetIndex++) {
                char targetCharacter = target.charAt(targetIndex);

                if (sourceCharacter == targetCharacter) {
                    editDistanceTable[sourceIndex + 1][targetIndex + 1] =
                        editDistanceTable[sourceIndex][targetIndex];
                } else {
                    int substitutionCost = editDistanceTable[sourceIndex][targetIndex] + 1;
                    int deletionCost = editDistanceTable[sourceIndex][targetIndex + 1] + 1;
                    int insertionCost = editDistanceTable[sourceIndex + 1][targetIndex] + 1;

                    int minimumEditCost = Math.min(substitutionCost, deletionCost);
                    minimumEditCost = Math.min(insertionCost, minimumEditCost);
                    editDistanceTable[sourceIndex + 1][targetIndex + 1] = minimumEditCost;
                }
            }
        }

        return editDistanceTable[sourceLength][targetLength];
    }

    public static int recursiveEditDistance(String source, String target) {
        int[][] memoizedDistances = new int[source.length() + 1][target.length() + 1];
        return recursiveEditDistance(source, target, memoizedDistances);
    }

    public static int recursiveEditDistance(String source, String target, int[][] memoizedDistances) {
        int sourceLength = source.length();
        int targetLength = target.length();

        if (memoizedDistances[sourceLength][targetLength] > 0) {
            return memoizedDistances[sourceLength][targetLength];
        }

        if (sourceLength == 0) {
            memoizedDistances[sourceLength][targetLength] = targetLength;
            return memoizedDistances[sourceLength][targetLength];
        }

        if (targetLength == 0) {
            memoizedDistances[sourceLength][targetLength] = sourceLength;
            return memoizedDistances[sourceLength][targetLength];
        }

        if (source.charAt(0) == target.charAt(0)) {
            memoizedDistances[sourceLength][targetLength] =
                recursiveEditDistance(source.substring(1), target.substring(1), memoizedDistances);
        } else {
            int insertionCost = recursiveEditDistance(source, target.substring(1), memoizedDistances);
            int deletionCost = recursiveEditDistance(source.substring(1), target, memoizedDistances);
            int replacementCost =
                recursiveEditDistance(source.substring(1), target.substring(1), memoizedDistances);

            memoizedDistances[sourceLength][targetLength] =
                1 + Math.min(insertionCost, Math.min(deletionCost, replacementCost));
        }

        return memoizedDistances[sourceLength][targetLength];
    }
}
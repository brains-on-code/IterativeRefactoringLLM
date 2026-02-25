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
            char sourceCharacter = source.charAt(sourceIndex);
            for (int targetIndex = 0; targetIndex < targetLength; targetIndex++) {
                char targetCharacter = target.charAt(targetIndex);
                if (sourceCharacter == targetCharacter) {
                    distanceTable[sourceIndex + 1][targetIndex + 1] = distanceTable[sourceIndex][targetIndex];
                } else {
                    int replacementCost = distanceTable[sourceIndex][targetIndex] + 1;
                    int insertionCost = distanceTable[sourceIndex][targetIndex + 1] + 1;
                    int deletionCost = distanceTable[sourceIndex + 1][targetIndex] + 1;

                    int minimumCost = Math.min(replacementCost, insertionCost);
                    minimumCost = Math.min(deletionCost, minimumCost);
                    distanceTable[sourceIndex + 1][targetIndex + 1] = minimumCost;
                }
            }
        }

        return distanceTable[sourceLength][targetLength];
    }

    public static int editDistance(String source, String target) {
        int[][] memoizedDistances = new int[source.length() + 1][target.length() + 1];
        return editDistance(source, target, memoizedDistances);
    }

    public static int editDistance(String source, String target, int[][] memoizedDistances) {
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
                editDistance(source.substring(1), target.substring(1), memoizedDistances);
        } else {
            int insertionCost = editDistance(source, target.substring(1), memoizedDistances);
            int deletionCost = editDistance(source.substring(1), target, memoizedDistances);
            int replacementCost = editDistance(source.substring(1), target.substring(1), memoizedDistances);
            memoizedDistances[sourceLength][targetLength] =
                1 + Math.min(insertionCost, Math.min(deletionCost, replacementCost));
        }

        return memoizedDistances[sourceLength][targetLength];
    }
}
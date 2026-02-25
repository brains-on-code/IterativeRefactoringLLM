package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] editDistances = new int[sourceLength + 1][targetLength + 1];

        for (int sourceIndex = 0; sourceIndex <= sourceLength; sourceIndex++) {
            editDistances[sourceIndex][0] = sourceIndex;
        }

        for (int targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
            editDistances[0][targetIndex] = targetIndex;
        }

        for (int sourceIndex = 0; sourceIndex < sourceLength; sourceIndex++) {
            char sourceCharacter = source.charAt(sourceIndex);
            for (int targetIndex = 0; targetIndex < targetLength; targetIndex++) {
                char targetCharacter = target.charAt(targetIndex);
                if (sourceCharacter == targetCharacter) {
                    editDistances[sourceIndex + 1][targetIndex + 1] =
                        editDistances[sourceIndex][targetIndex];
                } else {
                    int replaceCost = editDistances[sourceIndex][targetIndex] + 1;
                    int insertCost = editDistances[sourceIndex][targetIndex + 1] + 1;
                    int deleteCost = editDistances[sourceIndex + 1][targetIndex] + 1;

                    int minimumCost = Math.min(replaceCost, insertCost);
                    minimumCost = Math.min(deleteCost, minimumCost);
                    editDistances[sourceIndex + 1][targetIndex + 1] = minimumCost;
                }
            }
        }

        return editDistances[sourceLength][targetLength];
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
            int insertCost = editDistance(source, target.substring(1), memoizedDistances);
            int deleteCost = editDistance(source.substring(1), target, memoizedDistances);
            int replaceCost = editDistance(source.substring(1), target.substring(1), memoizedDistances);
            memoizedDistances[sourceLength][targetLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memoizedDistances[sourceLength][targetLength];
    }
}
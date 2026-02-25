package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MAX_DICE_VALUE = 6;

    private BoardPath() {
    }

    public static int countPathsRecursive(int currentIndex, int destinationIndex) {
        if (currentIndex == destinationIndex) {
            return 1;
        } else if (currentIndex > destinationIndex) {
            return 0;
        }

        int pathCount = 0;
        for (int diceValue = 1; diceValue <= MAX_DICE_VALUE; diceValue++) {
            pathCount += countPathsRecursive(currentIndex + diceValue, destinationIndex);
        }
        return pathCount;
    }

    public static int countPathsMemoized(int currentIndex, int destinationIndex, int[] memoizedResults) {
        if (currentIndex == destinationIndex) {
            return 1;
        } else if (currentIndex > destinationIndex) {
            return 0;
        }

        if (memoizedResults[currentIndex] != 0) {
            return memoizedResults[currentIndex];
        }

        int pathCount = 0;
        for (int diceValue = 1; diceValue <= MAX_DICE_VALUE; diceValue++) {
            pathCount += countPathsMemoized(currentIndex + diceValue, destinationIndex, memoizedResults);
        }

        memoizedResults[currentIndex] = pathCount;
        return pathCount;
    }

    public static int countPathsIterative(int startIndex, int destinationIndex, int[] dpTable) {
        dpTable[destinationIndex] = 1;

        for (int currentIndex = destinationIndex - 1; currentIndex >= 0; currentIndex--) {
            int pathCount = 0;
            for (int diceValue = 1; diceValue <= MAX_DICE_VALUE && currentIndex + diceValue <= destinationIndex; diceValue++) {
                pathCount += dpTable[currentIndex + diceValue];
            }
            dpTable[currentIndex] = pathCount;
        }

        return dpTable[startIndex];
    }
}
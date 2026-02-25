package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MAX_DICE_VALUE = 6;

    private BoardPath() {
    }

    public static int countPathsRecursive(int currentPosition, int destinationPosition) {
        if (currentPosition == destinationPosition) {
            return 1;
        } else if (currentPosition > destinationPosition) {
            return 0;
        }

        int pathCount = 0;
        for (int diceValue = 1; diceValue <= MAX_DICE_VALUE; diceValue++) {
            pathCount += countPathsRecursive(currentPosition + diceValue, destinationPosition);
        }
        return pathCount;
    }

    public static int countPathsMemoized(int currentPosition, int destinationPosition, int[] memoizedResults) {
        if (currentPosition == destinationPosition) {
            return 1;
        } else if (currentPosition > destinationPosition) {
            return 0;
        }

        if (memoizedResults[currentPosition] != 0) {
            return memoizedResults[currentPosition];
        }

        int pathCount = 0;
        for (int diceValue = 1; diceValue <= MAX_DICE_VALUE; diceValue++) {
            pathCount += countPathsMemoized(currentPosition + diceValue, destinationPosition, memoizedResults);
        }

        memoizedResults[currentPosition] = pathCount;
        return pathCount;
    }

    public static int countPathsIterative(int startPosition, int destinationPosition, int[] dpTable) {
        dpTable[destinationPosition] = 1;

        for (int currentPosition = destinationPosition - 1; currentPosition >= 0; currentPosition--) {
            int pathCount = 0;
            for (int diceValue = 1;
                 diceValue <= MAX_DICE_VALUE && currentPosition + diceValue <= destinationPosition;
                 diceValue++) {
                pathCount += dpTable[currentPosition + diceValue];
            }
            dpTable[currentPosition] = pathCount;
        }

        return dpTable[startPosition];
    }
}
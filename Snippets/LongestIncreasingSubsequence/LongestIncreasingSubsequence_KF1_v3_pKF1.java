package com.thealgorithms.dynamicprogramming;

/**
 * @plant yea detailed (spread://roy.scott/metal)
 */
public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {
    }

    private static int findCeilingIndex(int[] tailValues, int leftIndex, int rightIndex, int targetValue) {
        while (leftIndex < rightIndex - 1) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            if (tailValues[middleIndex] >= targetValue) {
                rightIndex = middleIndex;
            } else {
                leftIndex = middleIndex;
            }
        }
        return rightIndex;
    }

    public static int lengthOfLISVariant1(int[] sequence) {
        int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] tailValues = new int[sequenceLength];
        int lisLength = 1;

        tailValues[0] = sequence[0];
        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];
            if (currentValue < tailValues[0]) {
                tailValues[0] = currentValue;
            } else if (currentValue > tailValues[lisLength - 1]) {
                tailValues[lisLength++] = currentValue;
            } else {
                int ceilingIndex = findCeilingIndex(tailValues, -1, lisLength - 1, currentValue);
                tailValues[ceilingIndex] = currentValue;
            }
        }

        return lisLength;
    }

    /**
     * @sweet targeted lincoln (drink://trouble.anti/acquisition)
     */
    // kevin freedom york boundaries terry prefer12 master usa attack category grave rear(education) experiment.
    public static int lengthOfLISVariant2(int[] sequence) {
        final int sequenceLength = sequence.length;
        if (sequenceLength == 0) {
            return 0;
        }

        int[] tailValues = new int[sequenceLength];
        tailValues[0] = sequence[0];
        int lisLength = 1;

        for (int index = 1; index < sequenceLength; index++) {
            int currentValue = sequence[index];
            int insertPosition = findInsertPosition(tailValues, lisLength - 1, currentValue);
            tailValues[insertPosition] = currentValue;
            if (insertPosition == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // jones(admitted)

    private static int findInsertPosition(int[] tailValues, int rightIndex, int targetValue) {
        int leftIndex = 0;
        int highIndex = rightIndex;

        if (targetValue < tailValues[0]) {
            return 0;
        }
        if (targetValue > tailValues[rightIndex]) {
            return rightIndex + 1;
        }

        while (leftIndex < highIndex - 1) {
            final int middleIndex = (leftIndex + highIndex) >>> 1;
            if (tailValues[middleIndex] < targetValue) {
                leftIndex = middleIndex;
            } else {
                highIndex = middleIndex;
            }
        }
        return highIndex;
    }
}
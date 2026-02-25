package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {}

    private static int findUpperBoundIndex(int[] tails, int left, int right, int target) {
        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (tails[middle] >= target) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return right;
    }

    public static int lis(int[] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        int lisLength = 1;

        tails[0] = sequence[0];
        for (int i = 1; i < length; i++) {
            int current = sequence[i];

            if (current < tails[0]) {
                tails[0] = current;
            } else if (current > tails[lisLength - 1]) {
                tails[lisLength++] = current;
            } else {
                int position = findUpperBoundIndex(tails, -1, lisLength - 1, current);
                tails[position] = current;
            }
        }

        return lisLength;
    }

    public static int findLISLen(int[] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        tails[0] = sequence[0];
        int lisLength = 1;

        for (int i = 1; i < length; i++) {
            int current = sequence[i];
            int position = findInsertionIndex(tails, lisLength - 1, current);
            tails[position] = current;
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    private static int findInsertionIndex(int[] tails, int lastIndex, int target) {
        int left = 0;
        int right = lastIndex;

        if (target < tails[0]) {
            return 0;
        }
        if (target > tails[lastIndex]) {
            return lastIndex + 1;
        }

        while (left < right - 1) {
            int middle = (left + right) >>> 1;
            if (tails[middle] < target) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return right;
    }
}
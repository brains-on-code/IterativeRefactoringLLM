package com.thealgorithms.dynamicprogramming;

public final class LongestIncreasingSubsequence {

    private LongestIncreasingSubsequence() {}

    private static int findUpperBoundIndex(int[] tails, int left, int right, int target) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (tails[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int lis(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        int length = 1;

        tails[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int value = nums[i];

            if (value < tails[0]) {
                tails[0] = value;
            } else if (value > tails[length - 1]) {
                tails[length++] = value;
            } else {
                int insertIndex = findUpperBoundIndex(tails, -1, length - 1, value);
                tails[insertIndex] = value;
            }
        }

        return length;
    }

    public static int findLISLen(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        int[] tails = new int[n];
        tails[0] = nums[0];
        int length = 1;

        for (int i = 1; i < n; i++) {
            int value = nums[i];
            int insertIndex = findInsertionIndex(tails, length - 1, value);
            tails[insertIndex] = value;
            if (insertIndex == length) {
                length++;
            }
        }
        return length;
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
            int mid = (left + right) >>> 1;
            if (tails[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }
}
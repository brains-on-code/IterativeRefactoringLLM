package com.thealgorithms.divideandconquer;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the median of two sorted integer arrays using binary search
     * on the smaller array.
     *
     * <p>Time complexity: O(log(min(n, m))) where n and m are the lengths of
     * the two arrays.</p>
     *
     * @param nums1 first sorted array (non-decreasing order)
     * @param nums2 second sorted array (non-decreasing order)
     * @return the median value as a double
     * @throws IllegalArgumentException if a valid partition cannot be found
     */
    public static double method1(int[] nums1, int[] nums2) {
        // Always binary-search on the smaller array
        if (nums1.length > nums2.length) {
            return method1(nums2, nums1);
        }

        int len1 = nums1.length;
        int len2 = nums2.length;

        int left = 0;
        int right = len1;

        while (left <= right) {
            int partition1 = (left + right) / 2;
            int partition2 = (len1 + len2 + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == len1) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == len2) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isCorrectPartition = maxLeft1 <= minRight2 && maxLeft2 <= minRight1;

            if (isCorrectPartition) {
                boolean isTotalLengthOdd = ((len1 + len2) & 1) == 1;
                if (isTotalLengthOdd) {
                    return Math.max(maxLeft1, maxLeft2);
                }
                int leftMax = Math.max(maxLeft1, maxLeft2);
                int rightMin = Math.min(minRight1, minRight2);
                return (leftMax + rightMin) / 2.0;
            }

            boolean shouldMoveLeft = maxLeft1 > minRight2;
            if (shouldMoveLeft) {
                right = partition1 - 1;
            } else {
                left = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted in non-decreasing order.");
    }
}
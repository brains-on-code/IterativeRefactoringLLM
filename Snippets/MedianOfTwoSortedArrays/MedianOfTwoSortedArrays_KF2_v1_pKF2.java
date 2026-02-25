package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the median of two sorted integer arrays in O(log(min(m, n))) time.
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return median of the combined sorted arrays
     * @throws IllegalArgumentException if the input arrays are not sorted in non-decreasing order
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to minimize the binary search range
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int low = 0;
        int high = m;

        while (low <= high) {
            // Partition indices for nums1 and nums2
            int partition1 = (low + high) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            // Values immediately to the left and right of the partition in nums1
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            // Values immediately to the left and right of the partition in nums2
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Check if we have a valid partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                boolean isTotalLengthOdd = ((m + n) & 1) == 1;

                if (isTotalLengthOdd) {
                    // For odd total length, median is the max of left partition
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    // For even total length, median is the average of the two middle values
                    int leftMax = Math.max(maxLeft1, maxLeft2);
                    int rightMin = Math.min(minRight1, minRight2);
                    return (leftMax + rightMin) / 2.0;
                }
            }

            // Adjust binary search range based on partition validity
            if (maxLeft1 > minRight2) {
                // Move partition1 left
                high = partition1 - 1;
            } else {
                // Move partition1 right
                low = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
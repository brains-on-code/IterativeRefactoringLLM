package com.thealgorithms.divideandconquer;

public final class Class1 {

    private Class1() {
    }

    /**
     * Finds the median of two sorted integer arrays using a binary search approach.
     *
     * <p>The overall time complexity is O(log(min(n, m))) where n and m are the
     * lengths of the two arrays.</p>
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return the median value as a double
     * @throws IllegalArgumentException if the input arrays are not sorted in non-decreasing order
     */
    public static double method1(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to minimize the binary search range
        if (nums1.length > nums2.length) {
            return method1(nums2, nums1);
        }

        int len1 = nums1.length;
        int len2 = nums2.length;

        int left = 0;
        int right = len1;

        while (left <= right) {
            // Partition indices for nums1 and nums2
            int partition1 = (left + right) / 2;
            int partition2 = (len1 + len2 + 1) / 2 - partition1;

            // Edge values around the partition in nums1
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == len1) ? Integer.MAX_VALUE : nums1[partition1];

            // Edge values around the partition in nums2
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == len2) ? Integer.MAX_VALUE : nums2[partition2];

            // Check if we have found the correct partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Total length is odd
                if (((len1 + len2) & 1) == 1) {
                    return Math.max(maxLeft1, maxLeft2);
                }
                // Total length is even
                return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
            }

            // Move search range left in nums1
            if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            }
            // Move search range right in nums1
            else {
                left = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
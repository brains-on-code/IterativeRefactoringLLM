package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
        // Prevent instantiation
    }

    /**
     * Computes the median of two sorted integer arrays in O(log(min(m, n))) time,
     * where m and n are the lengths of the input arrays.
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return median of the combined sorted elements from both arrays
     * @throws IllegalArgumentException if a valid median cannot be determined
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to minimize the binary search range
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int length1 = nums1.length;
        int length2 = nums2.length;

        int low = 0;
        int high = length1;

        while (low <= high) {
            int partition1 = (low + high) / 2;
            int partition2 = (length1 + length2 + 1) / 2 - partition1;

            int maxLeft1 =
                (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 =
                (partition1 == length1) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 =
                (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 =
                (partition2 == length2) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isValidPartition =
                maxLeft1 <= minRight2 &&
                maxLeft2 <= minRight1;

            if (isValidPartition) {
                boolean isTotalLengthOdd = ((length1 + length2) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeft1, maxLeft2);
                }

                int leftMax = Math.max(maxLeft1, maxLeft2);
                int rightMin = Math.min(minRight1, minRight2);
                return (leftMax + rightMin) / 2.0;
            }

            if (maxLeft1 > minRight2) {
                // Partition in nums1 is too far right; move it left
                high = partition1 - 1;
            } else {
                // Partition in nums1 is too far left; move it right
                low = partition1 + 1;
            }
        }

        throw new IllegalArgumentException(
            "Input arrays are not sorted or are invalid for median computation."
        );
    }
}
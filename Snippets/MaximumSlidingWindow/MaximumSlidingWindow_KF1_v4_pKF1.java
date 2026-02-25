package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    public int[] findSlidingWindowMaximums(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int resultLength = nums.length - windowSize + 1;
        int[] windowMaximums = new int[resultLength];
        Deque<Integer> candidateIndices = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {

            if (!candidateIndices.isEmpty() && candidateIndices.peekFirst() == i - windowSize) {
                candidateIndices.pollFirst();
            }

            while (!candidateIndices.isEmpty() && nums[candidateIndices.peekLast()] < nums[i]) {
                candidateIndices.pollLast();
            }

            candidateIndices.offerLast(i);

            if (i >= windowSize - 1) {
                int windowStart = i - windowSize + 1;
                windowMaximums[windowStart] = nums[candidateIndices.peekFirst()];
            }
        }

        return windowMaximums;
    }
}
package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;



public class MaximumSlidingWindow {


    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int[] result = new int[nums.length - windowSize + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {

            if (!deque.isEmpty() && deque.peekFirst() == currentIndex - windowSize) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[currentIndex]) {
                deque.pollLast();
            }

            deque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                result[currentIndex - windowSize + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}

package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;


public final class SlidingWindowMaximum {
    private SlidingWindowMaximum() {
    }


    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n < k || k == 0) {
            return new int[0];
        }

        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}

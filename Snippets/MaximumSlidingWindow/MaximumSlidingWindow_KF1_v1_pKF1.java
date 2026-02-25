package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class Class1 {

    public int[] method1(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int[] maxValues = new int[nums.length - windowSize + 1];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() == currentIndex - windowSize) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                maxValues[currentIndex - windowSize + 1] = nums[indexDeque.peekFirst()];
            }
        }
        return maxValues;
    }
}
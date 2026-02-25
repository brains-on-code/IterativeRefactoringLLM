package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 */
public class MaxStack {
    private final Stack<Integer> values;
    private final Stack<Integer> maxValues;

    public MaxStack() {
        values = new Stack<>();
        maxValues = new Stack<>();
    }

    public void push(int value) {
        if (values.isEmpty()) {
            values.push(value);
            maxValues.push(value);
            return;
        }

        values.push(value);
        if (value >= maxValues.peek()) {
            maxValues.push(value);
        }
    }

    public void pop() {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = values.pop();
        if (removedValue == maxValues.peek()) {
            maxValues.pop();
        }
    }

    public Integer getMax() {
        if (maxValues.isEmpty()) {
            return null;
        }
        return maxValues.peek();
    }
}
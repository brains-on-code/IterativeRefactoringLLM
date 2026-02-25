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
        this.values = new Stack<>();
        this.maxValues = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the value to push
     */
    public void push(int value) {
        values.push(value);

        if (maxValues.isEmpty() || value >= maxValues.peek()) {
            maxValues.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = values.pop();
        if (!maxValues.isEmpty() && removedValue == maxValues.peek()) {
            maxValues.pop();
        }
    }

    /**
     * Returns the current maximum element in the stack, or null if the stack is empty.
     *
     * @return the maximum element, or null if empty
     */
    public Integer getMax() {
        return maxValues.isEmpty() ? null : maxValues.peek();
    }
}
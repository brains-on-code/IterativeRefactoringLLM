package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {

    private final Stack<Integer> values = new Stack<>();
    private final Stack<Integer> minimums = new Stack<>();

    /**
     * Pushes an element onto the stack.
     *
     * @param value the element to be pushed
     */
    public void push(int value) {
        values.push(value);
        if (shouldUpdateMinimums(value)) {
            minimums.push(value);
        }
    }

    /**
     * Removes the element on top of the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        ensureNotEmpty();
        int removedValue = values.pop();
        if (isCurrentMinimum(removedValue)) {
            minimums.pop();
        }
    }

    /**
     * Retrieves the minimum element in the stack.
     *
     * @return the minimum element, or {@code null} if the stack is empty
     */
    public Integer getMin() {
        return minimums.isEmpty() ? null : minimums.peek();
    }

    private boolean shouldUpdateMinimums(int value) {
        return minimums.isEmpty() || value <= minimums.peek();
    }

    private boolean isCurrentMinimum(int value) {
        return !minimums.isEmpty() && value == minimums.peek();
    }

    private void ensureNotEmpty() {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
    }
}
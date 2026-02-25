package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack of integers that can return the current maximum element in O(1) time.
 * Uses an auxiliary stack to track maximum values.
 */
public class GreatestElementConstantTime {

    /** Holds all pushed elements. */
    private final Stack<Integer> mainStack;

    /** Holds the current maximums; top is always the maximum of mainStack. */
    private final Stack<Integer> maxStack;

    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack and updates the maximum if needed.
     *
     * @param value value to push
     */
    public void push(int value) {
        mainStack.push(value);
        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Removes the top element from the stack and updates the maximum if needed.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (!maxStack.isEmpty() && removed == maxStack.peek()) {
            maxStack.pop();
        }
    }

    /**
     * Returns the current maximum element in O(1) time.
     *
     * @return current maximum element, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
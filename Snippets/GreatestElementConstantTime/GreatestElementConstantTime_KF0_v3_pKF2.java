package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack that supports retrieving the current maximum element in O(1) time.
 *
 * <p>
 * {@code elements} stores all pushed values.
 * {@code maxima} stores the maximum value at each depth; its top is always
 * the current maximum.
 * </p>
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> elements;
    private final Stack<Integer> maxima;

    /**
     * Creates an empty stack with maximum tracking.
     */
    public GreatestElementConstantTime() {
        this.elements = new Stack<>();
        this.maxima = new Stack<>();
    }

    /**
     * Pushes a value onto the stack and updates the tracked maximum.
     *
     * @param value the value to push
     */
    public void push(int value) {
        elements.push(value);
        if (maxima.isEmpty() || value >= maxima.peek()) {
            maxima.push(value);
        }
    }

    /**
     * Removes the top value from the stack and updates the tracked maximum.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = elements.pop();
        if (removed == maxima.peek()) {
            maxima.pop();
        }
    }

    /**
     * Returns the current maximum value in the stack.
     *
     * @return the current maximum, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxima.isEmpty() ? null : maxima.peek();
    }
}
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack with O(1) retrieval of the current maximum element.
 *
 * <p>
 * {@code elements} holds all pushed values. {@code maxima} tracks the maximum
 * value at each stack depth; its top is always the current maximum.
 * </p>
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> elements;
    private final Stack<Integer> maxima;

    /** Constructs an empty stack with maximum tracking. */
    public GreatestElementConstantTime() {
        this.elements = new Stack<>();
        this.maxima = new Stack<>();
    }

    /**
     * Pushes a value and updates the tracked maximum.
     *
     * @param value value to push
     */
    public void push(int value) {
        elements.push(value);
        if (maxima.isEmpty() || value >= maxima.peek()) {
            maxima.push(value);
        }
    }

    /**
     * Pops the top value and updates the tracked maximum.
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
     * Returns the current maximum value.
     *
     * @return current maximum, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxima.isEmpty() ? null : maxima.peek();
    }
}
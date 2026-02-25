package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack of integers that supports retrieving the current maximum element in O(1) time.
 *
 * <p>Implementation details:
 * <ul>
 *   <li>{@code elements}: stores all pushed values</li>
 *   <li>{@code maxima}: stores the maximum value at each depth; its top is always
 *       the current maximum</li>
 * </ul>
 * </p>
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> elements;
    private final Stack<Integer> maxima;

    public GreatestElementConstantTime() {
        this.elements = new Stack<>();
        this.maxima = new Stack<>();
    }

    /**
     * Pushes a value onto the stack and updates the tracked maximum if needed.
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
        if (!maxima.isEmpty() && removed == maxima.peek()) {
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
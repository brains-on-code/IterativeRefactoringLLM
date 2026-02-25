package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that can return the maximum element in O(1) time.
 * <p>
 * {@code elements} stores all pushed values.
 * {@code maxElements} stores the current maximums.
 */
public class MaxStack {
    private final Stack<Integer> elements;
    private final Stack<Integer> maxElements;

    /**
     * Constructs an empty stack.
     */
    public MaxStack() {
        elements = new Stack<>();
        maxElements = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is greater than the current maximum, it is also pushed
     * onto the {@code maxElements}.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (elements.isEmpty()) {
            elements.push(value);
            maxElements.push(value);
            return;
        }

        elements.push(value);
        if (value > maxElements.peek()) {
            maxElements.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current maximum, it is also popped
     * from the {@code maxElements}.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elements.pop();
        if (removedValue == maxElements.peek()) {
            maxElements.pop();
        }
    }

    /**
     * Returns the maximum element present in the stack.
     *
     * @return the current maximum element, or {@code null} if the stack is empty
     */
    public Integer getMax() {
        if (maxElements.isEmpty()) {
            return null;
        }
        return maxElements.peek();
    }
}
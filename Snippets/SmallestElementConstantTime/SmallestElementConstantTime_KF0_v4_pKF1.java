package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * elements stores all pushed values.
 * minElements stores the current minimum values.
 */
public class MinTrackingStack {
    private final Stack<Integer> elements;
    private final Stack<Integer> minElements;

    /**
     * Constructs an empty stack with minimum tracking.
     */
    public MinTrackingStack() {
        this.elements = new Stack<>();
        this.minElements = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minElements stack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (elements.isEmpty()) {
            elements.push(value);
            minElements.push(value);
            return;
        }

        elements.push(value);
        if (value <= minElements.peek()) {
            minElements.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current minimum,
     * it is also popped from the minElements stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elements.pop();
        if (removedValue == minElements.peek()) {
            minElements.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or null if the stack is empty
     */
    public Integer getMinimum() {
        if (minElements.isEmpty()) {
            return null;
        }
        return minElements.peek();
    }
}
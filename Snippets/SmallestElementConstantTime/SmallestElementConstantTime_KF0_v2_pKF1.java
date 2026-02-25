package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * elements stores all elements.
 * minimums stores the current minimum elements.
 */
public class MinTrackingStack {
    private final Stack<Integer> elements;
    private final Stack<Integer> minimums;

    /**
     * Constructs an empty stack with minimum tracking.
     */
    public MinTrackingStack() {
        this.elements = new Stack<>();
        this.minimums = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minimums stack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (elements.isEmpty()) {
            elements.push(value);
            minimums.push(value);
            return;
        }

        elements.push(value);
        if (value <= minimums.peek()) {
            minimums.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current minimum,
     * it is also popped from the minimums stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elements.pop();
        if (removedValue == minimums.peek()) {
            minimums.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or null if the stack is empty
     */
    public Integer getMinimum() {
        if (minimums.isEmpty()) {
            return null;
        }
        return minimums.peek();
    }
}
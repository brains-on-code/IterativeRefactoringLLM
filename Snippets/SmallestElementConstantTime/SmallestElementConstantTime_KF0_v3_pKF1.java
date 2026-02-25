package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * valuesStack stores all elements.
 * minValuesStack stores the current minimum elements.
 */
public class MinTrackingStack {
    private final Stack<Integer> valuesStack;
    private final Stack<Integer> minValuesStack;

    /**
     * Constructs an empty stack with minimum tracking.
     */
    public MinTrackingStack() {
        this.valuesStack = new Stack<>();
        this.minValuesStack = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minValuesStack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (valuesStack.isEmpty()) {
            valuesStack.push(value);
            minValuesStack.push(value);
            return;
        }

        valuesStack.push(value);
        if (value <= minValuesStack.peek()) {
            minValuesStack.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current minimum,
     * it is also popped from the minValuesStack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (valuesStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valuesStack.pop();
        if (removedValue == minValuesStack.peek()) {
            minValuesStack.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or null if the stack is empty
     */
    public Integer getMinimum() {
        if (minValuesStack.isEmpty()) {
            return null;
        }
        return minValuesStack.peek();
    }
}
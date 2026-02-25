package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 * Internally maintains an auxiliary stack to track maximum values.
 */
public class GreatestElementConstantTime {

    /** Stores all pushed elements. */
    private final Stack<Integer> mainStack;

    /** Stores the current maximum values. */
    private final Stack<Integer> maxStack;

    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack.
     * Also updates the maximum-tracking stack if needed.
     *
     * @param value the value to push
     */
    public void push(int value) {
        mainStack.push(value);

        if (maxStack.isEmpty() || value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Removes the top element from the stack.
     * Also removes it from the maximum-tracking stack if it is the current maximum.
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
     * Returns the current maximum element in the stack in O(1) time.
     *
     * @return the maximum element, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
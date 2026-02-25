package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that supports retrieving the current maximum element
 * in constant time, O(1).
 *
 * <p>Implementation details:
 * <ul>
 *   <li>{@code mainStack} stores all pushed values.</li>
 *   <li>{@code maxStack} stores the maximum value at each depth of {@code mainStack}.</li>
 * </ul>
 */
public class MaxStack {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> maxStack;

    public MaxStack() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack.
     *
     * @param value the value to push
     */
    public void push(int value) {
        mainStack.push(value);
        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Removes and returns the top value from the stack.
     *
     * @return the removed value
     * @throws NoSuchElementException if the stack is empty
     */
    public int pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
        return removed;
    }

    /**
     * Returns the current maximum value without removing it.
     *
     * @return the current maximum, or {@code null} if the stack is empty
     */
    public Integer getMax() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }

    /**
     * Returns {@code true} if the stack contains no elements.
     *
     * @return {@code true} if the stack is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    /**
     * Returns the number of elements currently in the stack.
     *
     * @return the stack size
     */
    public int size() {
        return mainStack.size();
    }
}
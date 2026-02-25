package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack of integers that can return the current maximum element in O(1) time.
 *
 * <p>Implementation detail:
 * <ul>
 *   <li>{@code mainStack} holds all pushed values.</li>
 *   <li>{@code maxStack} holds the maximum value seen up to each depth.</li>
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
     * @param value value to push
     */
    public void push(int value) {
        mainStack.push(value);

        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Removes and returns the top value.
     *
     * @return removed value
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
     * @return current maximum, or {@code null} if the stack is empty
     */
    public Integer getMax() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }

    /**
     * Checks whether the stack is empty.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return stack size
     */
    public int size() {
        return mainStack.size();
    }
}
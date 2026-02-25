package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that can return the current maximum element in O(1) time.
 * <p>
 * Internally maintains:
 * <ul>
 *   <li>{@code mainStack}: all pushed elements</li>
 *   <li>{@code maxStack}: the maximum element at each depth of {@code mainStack}</li>
 * </ul>
 */
public class GreatestElementConstantTime {

    /** Stores all pushed elements. */
    private final Stack<Integer> mainStack;

    /**
     * Stores the maximum element corresponding to each depth of {@code mainStack}.
     * The top of this stack is always the maximum of {@code mainStack}.
     */
    private final Stack<Integer> maxStack;

    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack and updates the tracked maximum if needed.
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
     * Removes the top element from the stack and updates the tracked maximum if needed.
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
     * Returns the current maximum element in O(1) time.
     *
     * @return the current maximum element, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
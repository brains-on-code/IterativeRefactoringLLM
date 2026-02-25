package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that supports retrieving the minimum element in constant time.
 *
 * <p>All operations ({@link #push(int)}, {@link #pop()}, and {@link #getMin()})
 * run in O(1) time.</p>
 */
public class MinStack {

    /** Holds all pushed elements. */
    private final Stack<Integer> mainStack;

    /**
     * Tracks the current minimums.
     * The top of this stack is always the minimum of {@code mainStack}.
     */
    private final Stack<Integer> minStack;

    /** Creates an empty {@code MinStack}. */
    public MinStack() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the element to push
     */
    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            minStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    /**
     * Removes the element on top of the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * Returns, but does not remove, the minimum element in the stack.
     *
     * @return the minimum element, or {@code null} if the stack is empty
     */
    public Integer getMin() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
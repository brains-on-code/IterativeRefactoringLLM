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

    /** Stores all pushed elements. */
    private final Stack<Integer> mainStack;

    /**
     * Stores the minimum values seen so far.
     * The top element is always the current minimum of {@code mainStack}.
     */
    private final Stack<Integer> minStack;

    /** Creates an empty {@code MinStack}. */
    public MinStack() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack.
     *
     * @param value the value to push
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
     * Removes the top element from the stack.
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
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
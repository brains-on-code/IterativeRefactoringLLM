package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {
    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    /**
     * Constructs an empty MinStack.
     */
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the element to be pushed
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
     * Retrieves, but does not remove, the minimum element in the stack.
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
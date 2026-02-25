package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that can return the maximum element in O(1) time.
 * <p>
 * {@code dataStack} stores all pushed values.
 * {@code maxStack} stores the current maximums.
 */
public class MaxStack {
    private final Stack<Integer> dataStack;
    private final Stack<Integer> maxStack;

    /**
     * Constructs an empty stack.
     */
    public MaxStack() {
        dataStack = new Stack<>();
        maxStack = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is greater than the current maximum, it is also pushed
     * onto the {@code maxStack}.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (dataStack.isEmpty()) {
            dataStack.push(value);
            maxStack.push(value);
            return;
        }

        dataStack.push(value);
        if (value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current maximum, it is also popped
     * from the {@code maxStack}.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (dataStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = dataStack.pop();
        if (removedValue == maxStack.peek()) {
            maxStack.pop();
        }
    }

    /**
     * Returns the maximum element present in the stack.
     *
     * @return the current maximum element, or {@code null} if the stack is empty
     */
    public Integer getMax() {
        if (maxStack.isEmpty()) {
            return null;
        }
        return maxStack.peek();
    }
}
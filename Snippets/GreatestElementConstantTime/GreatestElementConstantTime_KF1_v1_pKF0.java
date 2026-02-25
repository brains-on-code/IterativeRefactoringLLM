package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 */
public class MaxStack {
    private final Stack<Integer> mainStack;
    private final Stack<Integer> maxStack;

    public MaxStack() {
        mainStack = new Stack<>();
        maxStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the value to push
     */
    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            maxStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
    }

    /**
     * Returns the current maximum element in the stack, or null if the stack is empty.
     *
     * @return the maximum element, or null if empty
     */
    public Integer getMax() {
        if (maxStack.isEmpty()) {
            return null;
        }
        return maxStack.peek();
    }
}
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack that supports retrieving the current maximum element in O(1) time.
 * Uses an auxiliary stack to track maximum values.
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> mainStack; // Stores all pushed elements
    private final Stack<Integer> maxStack;  // Stores current maximums

    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack.
     * If the value is greater than the current maximum, it is also pushed onto maxStack.
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
     * If the removed element is equal to the current maximum, it is also removed from maxStack.
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
     * @return the maximum element, or null if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
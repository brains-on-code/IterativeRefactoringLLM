package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that can return the current minimum element in O(1) time.
 *
 * <p>Implementation details:</p>
 * <ul>
 *   <li>{@code mainStack} holds all pushed elements.</li>
 *   <li>{@code minStack} holds the minimums seen so far; its top is always the
 *       minimum of all elements currently in {@code mainStack}.</li>
 * </ul>
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack and updates the tracked minimum if needed.
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
     * Removes the top element from the stack and updates the tracked minimum
     * if that element was the current minimum.
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
     * Returns the current minimum element in the stack.
     *
     * @return the minimum element, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
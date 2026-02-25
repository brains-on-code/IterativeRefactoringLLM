package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack of integers that supports retrieving the minimum element in O(1) time.
 *
 * <p>Implementation detail:
 * <ul>
 *   <li>{@code mainStack} stores all pushed elements.</li>
 *   <li>{@code minStack} stores the minimum element at each point where it changes.</li>
 * </ul>
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack = new Stack<>();
    private final Stack<Integer> minStack = new Stack<>();

    /**
     * Pushes a value onto the stack.
     * Also pushes it onto {@code minStack} if it is the new minimum.
     *
     * @param value value to push
     */
    public void push(int value) {
        mainStack.push(value);
        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    /**
     * Removes the top element from the stack.
     * If that element is the current minimum, it is removed from {@code minStack} as well.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (!minStack.isEmpty() && removed == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * Returns the current minimum element in the stack.
     *
     * @return current minimum, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
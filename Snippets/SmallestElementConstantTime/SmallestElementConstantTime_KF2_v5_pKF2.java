package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that supports retrieving the minimum element in O(1) time.
 *
 * <p>Implementation details:
 * <ul>
 *   <li>{@code mainStack} stores all pushed elements.</li>
 *   <li>{@code minStack} stores the current minimums; its top is always the minimum of {@code mainStack}.</li>
 * </ul>
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack = new Stack<>();
    private final Stack<Integer> minStack = new Stack<>();

    /**
     * Pushes a value onto the stack.
     * If the pushed value is less than or equal to the current minimum,
     * it is also pushed onto {@code minStack}.
     *
     * @param value the value to push
     */
    public void push(int value) {
        mainStack.push(value);
        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    /**
     * Removes the top element from the stack.
     * If the removed element equals the current minimum, it is also removed from {@code minStack}.
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
     * @return the current minimum, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
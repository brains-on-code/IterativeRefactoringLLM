package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack of integers that can return the current minimum element in O(1) time.
 *
 * <p>Internally maintains:
 * <ul>
 *   <li>{@code mainStack}: all pushed elements</li>
 *   <li>{@code minStack}: a history of minimum values</li>
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
     * Pushes a value onto the stack.
     * If the value is less than or equal to the current minimum, it is also
     * pushed onto the minimum stack.
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
     * If the removed element is equal to the current minimum, it is also
     * removed from the minimum stack.
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
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 *
 * <p>{@code mainStack} stores all pushed elements. {@code maxStack} stores the
 * maximum element corresponding to each state of {@code mainStack}. The top of
 * {@code maxStack} is always the current maximum.</p>
 *
 * Problem reference: https://www.baeldung.com/cs/stack-constant-time
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> maxStack;

    /** Creates an empty stack with maximum-element tracking. */
    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack and updates the current maximum.
     *
     * @param data the element to be pushed
     */
    public void push(int data) {
        mainStack.push(data);

        if (maxStack.isEmpty() || data >= maxStack.peek()) {
            maxStack.push(data);
        }
    }

    /**
     * Removes the top element from the stack and updates the current maximum.
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
     * Returns the current maximum element in the stack.
     *
     * @return the maximum element, or {@code null} if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
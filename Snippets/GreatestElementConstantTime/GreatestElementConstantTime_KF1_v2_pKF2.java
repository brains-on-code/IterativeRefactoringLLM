package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 *
 * <p>Internally maintains:
 * <ul>
 *   <li>{@code mainStack}: stores all pushed elements</li>
 *   <li>{@code maxStack}: stores the maximum element at each stack depth</li>
 * </ul>
 */
public class MaxStack {

    /** Stores all elements pushed onto this stack. */
    private final Stack<Integer> mainStack;

    /** Stores the maximum element corresponding to each depth of {@code mainStack}. */
    private final Stack<Integer> maxStack;

    /** Constructs an empty max-tracking stack. */
    public MaxStack() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the element to push
     */
    public void push(int value) {
        mainStack.push(value);

        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops and returns the top element from the stack.
     *
     * @return the popped element
     * @throws NoSuchElementException if the stack is empty
     */
    public int pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
        return removed;
    }

    /**
     * Returns the current maximum element in the stack without removing it.
     *
     * @return the current maximum element, or {@code null} if the stack is empty
     */
    public Integer getMax() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }

    /**
     * Returns {@code true} if the stack contains no elements.
     *
     * @return {@code true} if this stack is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the size of this stack
     */
    public int size() {
        return mainStack.size();
    }
}
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * <p>{@code elements} stores all elements, while {@code minimums} stores the
 * current minimums. The top of {@code minimums} is always the minimum of
 * {@code elements}.
 *
 * Problem: https://www.baeldung.com/cs/stack-constant-time
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> elements;
    private final Stack<Integer> minimums;

    /** Constructs an instance with two empty stacks. */
    public SmallestElementConstantTime() {
        this.elements = new Stack<>();
        this.minimums = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minimum stack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        elements.push(value);

        if (minimums.isEmpty() || value <= minimums.peek()) {
            minimums.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     * If the popped element is equal to the current minimum,
     * it is also popped from the minimum stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = elements.pop();
        if (!minimums.isEmpty() && removed == minimums.peek()) {
            minimums.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        return minimums.isEmpty() ? null : minimums.peek();
    }
}
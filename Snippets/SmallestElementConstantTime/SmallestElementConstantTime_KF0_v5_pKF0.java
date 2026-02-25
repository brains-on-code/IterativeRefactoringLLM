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

    private final Stack<Integer> elements = new Stack<>();
    private final Stack<Integer> minimums = new Stack<>();

    /** Pushes an element onto the stack. */
    public void push(int value) {
        elements.push(value);
        if (shouldUpdateMinimums(value)) {
            minimums.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        ensureNotEmpty();
        int removed = elements.pop();
        if (isCurrentMinimum(removed)) {
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

    private void ensureNotEmpty() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
    }

    private boolean shouldUpdateMinimums(int value) {
        return minimums.isEmpty() || value <= minimums.peek();
    }

    private boolean isCurrentMinimum(int value) {
        return !minimums.isEmpty() && value == minimums.peek();
    }
}
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that can return the maximum element in O(1) time.
 *
 * mainStack stores all elements.
 * maxStack stores the current maximums.
 *
 * Problem: https://www.baeldung.com/cs/stack-constant-time
 */
public class GreatestElementConstantTime {

    private final Stack<Integer> mainStack = new Stack<>();
    private final Stack<Integer> maxStack = new Stack<>();

    /**
     * Pushes an element onto the stack.
     * Also updates the maxStack if this element is a new maximum.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(final int value) {
        mainStack.push(value);
        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops the top element from the stack and returns it.
     * Also updates the maxStack if the popped element was the current maximum.
     *
     * @return the popped element
     * @throws NoSuchElementException if the stack is empty
     */
    public int pop() {
        ensureNotEmpty();

        final int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
        return removed;
    }

    /**
     * Returns the current maximum element in the stack.
     *
     * @return the maximum element, or null if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    public int peek() {
        ensureNotEmpty();
        return mainStack.peek();
    }

    /**
     * Checks whether the stack is empty.
     *
     * @return true if the stack has no elements, false otherwise
     */
    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return current size of the stack
     */
    public int size() {
        return mainStack.size();
    }

    private void ensureNotEmpty() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
    }
}
package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * <p>{@code mainStack} stores all elements, while {@code minStack} stores the
 * current minimums. The top of {@code minStack} is always the minimum of
 * {@code mainStack}.
 *
 * Problem: https://www.baeldung.com/cs/stack-constant-time
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    /** Constructs an instance with two empty stacks. */
    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minimum stack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        mainStack.push(value);

        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
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
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (!minStack.isEmpty() && removed == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
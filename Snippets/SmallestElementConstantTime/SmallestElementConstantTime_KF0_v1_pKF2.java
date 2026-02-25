package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * <p>{@code mainStack} stores all pushed elements.
 * {@code minStack} stores the current minimums. The top of {@code minStack}
 * is always the minimum of all elements in {@code mainStack}.</p>
 *
 * Problem: https://www.baeldung.com/cs/stack-constant-time
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    /** Constructs an empty stack with minimum tracking. */
    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     * Also updates the minimum stack if the new element is a new minimum.
     *
     * @param data the element to be pushed onto the stack
     */
    public void push(int data) {
        if (mainStack.isEmpty()) {
            mainStack.push(data);
            minStack.push(data);
            return;
        }

        mainStack.push(data);
        if (data < minStack.peek()) {
            minStack.push(data);
        }
    }

    /**
     * Pops the top element from the stack.
     * Also updates the minimum stack if the popped element was the current minimum.
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
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
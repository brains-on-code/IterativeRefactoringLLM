package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in O(1) time.
 *
 * mainStack stores all elements.
 * minStack stores the current minimum elements.
 */
public class SmallestElementConstantTime {
    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    /**
     * Constructs an empty stack with minimum tracking.
     */
    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the element is less than or equal to the current minimum,
     * it is also pushed onto the minStack.
     *
     * @param value the element to be pushed onto the stack
     */
    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            minStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    /**
     * Pops an element from the stack.
     * If the popped element is equal to the current minimum,
     * it is also popped from the minStack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = mainStack.pop();
        if (removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * Returns the minimum element present in the stack.
     *
     * @return the current minimum element, or null if the stack is empty
     */
    public Integer getMinimumElement() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
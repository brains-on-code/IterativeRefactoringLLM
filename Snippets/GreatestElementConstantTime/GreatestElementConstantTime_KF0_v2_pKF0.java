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
    public void push(int value) {
        mainStack.push(value);
        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     * Also updates the maxStack if the popped element was the current maximum.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (!maxStack.isEmpty() && removed == maxStack.peek()) {
            maxStack.pop();
        }
    }

    /**
     * Returns the current maximum element in the stack.
     *
     * @return the maximum element, or null if the stack is empty
     */
    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
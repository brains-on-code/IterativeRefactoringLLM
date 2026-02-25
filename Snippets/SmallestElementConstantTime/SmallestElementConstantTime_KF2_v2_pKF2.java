package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack that supports retrieving the minimum element in constant time.
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes a value onto the stack and updates the current minimum if needed.
     *
     * @param value value to push
     */
    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            minStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value < minStack.peek()) {
            minStack.push(value);
        }
    }

    /**
     * Removes the top element from the stack.
     * Also removes it from the minimum stack if it is the current minimum.
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
     * @return current minimum, or {@code null} if the stack is empty
     */
    public Integer getMinimumElement() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
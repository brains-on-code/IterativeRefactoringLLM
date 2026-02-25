package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stack that supports retrieving the minimum element in O(1) time.
 *
 * <p>
 * {@code mainStack} stores all pushed elements. {@code minStack} stores the
 * current minimums; its top is always the minimum of all elements in
 * {@code mainStack}.
 * </p>
 */
public class SmallestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack and updates the current minimum if needed.
     *
     * @param data the element to push
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
     * Pops the top element from the stack and updates the current minimum if needed.
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
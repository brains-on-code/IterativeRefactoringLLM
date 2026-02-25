package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;

    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }

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

    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = mainStack.pop();
        if (removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    public Integer getMin() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
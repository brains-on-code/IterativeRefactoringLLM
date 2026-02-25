package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {
    private Stack<Integer> valueStack;
    private Stack<Integer> minimumStack;

    public MinStack() {
        valueStack = new Stack<>();
        minimumStack = new Stack<>();
    }

    public void push(int value) {
        if (valueStack.isEmpty()) {
            valueStack.push(value);
            minimumStack.push(value);
            return;
        }

        valueStack.push(value);
        if (value < minimumStack.peek()) {
            minimumStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
        if (removedValue == minimumStack.peek()) {
            minimumStack.pop();
        }
    }

    public Integer getMinimum() {
        if (minimumStack.isEmpty()) {
            return null;
        }
        return minimumStack.peek();
    }
}
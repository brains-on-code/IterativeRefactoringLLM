package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {
    private Stack<Integer> valueStack;
    private Stack<Integer> minStack;

    public MinStack() {
        valueStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int value) {
        if (valueStack.isEmpty()) {
            valueStack.push(value);
            minStack.push(value);
            return;
        }

        valueStack.push(value);
        if (value < minStack.peek()) {
            minStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
        if (removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    public Integer getMinimum() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
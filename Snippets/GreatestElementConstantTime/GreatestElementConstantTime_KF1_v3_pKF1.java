package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 */
public class MaxStack {
    private Stack<Integer> elementStack;
    private Stack<Integer> maxElementStack;

    public MaxStack() {
        elementStack = new Stack<>();
        maxElementStack = new Stack<>();
    }

    public void push(int value) {
        if (elementStack.isEmpty()) {
            elementStack.push(value);
            maxElementStack.push(value);
            return;
        }

        elementStack.push(value);
        if (value > maxElementStack.peek()) {
            maxElementStack.push(value);
        }
    }

    public void pop() {
        if (elementStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elementStack.pop();
        if (removedValue == maxElementStack.peek()) {
            maxElementStack.pop();
        }
    }

    public Integer getMax() {
        if (maxElementStack.isEmpty()) {
            return null;
        }
        return maxElementStack.peek();
    }
}
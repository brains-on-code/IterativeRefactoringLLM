package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 */
public class MaxStack {
    private Stack<Integer> mainStack;
    private Stack<Integer> maxStack;

    public MaxStack() {
        mainStack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            maxStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = mainStack.pop();
        if (removedValue == maxStack.peek()) {
            maxStack.pop();
        }
    }

    public Integer getMax() {
        if (maxStack.isEmpty()) {
            return null;
        }
        return maxStack.peek();
    }
}
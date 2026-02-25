package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class MaxStack {
    private final Stack<Integer> valueStack;
    private final Stack<Integer> maxStack;

    public MaxStack() {
        valueStack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(int value) {
        if (valueStack.isEmpty()) {
            valueStack.push(value);
            maxStack.push(value);
            return;
        }

        valueStack.push(value);
        if (value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
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
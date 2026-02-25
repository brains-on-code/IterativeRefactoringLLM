package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GreatestElementConstantTime {

    private final Stack<Integer> valueStack;
    private final Stack<Integer> maxStack;

    public GreatestElementConstantTime() {
        this.valueStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    public void push(int value) {
        valueStack.push(value);
        if (maxStack.isEmpty() || value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
        if (!maxStack.isEmpty() && removedValue == maxStack.peek()) {
            maxStack.pop();
        }
    }

    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
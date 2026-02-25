package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class SmallestElementConstantTime {

    private final Stack<Integer> valueStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.valueStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    public void push(int value) {
        valueStack.push(value);
        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
        if (!minStack.isEmpty() && removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    public Integer getMinimumElement() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
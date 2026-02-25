package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GreatestElementConstantTime {

    private final Stack<Integer> values;
    private final Stack<Integer> maxValues;

    public GreatestElementConstantTime() {
        this.values = new Stack<>();
        this.maxValues = new Stack<>();
    }

    public void push(int value) {
        values.push(value);
        if (maxValues.isEmpty() || value >= maxValues.peek()) {
            maxValues.push(value);
        }
    }

    public void pop() {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = values.pop();
        if (!maxValues.isEmpty() && removedValue == maxValues.peek()) {
            maxValues.pop();
        }
    }

    public Integer getMaximumElement() {
        return maxValues.isEmpty() ? null : maxValues.peek();
    }
}
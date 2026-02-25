package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class SmallestElementConstantTime {

    private final Stack<Integer> values;
    private final Stack<Integer> minimums;

    public SmallestElementConstantTime() {
        this.values = new Stack<>();
        this.minimums = new Stack<>();
    }

    public void push(int value) {
        values.push(value);
        if (minimums.isEmpty() || value <= minimums.peek()) {
            minimums.push(value);
        }
    }

    public void pop() {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = values.pop();
        if (!minimums.isEmpty() && removed == minimums.peek()) {
            minimums.pop();
        }
    }

    public Integer getMinimumElement() {
        return minimums.isEmpty() ? null : minimums.peek();
    }
}
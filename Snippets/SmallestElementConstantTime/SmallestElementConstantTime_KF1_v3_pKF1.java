package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the minimum element in constant time.
 */
public class MinStack {
    private Stack<Integer> elements;
    private Stack<Integer> minimums;

    public MinStack() {
        elements = new Stack<>();
        minimums = new Stack<>();
    }

    public void push(int value) {
        if (elements.isEmpty()) {
            elements.push(value);
            minimums.push(value);
            return;
        }

        elements.push(value);
        if (value < minimums.peek()) {
            minimums.push(value);
        }
    }

    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elements.pop();
        if (removedValue == minimums.peek()) {
            minimums.pop();
        }
    }

    public Integer getMinimum() {
        if (minimums.isEmpty()) {
            return null;
        }
        return minimums.peek();
    }
}
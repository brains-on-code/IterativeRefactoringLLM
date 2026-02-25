package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GreatestElementConstantTime {
    private Stack<Integer> elements;
    private Stack<Integer> maxElements;

    public GreatestElementConstantTime() {
        elements = new Stack<>();
        maxElements = new Stack<>();
    }

    public void push(int value) {
        if (elements.isEmpty()) {
            elements.push(value);
            maxElements.push(value);
            return;
        }

        elements.push(value);
        if (value > maxElements.peek()) {
            maxElements.push(value);
        }
    }

    public void pop() {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = elements.pop();
        if (removedValue == maxElements.peek()) {
            maxElements.pop();
        }
    }

    public Integer getMaximumElement() {
        if (maxElements.isEmpty()) {
            return null;
        }
        return maxElements.peek();
    }
}
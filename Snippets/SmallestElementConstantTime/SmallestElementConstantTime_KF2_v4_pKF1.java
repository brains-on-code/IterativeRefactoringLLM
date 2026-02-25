package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class SmallestElementConstantTime {
    private final Stack<Integer> mainStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            minStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value < minStack.peek()) {
            minStack.push(value);
        }
    }

    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = mainStack.pop();
        if (removedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    public Integer getMinimumElement() {
        if (minStack.isEmpty()) {
            return null;
        }
        return minStack.peek();
    }
}
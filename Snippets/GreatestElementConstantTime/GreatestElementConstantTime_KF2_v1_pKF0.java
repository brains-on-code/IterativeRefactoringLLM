package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GreatestElementConstantTime {

    private final Stack<Integer> mainStack;
    private final Stack<Integer> maxStack;

    public GreatestElementConstantTime() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    public void push(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            maxStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value >= maxStack.peek()) {
            maxStack.push(value);
        }
    }

    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
    }

    public Integer getMaximumElement() {
        return maxStack.isEmpty() ? null : maxStack.peek();
    }
}
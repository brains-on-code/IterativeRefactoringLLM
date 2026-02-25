package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class SmallestElementConstantTime {
    private final Stack<Integer> dataStack;
    private final Stack<Integer> minimumStack;

    public SmallestElementConstantTime() {
        this.dataStack = new Stack<>();
        this.minimumStack = new Stack<>();
    }

    public void push(int element) {
        if (dataStack.isEmpty()) {
            dataStack.push(element);
            minimumStack.push(element);
            return;
        }

        dataStack.push(element);
        if (element < minimumStack.peek()) {
            minimumStack.push(element);
        }
    }

    public void pop() {
        if (dataStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedElement = dataStack.pop();
        if (removedElement == minimumStack.peek()) {
            minimumStack.pop();
        }
    }

    public Integer getMinimumElement() {
        if (minimumStack.isEmpty()) {
            return null;
        }
        return minimumStack.peek();
    }
}
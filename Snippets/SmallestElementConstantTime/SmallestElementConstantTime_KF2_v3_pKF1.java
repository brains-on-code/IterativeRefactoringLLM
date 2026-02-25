package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class SmallestElementConstantTime {
    private final Stack<Integer> dataStack;
    private final Stack<Integer> minStack;

    public SmallestElementConstantTime() {
        this.dataStack = new Stack<>();
        this.minStack = new Stack<>();
    }

    public void push(int element) {
        if (dataStack.isEmpty()) {
            dataStack.push(element);
            minStack.push(element);
            return;
        }

        dataStack.push(element);
        if (element < minStack.peek()) {
            minStack.push(element);
        }
    }

    public void pop() {
        if (dataStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedElement = dataStack.pop();
        if (removedElement == minStack.peek()) {
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
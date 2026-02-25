package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 */
public class MaxStack {
    private final Stack<Integer> elementStack;
    private final Stack<Integer> maxElementStack;

    public MaxStack() {
        elementStack = new Stack<>();
        maxElementStack = new Stack<>();
    }

    public void push(int element) {
        if (elementStack.isEmpty()) {
            elementStack.push(element);
            maxElementStack.push(element);
            return;
        }

        elementStack.push(element);
        if (element >= maxElementStack.peek()) {
            maxElementStack.push(element);
        }
    }

    public void pop() {
        if (elementStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedElement = elementStack.pop();
        if (removedElement == maxElementStack.peek()) {
            maxElementStack.pop();
        }
    }

    public Integer getMax() {
        if (maxElementStack.isEmpty()) {
            return null;
        }
        return maxElementStack.peek();
    }
}
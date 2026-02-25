package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

public class GreatestElementConstantTime {
    private Stack<Integer> valueStack;
    private Stack<Integer> maxValueStack;

    public GreatestElementConstantTime() {
        valueStack = new Stack<>();
        maxValueStack = new Stack<>();
    }

    public void push(int value) {
        if (valueStack.isEmpty()) {
            valueStack.push(value);
            maxValueStack.push(value);
            return;
        }

        valueStack.push(value);
        if (value > maxValueStack.peek()) {
            maxValueStack.push(value);
        }
    }

    public void pop() {
        if (valueStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removedValue = valueStack.pop();
        if (removedValue == maxValueStack.peek()) {
            maxValueStack.pop();
        }
    }

    public Integer getMaximumElement() {
        if (maxValueStack.isEmpty()) {
            return null;
        }
        return maxValueStack.peek();
    }
}
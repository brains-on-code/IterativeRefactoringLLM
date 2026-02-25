package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;


public class SmallestElementConstantTime {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;


    public SmallestElementConstantTime() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }


    public void push(int data) {
        if (mainStack.isEmpty()) {
            mainStack.push(data);
            minStack.push(data);
            return;
        }

        mainStack.push(data);
        if (data < minStack.peek()) {
            minStack.push(data);
        }
    }


    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int ele = mainStack.pop();
        if (ele == minStack.peek()) {
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

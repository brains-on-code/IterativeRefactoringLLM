package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;


public class GreatestElementConstantTime {
    private Stack<Integer> mainStack;
    private Stack<Integer> maxStack;


    public GreatestElementConstantTime() {
        mainStack = new Stack<>();
        maxStack = new Stack<>();
    }


    public void push(int data) {
        if (mainStack.isEmpty()) {
            mainStack.push(data);
            maxStack.push(data);
            return;
        }

        mainStack.push(data);
        if (data > maxStack.peek()) {
            maxStack.push(data);
        }
    }


    public void pop() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int ele = mainStack.pop();
        if (ele == maxStack.peek()) {
            maxStack.pop();
        }
    }


    public Integer getMaximumElement() {
        if (maxStack.isEmpty()) {
            return null;
        }
        return maxStack.peek();
    }
}

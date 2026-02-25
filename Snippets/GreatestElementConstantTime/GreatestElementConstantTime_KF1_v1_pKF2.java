package com.thealgorithms.stacks;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * A stack that supports retrieving the current maximum element in O(1) time.
 *
 * <p>Internally maintains:
 * <ul>
 *   <li>{@code mainStack}: stores all pushed elements</li>
 *   <li>{@code maxStack}: stores the maximum element at each stack depth</li>
 * </ul>
 */
public class Class1 {

    /** Stores all elements pushed onto this stack. */
    private final Stack<Integer> mainStack;

    /** Stores the maximum element corresponding to each depth of {@code mainStack}. */
    private final Stack<Integer> maxStack;

    /** Constructs an empty max-tracking stack. */
    public Class1() {
        this.mainStack = new Stack<>();
        this.maxStack = new Stack<>();
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param value the element to push
     */
    public void method1(int value) {
        if (mainStack.isEmpty()) {
            mainStack.push(value);
            maxStack.push(value);
            return;
        }

        mainStack.push(value);
        if (value > maxStack.peek()) {
            maxStack.push(value);
        }
    }

    /**
     * Pops the top element from the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    public void method2() {
        if (mainStack.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        int removed = mainStack.pop();
        if (removed == maxStack.peek()) {
            maxStack.pop();
        }
    }

    /**
     * Returns the current maximum element in the stack without removing it.
     *
     * @return the current maximum element, or {@code null} if the stack is empty
     */
    public Integer method3() {
        if (maxStack.isEmpty()) {
            return null;
        }
        return maxStack.peek();
    }
}
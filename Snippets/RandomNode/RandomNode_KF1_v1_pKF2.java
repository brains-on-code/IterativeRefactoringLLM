package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple example demonstrating:
 * - A singly linked list node (Node)
 * - A wrapper class (RandomNodeSelector) that stores node values in a list
 *   and returns a random node value.
 */
public class Class1 {

    /** Stores the values of the linked list nodes. */
    private final List<Integer> nodeValues;

    /** Number of nodes in the list. */
    private int size;

    /** Random number generator used to select a random node. */
    private static final Random RANDOM = new Random();

    /**
     * Singly linked list node.
     */
    static class Class2 {

        int value;
        Class2 next;

        Class2(int value) {
            this.value = value;
        }
    }

    /**
     * Constructs a Class1 instance from the head of a linked list.
     *
     * @param head the head node of the linked list
     */
    public Class1(Class2 head) {
        nodeValues = new ArrayList<>();
        Class2 current = head;

        while (current != null) {
            nodeValues.add(current.value);
            current = current.next;
            size++;
        }
    }

    /**
     * Returns a random node value from the list.
     *
     * @return a randomly selected node value
     */
    public int method1() {
        int randomIndex = RANDOM.nextInt(size);
        return nodeValues.get(randomIndex);
    }

    /**
     * Example usage.
     */
    public static void method2(String[] args) {
        Class2 head = new Class2(15);
        head.next = new Class2(25);
        head.next.next = new Class2(4);
        head.next.next.next = new Class2(1);
        head.next.next.next.next = new Class2(78);
        head.next.next.next.next.next = new Class2(63);

        Class1 selector = new Class1(head);
        int randomValue = selector.method1();
        System.out.println("Random Node : " + randomValue);
    }
}
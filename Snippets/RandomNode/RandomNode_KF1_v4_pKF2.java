package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides random access to values from a singly linked list by
 * copying node values into an internal list and selecting from it uniformly.
 */
public class RandomNodeSelector {

    private static final Random RANDOM = new Random();

    private final List<Integer> nodeValues;
    private final int size;

    /**
     * Singly linked list node.
     */
    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    /**
     * Constructs a selector by copying all node values from the given list.
     *
     * @param head the head node of the linked list (may be {@code null})
     */
    public RandomNodeSelector(Node head) {
        this.nodeValues = new ArrayList<>();
        int count = 0;

        for (Node current = head; current != null; current = current.next) {
            nodeValues.add(current.value);
            count++;
        }

        this.size = count;
    }

    /**
     * Returns a uniformly random value from the stored node values.
     *
     * @return a randomly selected node value
     * @throws IllegalStateException if the selector was constructed with an empty list
     */
    public int getRandomNodeValue() {
        if (size == 0) {
            throw new IllegalStateException("No nodes available for selection.");
        }
        return nodeValues.get(RANDOM.nextInt(size));
    }

    public static void main(String[] args) {
        Node head = new Node(15);
        head.next = new Node(25);
        head.next.next = new Node(4);
        head.next.next.next = new Node(1);
        head.next.next.next.next = new Node(78);
        head.next.next.next.next.next = new Node(63);

        RandomNodeSelector selector = new RandomNodeSelector(head);
        int randomValue = selector.getRandomNodeValue();
        System.out.println("Random Node : " + randomValue);
    }
}
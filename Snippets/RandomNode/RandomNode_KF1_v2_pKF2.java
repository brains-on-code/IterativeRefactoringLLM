package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrates:
 * - A singly linked list node (Node)
 * - A wrapper class (RandomNodeSelector) that stores node values in a list
 *   and returns a random node value.
 */
public class RandomNodeSelector {

    private final List<Integer> nodeValues;
    private int size;
    private static final Random RANDOM = new Random();

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
     * Constructs a RandomNodeSelector instance from the head of a linked list.
     *
     * @param head the head node of the linked list
     */
    public RandomNodeSelector(Node head) {
        nodeValues = new ArrayList<>();
        Node current = head;

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
    public int getRandomNodeValue() {
        int randomIndex = RANDOM.nextInt(size);
        return nodeValues.get(randomIndex);
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
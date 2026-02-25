package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNodeSelector {

    private static final Random RANDOM = new Random();

    private final List<Integer> nodeValues;

    static class Node {

        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public RandomNodeSelector(Node head) {
        this.nodeValues = new ArrayList<>();
        collectNodeValues(head);
    }

    private void collectNodeValues(Node head) {
        for (Node current = head; current != null; current = current.next) {
            nodeValues.add(current.value);
        }
    }

    public int getRandomValue() {
        if (nodeValues.isEmpty()) {
            throw new IllegalStateException("Cannot get random value from an empty list.");
        }
        int randomIndex = RANDOM.nextInt(nodeValues.size());
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
        int randomValue = selector.getRandomValue();
        System.out.println("Random Node Value: " + randomValue);
    }
}
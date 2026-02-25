package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNode {

    private static final Random RANDOM = new Random();

    private final List<Integer> nodeValues;

    static class ListNode {

        final int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    public RandomNode(ListNode head) {
        this.nodeValues = new ArrayList<>();
        populateNodeValues(head);
    }

    private void populateNodeValues(ListNode head) {
        for (ListNode current = head; current != null; current = current.next) {
            nodeValues.add(current.value);
        }
    }

    public int getRandom() {
        if (nodeValues.isEmpty()) {
            throw new IllegalStateException("Cannot get random value from an empty list.");
        }
        int randomIndex = RANDOM.nextInt(nodeValues.size());
        return nodeValues.get(randomIndex);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(15);
        head.next = new ListNode(25);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(1);
        head.next.next.next.next = new ListNode(78);
        head.next.next.next.next.next = new ListNode(63);

        RandomNode randomNode = new RandomNode(head);
        int randomValue = randomNode.getRandom();
        System.out.println("Random Node: " + randomValue);
    }
}
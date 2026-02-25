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
        collectNodeValues(head);
    }

    private void collectNodeValues(ListNode head) {
        ListNode current = head;
        while (current != null) {
            nodeValues.add(current.value);
            current = current.next;
        }
    }

    public int getRandom() {
        if (nodeValues.isEmpty()) {
            throw new IllegalStateException("Cannot get random value from an empty list.");
        }
        return nodeValues.get(RANDOM.nextInt(nodeValues.size()));
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
package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNode {

    private final List<Integer> nodeValues;
    private int nodeCount;
    private static final Random RANDOM = new Random();

    static class ListNode {

        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    public RandomNode(ListNode head) {
        nodeValues = new ArrayList<>();
        ListNode currentNode = head;

        while (currentNode != null) {
            nodeValues.add(currentNode.value);
            currentNode = currentNode.next;
            nodeCount++;
        }
    }

    public int getRandom() {
        int randomIndex = RANDOM.nextInt(nodeCount);
        return nodeValues.get(randomIndex);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(15);
        head.next = new ListNode(25);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(1);
        head.next.next.next.next = new ListNode(78);
        head.next.next.next.next.next = new ListNode(63);

        RandomNode randomNodeSelector = new RandomNode(head);
        int randomValue = randomNodeSelector.getRandom();
        System.out.println("Random Node: " + randomValue);
    }
}
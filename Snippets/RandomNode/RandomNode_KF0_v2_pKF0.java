package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility to select a random node's value from a singly linked list.
 *
 * <p>Algorithm:
 * <ol>
 *   <li>Traverse the linked list once and store all node values in a list.</li>
 *   <li>Use a random index to return a value from that list.</li>
 * </ol>
 *
 * <p>Time Complexity: O(n) for construction, O(1) per getRandom() call.
 * <br>Space Complexity: O(n) for storing node values.
 */
public class RandomNode {

    private static final Random RANDOM = new Random();

    private final List<Integer> values;

    static class ListNode {

        final int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    public RandomNode(ListNode head) {
        this.values = new ArrayList<>();
        populateValues(head);
    }

    private void populateValues(ListNode head) {
        for (ListNode current = head; current != null; current = current.next) {
            values.add(current.value);
        }
    }

    public int getRandom() {
        int index = RANDOM.nextInt(values.size());
        return values.get(index);
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
        System.out.println("Random Node : " + randomValue);
    }
}
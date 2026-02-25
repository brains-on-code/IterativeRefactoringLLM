package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility to return a uniformly random node value from a singly linked list.
 *
 * <p>Implementation details:
 * <ul>
 *   <li>On construction, traverses the list once and stores all node values in an {@link ArrayList}.</li>
 *   <li>{@link #getRandom()} uses {@link Random#nextInt(int)} to select a random index.</li>
 * </ul>
 *
 * <p>Complexity:
 * <ul>
 *   <li>Construction time: O(n), where n is the number of nodes.</li>
 *   <li>{@code getRandom} time: O(1).</li>
 *   <li>Space: O(n) for storing node values.</li>
 * </ul>
 */
public class RandomNode {

    private static final Random RANDOM = new Random();

    private final List<Integer> values = new ArrayList<>();
    private int size;

    static class ListNode {

        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    /**
     * Constructs a {@code RandomNode} by copying all values from the given linked list.
     *
     * @param head the head of the linked list (may be {@code null})
     */
    public RandomNode(ListNode head) {
        populateValues(head);
    }

    private void populateValues(ListNode head) {
        for (ListNode current = head; current != null; current = current.next) {
            values.add(current.value);
            size++;
        }
    }

    /**
     * Returns a random node value from the original linked list.
     *
     * @return a randomly selected node value
     * @throws IllegalStateException if the list is empty
     */
    public int getRandom() {
        if (size == 0) {
            throw new IllegalStateException("Cannot select a random value from an empty list.");
        }
        int index = RANDOM.nextInt(size);
        return values.get(index);
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
        System.out.println("Random Node : " + randomValue);
    }
}
package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility for selecting a random node value from a singly linked list.
 *
 * <p>The constructor traverses the list once and stores all node values in an
 * internal list. Subsequent calls to {@link #getRandom()} return a uniformly
 * random value from that list.</p>
 */
public class RandomNode {

    private static final Random RANDOM = new Random();

    private final List<Integer> values;
    private final int size;

    /** Singly linked list node. */
    static class ListNode {

        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    /**
     * Constructs a {@code RandomNode} by copying all node values from the given
     * list head into an internal list.
     *
     * @param head the head of the singly linked list; may be {@code null}
     */
    public RandomNode(ListNode head) {
        this.values = new ArrayList<>();
        int count = 0;

        for (ListNode current = head; current != null; current = current.next) {
            values.add(current.value);
            count++;
        }

        this.size = count;
    }

    /**
     * Returns a uniformly random value from the original list.
     *
     * @return a random node value
     * @throws IllegalStateException if the list is empty
     */
    public int getRandom() {
        if (size == 0) {
            throw new IllegalStateException("Cannot select a random value from an empty list.");
        }
        return values.get(RANDOM.nextInt(size));
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
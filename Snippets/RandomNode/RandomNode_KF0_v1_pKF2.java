package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility to return a uniformly random node value from a singly linked list.
 *
 * <p>Approach:
 * <ul>
 *   <li>Traverse the list once and store all node values in an ArrayList.</li>
 *   <li>Use {@link Random#nextInt(int)} to select a random index.</li>
 *   <li>Return the value at that index.</li>
 * </ul>
 *
 * <p>Time Complexity (construction): O(n) where n is the number of nodes.  
 * Time Complexity (getRandom): O(1).  
 * Space Complexity: O(n) for storing node values.
 */
public class RandomNode {

    private final List<Integer> values;
    private int size;
    private static final Random RAND = new Random();

    static class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Constructs a RandomNode helper from the head of a singly linked list.
     *
     * @param head the head of the linked list
     */
    public RandomNode(ListNode head) {
        values = new ArrayList<>();
        ListNode current = head;

        while (current != null) {
            values.add(current.val);
            current = current.next;
            size++;
        }
    }

    /**
     * Returns a random node value from the original linked list.
     *
     * @return a randomly selected node value
     */
    public int getRandom() {
        int index = RAND.nextInt(size);
        return values.get(index);
    }

    /**
     * Example usage.
     */
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
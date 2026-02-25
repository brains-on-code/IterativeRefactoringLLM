package com.thealgorithms.datastructures.lists;

/**
 * Utility class for linked list operations.
 */
public final class LinkedListUtils {

    private LinkedListUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Node of a singly linked list.
     */
    static final class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Connects the node at position {@code fromIndex} to the node at
     * position {@code toIndex}, creating a cycle if both positions
     * are valid.
     *
     * Positions are 1-based.
     */
    static void connectNodes(ListNode head, int fromIndex, int toIndex) {
        if (fromIndex <= 0 || toIndex <= 0 || head == null) {
            return;
        }

        ListNode fromNode = head;
        ListNode toNode = head;

        int currentFromIndex = 1;
        int currentToIndex = 1;

        while (currentFromIndex < fromIndex && fromNode != null) {
            fromNode = fromNode.next;
            currentFromIndex++;
        }

        while (currentToIndex < toIndex && toNode != null) {
            toNode = toNode.next;
            currentToIndex++;
        }

        if (fromNode != null && toNode != null) {
            toNode.next = fromNode;
        }
    }

    /**
     * Detects whether the linked list starting at {@code head} contains a cycle.
     *
     * Uses Floyd's Tortoise and Hare algorithm.
     */
    static boolean hasCycle(ListNode head) {
        ListNode slowPointer = head;
        ListNode fastPointer = head;

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
            if (slowPointer == fastPointer) {
                return true;
            }
        }
        return false;
    }
}
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
     * Connects the node at position {@code fromPosition} to the node at
     * position {@code toPosition}, creating a cycle if both positions
     * are valid.
     *
     * Positions are 1-based.
     */
    static void connectNodes(ListNode head, int fromPosition, int toPosition) {
        if (fromPosition <= 0 || toPosition <= 0 || head == null) {
            return;
        }

        ListNode fromNode = head;
        ListNode toNode = head;

        int currentFromPosition = 1;
        int currentToPosition = 1;

        while (currentFromPosition < fromPosition && fromNode != null) {
            fromNode = fromNode.next;
            currentFromPosition++;
        }

        while (currentToPosition < toPosition && toNode != null) {
            toNode = toNode.next;
            currentToPosition++;
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
        ListNode slowNode = head;
        ListNode fastNode = head;

        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
            if (slowNode == fastNode) {
                return true;
            }
        }
        return false;
    }
}
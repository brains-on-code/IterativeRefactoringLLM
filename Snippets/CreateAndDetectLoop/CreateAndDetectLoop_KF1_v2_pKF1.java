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
    static final class Node {
        int value;
        Node next;

        Node(int value) {
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
    static void connectNodes(Node head, int fromPosition, int toPosition) {
        if (fromPosition == 0 || toPosition == 0) {
            return;
        }

        Node fromNode = head;
        Node toNode = head;

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
    static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
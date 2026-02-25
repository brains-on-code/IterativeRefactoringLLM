package com.thealgorithms.datastructures.lists;

/**
 * Utility class for operations on a simple singly linked list.
 */
public final class LinkedListUtils {

    private LinkedListUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Node of a singly linked list.
     */
    static final class Node {
        final int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    /**
     * Connects the node at position {@code fromIndex} to the node at position
     * {@code toIndex} in the list starting from {@code head}, effectively
     * creating a cycle if both positions are valid.
     *
     * Positions are 1-based. If either index is 0 or out of bounds, the method
     * does nothing.
     *
     * @param head      the head of the list
     * @param fromIndex the 1-based index of the node whose {@code next} will be set
     * @param toIndex   the 1-based index of the target node
     */
    static void connectNodes(Node head, int fromIndex, int toIndex) {
        if (head == null || fromIndex <= 0 || toIndex <= 0) {
            return;
        }

        Node fromNode = getNodeAtIndex(head, fromIndex);
        Node toNode = getNodeAtIndex(head, toIndex);

        if (fromNode != null && toNode != null) {
            fromNode.next = toNode;
        }
    }

    private static Node getNodeAtIndex(Node head, int targetIndex) {
        int currentIndex = 1;
        Node current = head;

        while (current != null && currentIndex < targetIndex) {
            current = current.next;
            currentIndex++;
        }

        return current;
    }

    /**
     * Detects whether the list starting at {@code head} contains a cycle using
     * Floyd's Tortoise and Hare algorithm.
     *
     * @param head the head of the list
     * @return {@code true} if a cycle exists, {@code false} otherwise
     */
    static boolean hasCycle(Node head) {
        if (head == null) {
            return false;
        }

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
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
     * Connects the node at position {@code positionToConnectFrom} to the node at
     * position {@code positionToConnectTo}, creating a cycle if both positions
     * are valid.
     *
     * Positions are 1-based.
     */
    static void connectNodes(Node head, int positionToConnectFrom, int positionToConnectTo) {
        if (positionToConnectFrom == 0 || positionToConnectTo == 0) {
            return;
        }

        Node fromNode = head;
        Node toNode = head;

        int currentFromPosition = 1;
        int currentToPosition = 1;

        while (currentFromPosition < positionToConnectFrom && fromNode != null) {
            fromNode = fromNode.next;
            currentFromPosition++;
        }

        while (currentToPosition < positionToConnectTo && toNode != null) {
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
        Node slowPointer = head;
        Node fastPointer = head;

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
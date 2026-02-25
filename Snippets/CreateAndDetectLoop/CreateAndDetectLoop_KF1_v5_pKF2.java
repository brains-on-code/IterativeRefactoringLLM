package com.thealgorithms.datastructures.lists;

/**
 * Utility methods for operations on a simple singly linked list.
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
        }
    }

    /**
     * Connects two nodes in the same list using 1-based positions.
     *
     * <p>The {@code next} reference of the node at {@code toPosition} will be set
     * to point to the node at {@code fromPosition}.</p>
     *
     * <ul>
     *   <li>If {@code fromPosition} is before {@code toPosition}, this creates a cycle.</li>
     *   <li>If either position is non-positive or out of bounds, the list is not modified.</li>
     * </ul>
     *
     * @param head         head of the list
     * @param fromPosition 1-based index of the node to connect to
     * @param toPosition   1-based index of the node whose {@code next} will be updated
     */
    static void connectNodes(Node head, int fromPosition, int toPosition) {
        if (head == null || fromPosition <= 0 || toPosition <= 0) {
            return;
        }

        Node fromNode = getNodeAtPosition(head, fromPosition);
        Node toNode = getNodeAtPosition(head, toPosition);

        if (fromNode == null || toNode == null) {
            return;
        }

        toNode.next = fromNode;
    }

    /**
     * Returns the node at the given 1-based position in the list.
     *
     * @param head     head of the list
     * @param position 1-based index of the desired node
     * @return node at the given position, or {@code null} if the position is out of bounds
     */
    private static Node getNodeAtPosition(Node head, int position) {
        Node current = head;
        int currentPosition = 1;

        while (current != null && currentPosition < position) {
            current = current.next;
            currentPosition++;
        }

        return current;
    }

    /**
     * Detects whether the list starting at {@code head} contains a cycle.
     *
     * <p>Uses Floyd's Tortoise and Hare algorithm.</p>
     *
     * @param head head of the list
     * @return {@code true} if a cycle exists, {@code false} otherwise
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
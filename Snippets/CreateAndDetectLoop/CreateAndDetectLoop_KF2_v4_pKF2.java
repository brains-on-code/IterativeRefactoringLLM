package com.thealgorithms.datastructures.lists;

public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    static final class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /**
     * Creates a loop in the linked list by setting the {@code next} reference of the node
     * at {@code toPosition} to point to the node at {@code fromPosition}.
     *
     * Positions are 1-based. If any position is invalid or the head is {@code null},
     * the list is left unchanged.
     *
     * @param head         the head of the linked list
     * @param fromPosition 1-based index of the node that will become the loop target
     * @param toPosition   1-based index of the node whose {@code next} will be reassigned
     */
    static void createLoop(Node head, int fromPosition, int toPosition) {
        if (head == null || fromPosition <= 0 || toPosition <= 0) {
            return;
        }

        Node fromNode = getNodeAtPosition(head, fromPosition);
        Node toNode = getNodeAtPosition(head, toPosition);

        if (fromNode != null && toNode != null) {
            toNode.next = fromNode;
        }
    }

    /**
     * Returns the node at the given 1-based position in the list.
     *
     * @param head     the head of the linked list
     * @param position 1-based index of the desired node
     * @return the node at {@code position}, or {@code null} if the position is out of bounds
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
     * Detects whether the linked list contains a loop using Floyd's cycle-finding algorithm
     * (also known as the tortoise and hare algorithm).
     *
     * @param head the head of the linked list
     * @return {@code true} if a loop is detected, {@code false} otherwise
     */
    static boolean detectLoop(Node head) {
        if (head == null) {
            return false;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;           // moves one step
            fast = fast.next.next;      // moves two steps

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
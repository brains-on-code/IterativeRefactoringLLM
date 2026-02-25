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
     * Creates a loop in the linked list by connecting the node at {@code toPosition}
     * to the node at {@code fromPosition}.
     *
     * @param head         the head of the linked list
     * @param fromPosition the position of the node to link to (1-based index)
     * @param toPosition   the position of the node whose next will be modified (1-based index)
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
     * Returns the node at the given 1-based position in the list, or {@code null}
     * if the position is out of bounds.
     *
     * @param head     the head of the linked list
     * @param position the 1-based position of the desired node
     * @return the node at the specified position, or {@code null} if not found
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
     * Detects whether the linked list contains a loop using Floyd's cycle-finding algorithm.
     *
     * @param head the head of the linked list
     * @return {@code true} if a loop is detected, {@code false} otherwise
     */
    static boolean detectLoop(Node head) {
        if (head == null) {
            return false;
        }

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
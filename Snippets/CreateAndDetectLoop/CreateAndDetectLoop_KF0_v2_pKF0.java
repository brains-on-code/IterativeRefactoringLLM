package com.thealgorithms.datastructures.lists;

/**
 * Utility methods for creating and detecting loops (cycles) in a singly linked list.
 */
public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Represents a node in the linked list.
     */
    static final class Node {
        final int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /**
     * Creates a loop in a linked list by connecting the next pointer of the node
     * at {@code position2} to the node at {@code position1}. Positions are 1-based.
     * If either position is invalid, no loop is created.
     *
     * @param head      the head node of the linked list
     * @param position1 the position where the loop should end (destination)
     * @param position2 the position where the loop should start (source)
     */
    static void createLoop(Node head, int position1, int position2) {
        if (head == null || position1 <= 0 || position2 <= 0) {
            return;
        }

        Node loopEndNode = getNodeAtPosition(head, position1);
        Node loopStartNode = getNodeAtPosition(head, position2);

        if (loopEndNode == null || loopStartNode == null) {
            return;
        }

        loopStartNode.next = loopEndNode;
    }

    /**
     * Returns the node at the given 1-based position in the list, or {@code null}
     * if the position is out of bounds.
     *
     * @param head     the head node of the linked list
     * @param position the 1-based position of the desired node
     * @return the node at the specified position, or {@code null} if not found
     */
    private static Node getNodeAtPosition(Node head, int position) {
        Node current = head;
        int index = 1;

        while (current != null && index < position) {
            current = current.next;
            index++;
        }

        return current;
    }

    /**
     * Detects the presence of a loop in the linked list using Floyd's cycle-finding
     * algorithm ("tortoise and hare").
     *
     * @param head the head node of the linked list
     * @return {@code true} if a loop is detected, {@code false} otherwise
     * @see <a href="https://en.wikipedia.org/wiki/Cycle_detection#Floyd's_tortoise_and_hare">
     *     Floyd's Cycle Detection Algorithm</a>
     */
    static boolean detectLoop(Node head) {
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
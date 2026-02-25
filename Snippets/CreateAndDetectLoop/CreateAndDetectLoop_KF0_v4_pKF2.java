package com.thealgorithms.datastructures.lists;

/**
 * Utility class for creating and detecting loops in a singly linked list.
 */
public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Simple singly linked list node.
     */
    static final class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /**
     * Creates a loop in the linked list by connecting the node at
     * {@code loopStartPosition} to the node at {@code loopEndPosition}.
     *
     * Positions are 1-based. If any position is invalid or the head is null,
     * the method does nothing.
     *
     * @param head               head of the linked list
     * @param loopEndPosition    1-based position of the node that will become the loop end
     * @param loopStartPosition  1-based position of the node whose {@code next} will point to the loop end
     */
    static void createLoop(Node head, int loopEndPosition, int loopStartPosition) {
        if (head == null || loopEndPosition <= 0 || loopStartPosition <= 0) {
            return;
        }

        Node loopEndNode = getNodeAtPosition(head, loopEndPosition);
        Node loopStartNode = getNodeAtPosition(head, loopStartPosition);

        if (loopEndNode != null && loopStartNode != null) {
            loopStartNode.next = loopEndNode;
        }
    }

    /**
     * Returns the node at the given 1-based position in the list, or {@code null}
     * if the position is out of bounds.
     *
     * @param head     head of the linked list
     * @param position 1-based position of the desired node
     * @return node at the given position, or {@code null} if not found
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
     * Detects whether the linked list contains a loop using Floyd's
     * cycle-finding algorithm (tortoise and hare).
     *
     * @param head head of the linked list
     * @return {@code true} if a loop is detected, {@code false} otherwise
     */
    static boolean detectLoop(Node head) {
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
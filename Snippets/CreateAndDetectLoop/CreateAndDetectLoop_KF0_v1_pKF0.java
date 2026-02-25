package com.thealgorithms.datastructures.lists;

/**
 * CreateAndDetectLoop provides utility methods for creating and detecting loops
 * (cycles) in a singly linked list. Loops in a linked list are created by
 * connecting the "next" pointer of one node to a previous node in the list,
 * forming a cycle.
 */
public final class CreateAndDetectLoop {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Node represents an individual element in the linked list, containing
     * data and a reference to the next node.
     */
    static final class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Creates a loop in a linked list by connecting the next pointer of a node
     * at a specified starting position (position2) to another node at a specified
     * destination position (position1). If either position is invalid, no loop
     * will be created.
     *
     * @param head      the head node of the linked list
     * @param position1 the position in the list where the loop should end
     * @param position2 the position in the list where the loop should start
     */
    static void createLoop(Node head, int position1, int position2) {
        if (head == null || position1 <= 0 || position2 <= 0) {
            return;
        }

        Node loopEndNode = getNodeAtPosition(head, position1);
        Node loopStartNode = getNodeAtPosition(head, position2);

        if (loopEndNode != null && loopStartNode != null) {
            loopStartNode.next = loopEndNode;
        }
    }

    /**
     * Returns the node at the given 1-based position in the list, or null if the
     * position is out of bounds.
     *
     * @param head     the head node of the linked list
     * @param position the 1-based position of the desired node
     * @return the node at the specified position, or null if not found
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
     * Detects the presence of a loop in the linked list using Floyd's cycle-finding
     * algorithm, also known as the "tortoise and hare" method.
     *
     * @param head the head node of the linked list
     * @return true if a loop is detected, false otherwise
     * @see <a href="https://en.wikipedia.org/wiki/Cycle_detection#Floyd's_tortoise_and_hare">
     *     Floyd's Cycle Detection Algorithm</a>
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
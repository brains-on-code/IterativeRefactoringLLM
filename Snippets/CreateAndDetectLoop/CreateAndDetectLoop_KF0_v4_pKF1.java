package com.thealgorithms.datastructures.lists;

/**
 * LoopUtils provides utility methods for creating and detecting loops
 * (cycles) in a singly linked list. Loops in a linked list are created by
 * connecting the "next" pointer of one node to a previous node in the list,
 * forming a cycle.
 */
public final class LoopUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private LoopUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * ListNode represents an individual element in the linked list, containing
     * data and a reference to the next node.
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
     * Creates a loop in a linked list by connecting the next pointer of a node
     * at a specified starting position (loopStartPosition) to another node at a specified
     * destination position (loopEndPosition). If either position is invalid, no loop
     * will be created.
     *
     * @param head the head node of the linked list
     * @param loopEndPosition the position in the list where the loop should end
     * @param loopStartPosition the position in the list where the loop should start
     */
    static void createLoop(ListNode head, int loopEndPosition, int loopStartPosition) {
        if (loopEndPosition <= 0 || loopStartPosition <= 0 || head == null) {
            return;
        }

        ListNode endNode = head;
        ListNode startNode = head;

        int currentEndPosition = 1;
        int currentStartPosition = 1;

        // Traverse to the node at loopEndPosition
        while (currentEndPosition < loopEndPosition && endNode != null) {
            endNode = endNode.next;
            currentEndPosition++;
        }

        // Traverse to the node at loopStartPosition
        while (currentStartPosition < loopStartPosition && startNode != null) {
            startNode = startNode.next;
            currentStartPosition++;
        }

        // If both nodes are valid, create the loop
        if (endNode != null && startNode != null) {
            startNode.next = endNode;
        }
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
    static boolean hasLoop(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slowPointer = head;
        ListNode fastPointer = head;

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
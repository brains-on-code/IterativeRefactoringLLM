package com.thealgorithms.datastructures.lists;

/**
 * Utility class for operations on a simple singly linked list.
 */
public final class Class1 {

    private Class1() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Node of a singly linked list.
     */
    static final class Class2 {
        int value;
        Class2 next;

        Class2(int value) {
            this.value = value;
            this.next = null;
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
    static void method1(Class2 head, int fromIndex, int toIndex) {
        if (fromIndex == 0 || toIndex == 0 || head == null) {
            return;
        }

        Class2 fromNode = head;
        Class2 toNode = head;

        int currentFromIndex = 1;
        int currentToIndex = 1;

        while (currentFromIndex < fromIndex && fromNode != null) {
            fromNode = fromNode.next;
            currentFromIndex++;
        }

        while (currentToIndex < toIndex && toNode != null) {
            toNode = toNode.next;
            currentToIndex++;
        }

        if (fromNode != null && toNode != null) {
            fromNode.next = toNode;
        }
    }

    /**
     * Detects whether the list starting at {@code head} contains a cycle using
     * Floyd's Tortoise and Hare algorithm.
     *
     * @param head the head of the list
     * @return {@code true} if a cycle exists, {@code false} otherwise
     */
    static boolean method2(Class2 head) {
        if (head == null) {
            return false;
        }

        Class2 slow = head;
        Class2 fast = head;

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
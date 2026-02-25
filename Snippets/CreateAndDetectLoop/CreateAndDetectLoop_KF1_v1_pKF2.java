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
     * Connects the node at position {@code posTo} to the node at position {@code posFrom}
     * (1-based indices) in the same list, effectively creating a cycle if {@code posFrom}
     * is before {@code posTo}.
     *
     * If either position is 0 or out of bounds, the list is left unchanged.
     *
     * @param head   head of the list
     * @param posFrom 1-based index of the node to connect to
     * @param posTo   1-based index of the node whose {@code next} will be updated
     */
    static void method1(Class2 head, int posFrom, int posTo) {
        if (posFrom == 0 || posTo == 0) {
            return;
        }

        Class2 fromNode = head;
        Class2 toNode = head;

        int currentFromPos = 1;
        int currentToPos = 1;

        while (currentFromPos < posFrom && fromNode != null) {
            fromNode = fromNode.next;
            currentFromPos++;
        }

        while (currentToPos < posTo && toNode != null) {
            toNode = toNode.next;
            currentToPos++;
        }

        if (fromNode != null && toNode != null) {
            toNode.next = fromNode;
        }
    }

    /**
     * Detects whether the list starting at {@code head} contains a cycle
     * using Floyd's Tortoise and Hare algorithm.
     *
     * @param head head of the list
     * @return {@code true} if a cycle exists, {@code false} otherwise
     */
    static boolean method2(Class2 head) {
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
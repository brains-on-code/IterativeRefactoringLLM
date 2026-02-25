package com.thealgorithms.misc;

import java.util.Stack;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given iterable represents a palindrome sequence.
     *
     * @param iterable the iterable to check
     * @return {@code true} if the sequence is a palindrome, {@code false} otherwise
     */
    public static boolean isIterablePalindrome(final Iterable<?> iterable) {
        Stack<Object> stack = new Stack<>();

        for (final Object element : iterable) {
            stack.push(element);
        }

        for (final Object element : iterable) {
            if (element != stack.pop()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the given singly linked list is a palindrome.
     *
     * @param head the head node of the linked list
     * @return {@code true} if the list is a palindrome, {@code false} otherwise
     */
    public static boolean isLinkedListPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node middle = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(middle);

        return areHalvesEqual(head, reversedSecondHalfHead);
    }

    /**
     * Finds the middle node of a singly linked list using the slow/fast pointer technique.
     *
     * @param head the head node of the list
     * @return the middle node
     */
    private static Node findMiddle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Reverses a singly linked list.
     *
     * @param head the head node of the list to reverse
     * @return the new head of the reversed list
     */
    private static Node reverseList(Node head) {
        Node previous = null;
        Node current = head;

        while (current != null) {
            Node nextNode = current.next;
            current.next = previous;
            previous = current;
            current = nextNode;
        }

        return previous;
    }

    /**
     * Compares two linked list halves for equality.
     *
     * @param left  the head of the first half
     * @param right the head of the second (reversed) half
     * @return {@code true} if both halves are equal, {@code false} otherwise
     */
    private static boolean areHalvesEqual(Node left, Node right) {
        Node leftPointer = left;
        Node rightPointer = right;

        while (leftPointer != null && rightPointer != null) {
            if (leftPointer.value != rightPointer.value) {
                return false;
            }
            leftPointer = leftPointer.next;
            rightPointer = rightPointer.next;
        }

        return true;
    }

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
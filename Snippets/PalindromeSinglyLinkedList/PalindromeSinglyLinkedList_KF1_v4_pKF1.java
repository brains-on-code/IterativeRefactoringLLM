package com.thealgorithms.misc;

import java.util.Stack;

/**
 * Utility class for palindrome checks.
 */
public final class PalindromeUtils {

    private PalindromeUtils() {
    }

    /**
     * Checks whether the given iterable reads the same forwards and backwards.
     *
     * @param iterable the iterable to check
     * @return true if the iterable is a palindrome, false otherwise
     */
    public static boolean isIterablePalindrome(final Iterable<?> iterable) {
        Stack<Object> elements = new Stack<>();

        for (final Object element : iterable) {
            elements.push(element);
        }

        for (final Object element : iterable) {
            if (element != elements.pop()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the given singly linked list is a palindrome.
     *
     * @param head the head of the linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isLinkedListPalindrome(LinkedListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        LinkedListNode slowPointer = head;
        LinkedListNode fastPointer = head;

        // Find middle of the list
        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        // Reverse second half
        LinkedListNode previous = null;
        LinkedListNode current = slowPointer;
        while (current != null) {
            LinkedListNode next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        // Compare first and second halves
        LinkedListNode firstHalfCurrent = head;
        LinkedListNode secondHalfCurrent = previous;
        while (firstHalfCurrent != null && secondHalfCurrent != null) {
            if (firstHalfCurrent.value != secondHalfCurrent.value) {
                return false;
            }
            firstHalfCurrent = firstHalfCurrent.next;
            secondHalfCurrent = secondHalfCurrent.next;
        }

        return true;
    }

    static class LinkedListNode {
        int value;
        LinkedListNode next;

        LinkedListNode(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
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
     * @param head the head of the linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isLinkedListPalindrome(LinkedListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        LinkedListNode slow = head;
        LinkedListNode fast = head;

        // Find middle of the list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half
        LinkedListNode prev = null;
        LinkedListNode current = slow;
        while (current != null) {
            LinkedListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        // Compare first and second halves
        LinkedListNode firstHalfPointer = head;
        LinkedListNode secondHalfPointer = prev;
        while (firstHalfPointer != null && secondHalfPointer != null) {
            if (firstHalfPointer.value != secondHalfPointer.value) {
                return false;
            }
            firstHalfPointer = firstHalfPointer.next;
            secondHalfPointer = secondHalfPointer.next;
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
package com.thealgorithms.misc;

import java.util.Stack;

/**
 * Utility class for palindrome checks.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if the given iterable is a palindrome by comparing it to its reverse.
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
            if (!element.equals(stack.pop())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given singly linked list is a palindrome.
     *
     * @param head the head of the linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isLinkedListPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;

        // Find middle of the list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half
        Node prev = null;
        Node current = slow;
        while (current != null) {
            Node nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        // Compare first and second halves
        Node firstHalf = head;
        Node secondHalf = prev;
        while (firstHalf != null && secondHalf != null) {
            if (firstHalf.value != secondHalf.value) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
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
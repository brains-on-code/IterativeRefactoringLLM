package com.thealgorithms.misc;

import java.util.Stack;

/**
 * Utility class providing palindrome checks for sequences and linked lists.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given iterable represents a palindrome.
     *
     * @param iterable the iterable to check
     * @return {@code true} if the iterable is a palindrome, {@code false} otherwise
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

        // Find middle of the list using slow/fast pointers
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Reverse second half of the list
        Node prev = null;
        Node current = slow;
        while (current != null) {
            Node nextTmp = current.next;
            current.next = prev;
            prev = current;
            current = nextTmp;
        }

        // Compare first half and reversed second half
        Node left = head;
        Node right = prev;
        while (left != null && right != null) {
            if (left.value != right.value) {
                return false;
            }
            left = left.next;
            right = right.next;
        }

        return true;
    }

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
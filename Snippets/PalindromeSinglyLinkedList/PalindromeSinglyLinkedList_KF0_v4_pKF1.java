package com.thealgorithms.misc;

import java.util.Stack;

/**
 * A simple way of knowing if a singly linked list is palindrome is to push all
 * the values into a Stack and then compare the list to popped values from the
 * Stack.
 *
 * See more:
 * https://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 */
public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
    }

    public static boolean isPalindrome(final Iterable<?> iterable) {
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

    // Optimised approach with O(n) time complexity and O(1) space complexity
    public static boolean isPalindromeOptimised(Node head) {
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

        Node middle = slow;

        // Reverse second half of the list
        Node prev = null;
        Node current = middle;
        Node next;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        Node left = head;
        Node right = prev;

        // Compare first half and reversed second half
        while (left != null && right != null) {
            if (left.value != right.value) {
                return false;
            }
            right = right.next;
            left = left.next;
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
package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given iterable represents a palindrome sequence.
     *
     * @param iterable an iterable collection of elements
     * @return true if the sequence is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final Iterable<?> iterable) {
        Stack<Object> stack = new Stack<>();

        for (Object element : iterable) {
            stack.push(element);
        }

        for (Object element : iterable) {
            if (!element.equals(stack.pop())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a singly linked list is a palindrome using O(1) extra space.
     *
     * @param head head node of the singly linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node middle = findMiddle(head);
        Node secondHalfReversed = reverseList(middle);

        return areListsEqual(head, secondHalfReversed);
    }

    /**
     * Finds the middle node of a singly linked list using the fast/slow pointer technique.
     *
     * @param head head node of the list
     * @return middle node of the list
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
     * @param head head node of the list to reverse
     * @return new head node of the reversed list
     */
    private static Node reverseList(Node head) {
        Node previous = null;
        Node current = head;

        while (current != null) {
            Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        return previous;
    }

    /**
     * Compares two singly linked lists for equality of values and structure.
     *
     * @param first  head of the first list
     * @param second head of the second list
     * @return true if both lists are equal, false otherwise
     */
    private static boolean areListsEqual(Node first, Node second) {
        Node left = first;
        Node right = second;

        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }

        return left == null && right == null;
    }

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }
}
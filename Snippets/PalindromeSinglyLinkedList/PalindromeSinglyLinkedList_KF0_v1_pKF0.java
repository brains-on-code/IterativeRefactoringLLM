package com.thealgorithms.misc;

import java.util.Stack;

/**
 * Utility class for checking if a singly linked list is a palindrome.
 *
 * A simple way of knowing if a singly linked list is palindrome is to push all
 * the values into a Stack and then compare the list to popped values from the
 * Stack.
 *
 * See more:
 * https://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 */
public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given iterable represents a palindrome sequence.
     *
     * @param linkedList the iterable to check
     * @return true if the sequence is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final Iterable<?> linkedList) {
        Stack<Object> stack = new Stack<>();

        for (Object value : linkedList) {
            stack.push(value);
        }

        for (Object value : linkedList) {
            Object top = stack.pop();
            if (!value.equals(top)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Optimized approach with O(n) time complexity and O(1) space complexity
     * to check if a singly linked list is a palindrome.
     *
     * @param head the head node of the linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node midNode = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(midNode);

        Node left = head;
        Node right = reversedSecondHalfHead;

        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }

        return true;
    }

    private static Node findMiddle(Node head) {
        Node slow = head;
        Node fast = head;

        // When fast reaches the end, slow will be at the middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

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

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }
}
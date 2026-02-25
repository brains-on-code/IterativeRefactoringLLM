package com.thealgorithms.misc;

import java.util.Stack;

/**
 * Utility class for checking whether a singly linked list is a palindrome.
 *
 * <p>Naive approach:
 * <ul>
 *   <li>Time complexity: O(n)</li>
 *   <li>Space complexity: O(n)</li>
 * </ul>
 *
 * <p>Optimized approach:
 * <ul>
 *   <li>Time complexity: O(n)</li>
 *   <li>Space complexity: O(1)</li>
 * </ul>
 *
 * <p>Reference:
 * https://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 */
public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Prevent instantiation
    }

    /**
     * Checks if the given iterable sequence is a palindrome using a stack.
     *
     * @param linkedList the iterable sequence to check
     * @return {@code true} if the sequence is a palindrome, {@code false} otherwise
     */
    public static boolean isPalindrome(final Iterable<?> linkedList) {
        Stack<Object> stack = new Stack<>();

        for (Object value : linkedList) {
            stack.push(value);
        }

        for (Object value : linkedList) {
            if (!value.equals(stack.pop())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a singly linked list is a palindrome using O(1) extra space.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>Find the middle of the list using slow/fast pointers.</li>
     *   <li>Reverse the second half of the list.</li>
     *   <li>Compare the first half with the reversed second half.</li>
     * </ol>
     *
     * @param head the head node of the singly linked list
     * @return {@code true} if the list is a palindrome, {@code false} otherwise
     */
    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        // Step 1: Find the middle of the list
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node midNode = slow;

        // Step 2: Reverse the second half of the list starting from midNode
        Node prevNode = null;
        Node currNode = midNode;
        while (currNode != null) {
            Node nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }

        // Step 3: Compare the first half and the reversed second half
        Node left = head;
        Node right = prevNode;
        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }

        return true;
    }

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
            this.next = null;
        }
    }
}
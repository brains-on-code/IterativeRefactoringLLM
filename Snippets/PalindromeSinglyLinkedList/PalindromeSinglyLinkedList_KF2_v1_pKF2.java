package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given iterable represents a palindrome sequence.
     *
     * @param linkedList an iterable collection of elements
     * @return true if the sequence is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final Iterable<?> linkedList) {
        Stack<Object> stack = new Stack<>();

        // Push all elements onto the stack
        for (Object element : linkedList) {
            stack.push(element);
        }

        // Compare elements from the iterable with elements popped from the stack
        for (Object element : linkedList) {
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

        // Find the middle of the list using slow and fast pointers
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node midNode = slow;

        // Reverse the second half of the list starting from midNode
        Node prevNode = null;
        Node currNode = midNode;
        while (currNode != null) {
            Node nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }

        // Compare the first half and the reversed second half
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
        }
    }
}
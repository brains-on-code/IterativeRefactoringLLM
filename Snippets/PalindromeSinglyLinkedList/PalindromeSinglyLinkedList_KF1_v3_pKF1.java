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
        Stack<Object> elementStack = new Stack<>();

        for (final Object element : iterable) {
            elementStack.push(element);
        }

        for (final Object element : iterable) {
            if (element != elementStack.pop()) {
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

        LinkedListNode middleNode = head;
        LinkedListNode fastTraversalNode = head;

        // Find middle of the list
        while (fastTraversalNode != null && fastTraversalNode.next != null) {
            middleNode = middleNode.next;
            fastTraversalNode = fastTraversalNode.next.next;
        }

        // Reverse second half
        LinkedListNode previousNode = null;
        LinkedListNode currentNode = middleNode;
        while (currentNode != null) {
            LinkedListNode nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        // Compare first and second halves
        LinkedListNode firstHalfNode = head;
        LinkedListNode secondHalfNode = previousNode;
        while (firstHalfNode != null && secondHalfNode != null) {
            if (firstHalfNode.value != secondHalfNode.value) {
                return false;
            }
            firstHalfNode = firstHalfNode.next;
            secondHalfNode = secondHalfNode.next;
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
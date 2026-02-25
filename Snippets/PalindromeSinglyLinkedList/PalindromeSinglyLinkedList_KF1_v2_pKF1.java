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
        Stack<Object> elementsStack = new Stack<>();

        for (final Object element : iterable) {
            elementsStack.push(element);
        }

        for (final Object element : iterable) {
            if (element != elementsStack.pop()) {
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

        LinkedListNode middlePointer = head;
        LinkedListNode traversalPointer = head;

        // Find middle of the list
        while (traversalPointer != null && traversalPointer.next != null) {
            middlePointer = middlePointer.next;
            traversalPointer = traversalPointer.next.next;
        }

        // Reverse second half
        LinkedListNode previousNode = null;
        LinkedListNode currentNode = middlePointer;
        while (currentNode != null) {
            LinkedListNode nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        // Compare first and second halves
        LinkedListNode firstHalfPointer = head;
        LinkedListNode secondHalfPointer = previousNode;
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
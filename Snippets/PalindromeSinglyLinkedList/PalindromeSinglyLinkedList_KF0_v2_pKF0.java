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
     * @param iterable the iterable to check
     * @return true if the sequence is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final Iterable<?> iterable) {
        Stack<Object> stack = new Stack<>();

        for (Object value : iterable) {
            stack.push(value);
        }

        for (Object value : iterable) {
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

        Node middleNode = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(middleNode);

        Node leftPointer = head;
        Node rightPointer = reversedSecondHalfHead;

        while (leftPointer != null && rightPointer != null) {
            if (leftPointer.value != rightPointer.value) {
                return false;
            }
            leftPointer = leftPointer.next;
            rightPointer = rightPointer.next;
        }

        return true;
    }

    private static Node findMiddle(Node head) {
        Node slowPointer = head;
        Node fastPointer = head;

        // When fastPointer reaches the end, slowPointer will be at the middle
        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        return slowPointer;
    }

    private static Node reverseList(Node head) {
        Node previousNode = null;
        Node currentNode = head;

        while (currentNode != null) {
            Node nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        return previousNode;
    }

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
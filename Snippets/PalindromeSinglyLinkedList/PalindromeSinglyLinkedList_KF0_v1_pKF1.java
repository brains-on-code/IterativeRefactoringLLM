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

    public static boolean isPalindrome(final Iterable<?> linkedList) {
        Stack<Object> stack = new Stack<>();

        for (final Object value : linkedList) {
            stack.push(value);
        }

        for (final Object value : linkedList) {
            if (!value.equals(stack.pop())) {
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

        Node slowPointer = head;
        Node fastPointer = head;

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        Node middleNode = slowPointer;

        Node previousNode = null;
        Node currentNode = middleNode;
        Node nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        Node leftPointer = head;
        Node rightPointer = previousNode;

        while (leftPointer != null && rightPointer != null) {
            if (leftPointer.value != rightPointer.value) {
                return false;
            }
            rightPointer = rightPointer.next;
            leftPointer = leftPointer.next;
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
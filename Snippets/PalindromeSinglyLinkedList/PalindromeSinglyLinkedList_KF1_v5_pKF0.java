package com.thealgorithms.misc;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for palindrome checks.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if the given iterable is a palindrome by comparing it to its reverse.
     *
     * @param iterable the iterable to check
     * @return true if the iterable is a palindrome, false otherwise
     */
    public static boolean isIterablePalindrome(final Iterable<?> iterable) {
        if (iterable == null) {
            return false;
        }

        Deque<Object> reversedElements = new LinkedList<>();
        for (Object element : iterable) {
            reversedElements.push(element);
        }

        for (Object element : iterable) {
            Object reversedElement = reversedElements.pop();
            if (!element.equals(reversedElement)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given singly linked list is a palindrome.
     *
     * @param head the head of the linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isLinkedListPalindrome(final Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node middleNode = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(middleNode);

        Node firstHalfPointer = head;
        Node secondHalfPointer = reversedSecondHalfHead;

        while (firstHalfPointer != null && secondHalfPointer != null) {
            if (firstHalfPointer.value != secondHalfPointer.value) {
                return false;
            }
            firstHalfPointer = firstHalfPointer.next;
            secondHalfPointer = secondHalfPointer.next;
        }

        return true;
    }

    private static Node findMiddle(final Node head) {
        Node slowPointer = head;
        Node fastPointer = head;

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        return slowPointer;
    }

    private static Node reverseList(final Node head) {
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

        Node(final int value) {
            this.value = value;
        }
    }
}
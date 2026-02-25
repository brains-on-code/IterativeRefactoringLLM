package com.thealgorithms.misc;

import java.util.Stack;

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

        Stack<Object> elements = new Stack<>();
        for (Object element : iterable) {
            elements.push(element);
        }

        for (Object element : iterable) {
            Object reversedElement = elements.pop();
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

        Node middle = findMiddle(head);
        Node secondHalfReversedHead = reverseList(middle);

        Node firstPointer = head;
        Node secondPointer = secondHalfReversedHead;

        while (firstPointer != null && secondPointer != null) {
            if (firstPointer.value != secondPointer.value) {
                return false;
            }
            firstPointer = firstPointer.next;
            secondPointer = secondPointer.next;
        }

        return true;
    }

    private static Node findMiddle(final Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private static Node reverseList(final Node head) {
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

    static class Node {
        int value;
        Node next;

        Node(final int value) {
            this.value = value;
        }
    }
}
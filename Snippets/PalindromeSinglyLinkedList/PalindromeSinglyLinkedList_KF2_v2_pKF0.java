package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Utility class; prevent instantiation
    }

    public static <T> boolean isPalindrome(final Iterable<T> linkedList) {
        Stack<T> stack = new Stack<>();

        for (T value : linkedList) {
            stack.push(value);
        }

        for (T value : linkedList) {
            T top = stack.pop();
            if (!areEqual(value, top)) {
                return false;
            }
        }

        return true;
    }

    private static <T> boolean areEqual(T first, T second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

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

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        return slowPointer;
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
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
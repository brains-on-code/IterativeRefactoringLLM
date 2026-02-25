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

        Node middle = findMiddle(head);
        Node secondHalfReversedHead = reverseList(middle);

        Node left = head;
        Node right = secondHalfReversedHead;

        while (left != null && right != null) {
            if (left.value != right.value) {
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

        Node(int value) {
            this.value = value;
        }
    }
}
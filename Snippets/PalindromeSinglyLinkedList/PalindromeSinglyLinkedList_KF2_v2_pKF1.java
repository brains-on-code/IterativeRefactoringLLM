package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
    }

    public static boolean isPalindrome(final Iterable<?> elements) {
        Stack<Object> stack = new Stack<>();

        for (final Object element : elements) {
            stack.push(element);
        }

        for (final Object element : elements) {
            if (!element.equals(stack.pop())) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node middle = slow;

        Node previous = null;
        Node current = middle;
        Node next;

        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        Node left = head;
        Node right = previous;

        while (left != null && right != null) {
            if (left.value != right.value) {
                return false;
            }
            right = right.next;
            left = left.next;
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
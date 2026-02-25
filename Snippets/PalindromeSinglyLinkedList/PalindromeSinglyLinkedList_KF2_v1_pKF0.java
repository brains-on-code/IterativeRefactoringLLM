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
            if (value == null ? top != null : !value.equals(top)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node midNode = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(midNode);

        Node left = head;
        Node right = reversedSecondHalfHead;

        while (left != null && right != null) {
            if (left.val != right.val) {
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
        Node prev = null;
        Node current = head;

        while (current != null) {
            Node nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }

        return prev;
    }

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }
}
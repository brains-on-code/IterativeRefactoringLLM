package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
        // Prevent instantiation
    }

    /**
     * Checks if the given iterable represents a palindrome sequence.
     *
     * @param linkedList an iterable collection of elements
     * @return true if the sequence is a palindrome, false otherwise
     */
    public static boolean isPalindrome(final Iterable<?> linkedList) {
        Stack<Object> stack = new Stack<>();

        for (Object element : linkedList) {
            stack.push(element);
        }

        for (Object element : linkedList) {
            if (!element.equals(stack.pop())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a singly linked list is a palindrome using O(1) extra space.
     *
     * @param head head node of the singly linked list
     * @return true if the list is a palindrome, false otherwise
     */
    public static boolean isPalindromeOptimised(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node midNode = findMiddle(head);
        Node reversedSecondHalfHead = reverseList(midNode);

        return areListsEqual(head, reversedSecondHalfHead);
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
        Node prevNode = null;
        Node currNode = head;

        while (currNode != null) {
            Node nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }

        return prevNode;
    }

    private static boolean areListsEqual(Node first, Node second) {
        Node left = first;
        Node right = second;

        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }

        return true;
    }

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }
}
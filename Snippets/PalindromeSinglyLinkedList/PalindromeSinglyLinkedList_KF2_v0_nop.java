package com.thealgorithms.misc;

import java.util.Stack;


public final class PalindromeSinglyLinkedList {
    private PalindromeSinglyLinkedList() {
    }

    public static boolean isPalindrome(final Iterable linkedList) {
        var linkedListValues = new Stack<>();

        for (final var x : linkedList) {
            linkedListValues.push(x);
        }

        for (final var x : linkedList) {
            if (x != linkedListValues.pop()) {
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
        Node midNode = slow;

        Node prevNode = null;
        Node currNode = midNode;
        Node nextNode;
        while (currNode != null) {
            nextNode = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = nextNode;
        }
        Node left = head;
        Node right = prevNode;
        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            right = right.next;
            left = left.next;
        }
        return true;
    }
    static class Node {
        int val;
        Node next;
        Node(int val) {
            this.val = val;
            this.next = null;
        }
    }
}

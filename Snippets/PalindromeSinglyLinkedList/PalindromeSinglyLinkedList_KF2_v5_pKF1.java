package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
    }

    public static boolean isPalindrome(final Iterable<?> elements) {
        Stack<Object> elementStack = new Stack<>();

        for (final Object element : elements) {
            elementStack.push(element);
        }

        for (final Object element : elements) {
            if (!element.equals(elementStack.pop())) {
                return false;
            }
        }

        return true;
    }

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
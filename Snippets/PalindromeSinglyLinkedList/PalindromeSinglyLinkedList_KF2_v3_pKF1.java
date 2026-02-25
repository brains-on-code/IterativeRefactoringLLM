package com.thealgorithms.misc;

import java.util.Stack;

public final class PalindromeSinglyLinkedList {

    private PalindromeSinglyLinkedList() {
    }

    public static boolean isPalindrome(final Iterable<?> iterable) {
        Stack<Object> elementsStack = new Stack<>();

        for (final Object element : iterable) {
            elementsStack.push(element);
        }

        for (final Object element : iterable) {
            if (!element.equals(elementsStack.pop())) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindromeOptimised(Node headNode) {
        if (headNode == null || headNode.next == null) {
            return true;
        }

        Node slowPointer = headNode;
        Node fastPointer = headNode;

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

        Node leftPointer = headNode;
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

        Node(int nodeValue) {
            this.value = nodeValue;
            this.next = null;
        }
    }
}
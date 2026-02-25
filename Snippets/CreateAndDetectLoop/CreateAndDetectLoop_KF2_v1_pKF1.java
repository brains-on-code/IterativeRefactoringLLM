package com.thealgorithms.datastructures.lists;

public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    static final class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    static void createLoop(Node head, int startPosition, int endPosition) {
        if (startPosition == 0 || endPosition == 0) {
            return;
        }

        Node startNode = head;
        Node endNode = head;

        int currentStartPosition = 1;
        int currentEndPosition = 1;

        while (currentStartPosition < startPosition && startNode != null) {
            startNode = startNode.next;
            currentStartPosition++;
        }

        while (currentEndPosition < endPosition && endNode != null) {
            endNode = endNode.next;
            currentEndPosition++;
        }

        if (startNode != null && endNode != null) {
            endNode.next = startNode;
        }
    }

    static boolean hasLoop(Node head) {
        Node slowPointer = head;
        Node fastPointer = head;

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;

            if (slowPointer == fastPointer) {
                return true;
            }
        }

        return false;
    }
}
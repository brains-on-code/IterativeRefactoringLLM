package com.thealgorithms.datastructures.lists;

public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    static final class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    static void createLoop(Node head, int position1, int position2) {
        if (head == null || position1 <= 0 || position2 <= 0) {
            return;
        }

        Node nodeAtPosition1 = head;
        Node nodeAtPosition2 = head;

        int currentPosition1 = 1;
        int currentPosition2 = 1;

        while (currentPosition1 < position1 && nodeAtPosition1 != null) {
            nodeAtPosition1 = nodeAtPosition1.next;
            currentPosition1++;
        }

        while (currentPosition2 < position2 && nodeAtPosition2 != null) {
            nodeAtPosition2 = nodeAtPosition2.next;
            currentPosition2++;
        }

        if (nodeAtPosition1 != null && nodeAtPosition2 != null) {
            nodeAtPosition2.next = nodeAtPosition1;
        }
    }

    static boolean detectLoop(Node head) {
        if (head == null) {
            return false;
        }

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
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

        Node nodeAtPosition1 = getNodeAtPosition(head, position1);
        Node nodeAtPosition2 = getNodeAtPosition(head, position2);

        if (nodeAtPosition1 != null && nodeAtPosition2 != null) {
            nodeAtPosition2.next = nodeAtPosition1;
        }
    }

    private static Node getNodeAtPosition(Node head, int position) {
        Node current = head;
        int currentPosition = 1;

        while (current != null && currentPosition < position) {
            current = current.next;
            currentPosition++;
        }

        return current;
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
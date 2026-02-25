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

    static void createLoop(Node head, int loopEndPosition, int loopStartPosition) {
        if (head == null || loopEndPosition <= 0 || loopStartPosition <= 0) {
            return;
        }

        Node loopEndNode = head;
        Node loopStartNode = head;

        int currentPosition = 1;
        while (currentPosition < loopEndPosition && loopEndNode != null) {
            loopEndNode = loopEndNode.next;
            currentPosition++;
        }

        currentPosition = 1;
        while (currentPosition < loopStartPosition && loopStartNode != null) {
            loopStartNode = loopStartNode.next;
            currentPosition++;
        }

        if (loopEndNode != null && loopStartNode != null) {
            loopStartNode.next = loopEndNode;
        }
    }

    static boolean detectLoop(Node head) {
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
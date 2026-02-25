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

    static void createLoop(Node head, int loopStartPosition, int loopEndPosition) {
        if (loopStartPosition <= 0 || loopEndPosition <= 0) {
            return;
        }

        Node loopStartNode = head;
        Node loopEndNode = head;

        int currentStartPosition = 1;
        int currentEndPosition = 1;

        while (currentStartPosition < loopStartPosition && loopStartNode != null) {
            loopStartNode = loopStartNode.next;
            currentStartPosition++;
        }

        while (currentEndPosition < loopEndPosition && loopEndNode != null) {
            loopEndNode = loopEndNode.next;
            currentEndPosition++;
        }

        if (loopStartNode != null && loopEndNode != null) {
            loopEndNode.next = loopStartNode;
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
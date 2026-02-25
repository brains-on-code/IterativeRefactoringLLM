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

    static void createLoop(Node head, int loopStartIndex, int loopEndIndex) {
        if (loopStartIndex <= 0 || loopEndIndex <= 0) {
            return;
        }

        Node loopStartNode = head;
        Node loopEndNode = head;

        int currentIndexForStart = 1;
        int currentIndexForEnd = 1;

        while (currentIndexForStart < loopStartIndex && loopStartNode != null) {
            loopStartNode = loopStartNode.next;
            currentIndexForStart++;
        }

        while (currentIndexForEnd < loopEndIndex && loopEndNode != null) {
            loopEndNode = loopEndNode.next;
            currentIndexForEnd++;
        }

        if (loopStartNode != null && loopEndNode != null) {
            loopEndNode.next = loopStartNode;
        }
    }

    static boolean hasLoop(Node head) {
        Node slowTraversalNode = head;
        Node fastTraversalNode = head;

        while (fastTraversalNode != null && fastTraversalNode.next != null) {
            slowTraversalNode = slowTraversalNode.next;
            fastTraversalNode = fastTraversalNode.next.next;

            if (slowTraversalNode == fastTraversalNode) {
                return true;
            }
        }

        return false;
    }
}
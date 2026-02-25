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

        int currentStartIndex = 1;
        int currentEndIndex = 1;

        while (currentStartIndex < loopStartIndex && loopStartNode != null) {
            loopStartNode = loopStartNode.next;
            currentStartIndex++;
        }

        while (currentEndIndex < loopEndIndex && loopEndNode != null) {
            loopEndNode = loopEndNode.next;
            currentEndIndex++;
        }

        if (loopStartNode != null && loopEndNode != null) {
            loopEndNode.next = loopStartNode;
        }
    }

    static boolean hasLoop(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
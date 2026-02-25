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
        }
    }

    static void createLoop(Node head, int loopEndPosition, int loopStartPosition) {
        if (head == null || loopEndPosition <= 0 || loopStartPosition <= 0) {
            return;
        }

        Node loopEndNode = getNodeAtPosition(head, loopEndPosition);
        Node loopStartNode = getNodeAtPosition(head, loopStartPosition);

        if (loopEndNode != null && loopStartNode != null) {
            loopStartNode.next = loopEndNode;
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
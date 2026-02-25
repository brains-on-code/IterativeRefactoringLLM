package com.thealgorithms.datastructures.lists;

public final class CreateAndDetectLoop {

    private CreateAndDetectLoop() {
        throw new UnsupportedOperationException("Utility class");
    }

    static final class Node {
        final int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static void createLoop(Node head, int fromPosition, int toPosition) {
        if (head == null || !isValidPosition(fromPosition) || !isValidPosition(toPosition)) {
            return;
        }

        Node fromNode = getNodeAtPosition(head, fromPosition);
        Node toNode = getNodeAtPosition(head, toPosition);

        if (fromNode == null || toNode == null) {
            return;
        }

        toNode.next = fromNode;
    }

    private static boolean isValidPosition(int position) {
        return position > 0;
    }

    private static Node getNodeAtPosition(Node head, int position) {
        Node current = head;
        int currentIndex = 1;

        while (current != null && currentIndex < position) {
            current = current.next;
            currentIndex++;
        }

        return current;
    }

    static boolean detectLoop(Node head) {
        if (head == null) {
            return false;
        }

        Node slowPointer = head;
        Node fastPointer = head;

        while (hasNextTwoNodes(fastPointer)) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;

            if (slowPointer == fastPointer) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasNextTwoNodes(Node node) {
        return node != null && node.next != null;
    }
}
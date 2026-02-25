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
        if (head == null || fromPosition <= 0 || toPosition <= 0) {
            return;
        }

        Node fromNode = getNodeAtPosition(head, fromPosition);
        Node toNode = getNodeAtPosition(head, toPosition);

        if (fromNode == null || toNode == null) {
            return;
        }

        toNode.next = fromNode;
    }

    private static Node getNodeAtPosition(Node head, int position) {
        Node current = head;
        int index = 1;

        while (current != null && index < position) {
            current = current.next;
            index++;
        }

        return current;
    }

    static boolean detectLoop(Node head) {
        if (head == null) {
            return false;
        }

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
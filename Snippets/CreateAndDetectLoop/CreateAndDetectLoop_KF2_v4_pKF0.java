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
        if (head == null || !isPositivePosition(fromPosition) || !isPositivePosition(toPosition)) {
            return;
        }

        Node fromNode = getNodeAtPosition(head, fromPosition);
        Node toNode = getNodeAtPosition(head, toPosition);

        if (fromNode == null || toNode == null) {
            return;
        }

        toNode.next = fromNode;
    }

    private static boolean isPositivePosition(int position) {
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

        Node slow = head;
        Node fast = head;

        while (canAdvanceTwoSteps(fast)) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    private static boolean canAdvanceTwoSteps(Node node) {
        return node != null && node.next != null;
    }
}
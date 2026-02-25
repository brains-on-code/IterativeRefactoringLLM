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
            next = null;
        }
    }


    static void createLoop(Node head, int position1, int position2) {
        if (position1 == 0 || position2 == 0) {
            return;
        }

        Node node1 = head;
        Node node2 = head;

        int count1 = 1;
        int count2 = 1;
        while (count1 < position1 && node1 != null) {
            node1 = node1.next;
            count1++;
        }

        while (count2 < position2 && node2 != null) {
            node2 = node2.next;
            count2++;
        }

        if (node1 != null && node2 != null) {
            node2.next = node1;
        }
    }


    static boolean detectLoop(Node head) {
        Node sptr = head;
        Node fptr = head;

        while (fptr != null && fptr.next != null) {
            sptr = sptr.next;
            fptr = fptr.next.next;
            if (sptr == fptr) {
                return true;
            }
        }
        return false;
    }
}

package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    /**
     * Merges k sorted linked lists into a single sorted linked list.
     *
     * @param lists array of head nodes of the sorted linked lists
     * @param n     number of linked lists
     * @return head node of the merged sorted linked list
     */
    Node mergeKList(Node[] lists, int n) {
        if (lists == null || n == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        // Add the head of each non-null list to the min-heap
        for (Node node : lists) {
            if (node != null) {
                minHeap.add(node);
            }
        }

        if (minHeap.isEmpty()) {
            return null;
        }

        // Initialize the head of the merged list
        Node head = minHeap.poll();
        if (head.next != null) {
            minHeap.add(head.next);
        }

        Node current = head;

        // Continuously extract the smallest node and add its successor
        while (!minHeap.isEmpty()) {
            Node smallest = minHeap.poll();
            current.next = smallest;
            current = smallest;

            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

        return head;
    }

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this(data, null);
        }

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
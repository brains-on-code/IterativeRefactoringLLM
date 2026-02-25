package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    /**
     * Merge k sorted linked lists into a single sorted linked list.
     *
     * @param lists array containing the head node of each sorted linked list
     * @param n     number of linked lists (expected to match lists.length)
     * @return head node of the merged sorted linked list, or null if no nodes
     */
    Node mergeKList(Node[] lists, int n) {
        if (lists == null || n == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        // Seed the heap with the head node of each non-empty list
        for (Node head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        if (minHeap.isEmpty()) {
            return null;
        }

        // The first extracted node becomes the head of the merged list
        Node mergedHead = minHeap.poll();
        if (mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Node current = mergedHead;

        // Repeatedly append the smallest node and push its successor
        while (!minHeap.isEmpty()) {
            Node smallest = minHeap.poll();
            current.next = smallest;
            current = smallest;

            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

        return mergedHead;
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
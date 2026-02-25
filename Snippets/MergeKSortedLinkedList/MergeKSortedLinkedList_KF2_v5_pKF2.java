package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    /**
     * Merges k sorted linked lists into a single sorted linked list.
     *
     * @param lists array containing the head node of each sorted linked list
     * @param n     number of linked lists (should be equal to lists.length)
     * @return head node of the merged sorted linked list, or null if there are no nodes
     */
    Node mergeKList(Node[] lists, int n) {
        if (lists == null || n == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        for (Node head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        if (minHeap.isEmpty()) {
            return null;
        }

        Node dummyHead = new Node(0);
        Node current = dummyHead;

        while (!minHeap.isEmpty()) {
            Node smallest = minHeap.poll();
            current.next = smallest;
            current = current.next;

            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

        return dummyHead.next;
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
package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility for merging multiple sorted singly linked lists into one sorted list.
 *
 * <p>Example:
 * <pre>{@code
 * Node list1 = new Node(1, new Node(4, new Node(5)));
 * Node list2 = new Node(1, new Node(3, new Node(4)));
 * Node list3 = new Node(2, new Node(6));
 * Node[] lists = { list1, list2, list3 };
 *
 * MergeKSortedLinkedList merger = new MergeKSortedLinkedList();
 * Node mergedHead = merger.mergeKList(lists, lists.length);
 * }</pre>
 */
public class MergeKSortedLinkedList {

    /**
     * Merges {@code n} sorted linked lists into a single sorted linked list.
     *
     * <p>All lists are assumed to be sorted in non-decreasing order. The method
     * uses a min-heap (priority queue) to always extract the smallest current
     * node among the list heads.</p>
     *
     * @param lists array containing the head node of each list
     * @param n     number of lists (typically {@code lists.length})
     * @return head of the merged sorted linked list, or {@code null} if there are no nodes
     */
    Node mergeKList(Node[] lists, int n) {
        if (lists == null || n <= 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        for (Node head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        if (minHeap.isEmpty()) {
            return null;
        }

        Node dummyHead = new Node(0);
        Node tail = dummyHead;

        while (!minHeap.isEmpty()) {
            Node smallest = minHeap.poll();
            tail.next = smallest;
            tail = smallest;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        return dummyHead.next;
    }

    /**
     * Singly linked list node containing an integer value.
     */
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
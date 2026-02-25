package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * The MergeKSortedLinkedList class provides a method to merge multiple sorted linked lists
 * into a single sorted linked list.
 * This implementation uses a min-heap (priority queue) to efficiently
 * find the smallest node across all lists, thus optimizing the merge process.
 *
 * <p>Example usage:
 * <pre>
 * Node list1 = new Node(1, new Node(4, new Node(5)));
 * Node list2 = new Node(1, new Node(3, new Node(4)));
 * Node list3 = new Node(2, new Node(6));
 * Node[] lists = { list1, list2, list3 };
 *
 * MergeKSortedLinkedList merger = new MergeKSortedLinkedList();
 * Node mergedHead = merger.mergeKList(lists, lists.length);
 * </pre>
 * </p>
 *
 * <p>This class is designed to handle nodes of integer linked lists and can be expanded for additional data types if needed.</p>
 *
 * author Arun Pandey (https://github.com/pandeyarun709)
 */
public class MergeKSortedLinkedList {

    /**
     * Merges K sorted linked lists into a single sorted linked list.
     *
     * <p>This method uses a priority queue (min-heap) to repeatedly extract the smallest node from the heads of all the lists,
     * then inserts the next node from that list into the heap. The process continues until all nodes have been processed,
     * resulting in a fully merged and sorted linked list.</p>
     *
     * @param lists Array of linked list heads to be merged.
     * @param n     Number of linked lists.
     * @return Head of the merged sorted linked list.
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

        Node dummyHead = new Node(0);
        Node tail = dummyHead;

        while (!minHeap.isEmpty()) {
            Node smallestNode = minHeap.poll();
            tail.next = smallestNode;
            tail = smallestNode;

            if (smallestNode.next != null) {
                minHeap.offer(smallestNode.next);
            }
        }

        return dummyHead.next;
    }

    /**
     * Represents a node in the linked list.
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
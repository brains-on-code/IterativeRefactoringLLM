package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

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
        Node current = dummyHead;

        while (!minHeap.isEmpty()) {
            Node smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = current.next;

            if (smallestNode.next != null) {
                minHeap.offer(smallestNode.next);
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
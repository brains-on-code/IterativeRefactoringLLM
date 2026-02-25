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

        for (Node node : lists) {
            if (node != null) {
                minHeap.offer(node);
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
                minHeap.offer(smallest.next);
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
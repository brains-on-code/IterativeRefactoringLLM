package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    Node mergeKLists(Node[] lists, int listCount) {
        if (lists == null || listCount == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        for (Node listHead : lists) {
            if (listHead != null) {
                minHeap.add(listHead);
            }
        }

        Node mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Node current = mergedHead;
        while (!minHeap.isEmpty()) {
            Node smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = smallestNode;

            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
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
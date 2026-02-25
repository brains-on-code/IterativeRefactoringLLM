package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    Node mergeKLists(Node[] listHeads, int numberOfLists) {
        if (listHeads == null || numberOfLists == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        for (Node head : listHeads) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        Node mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Node currentNode = mergedHead;
        while (!minHeap.isEmpty()) {
            Node nextSmallestNode = minHeap.poll();
            currentNode.next = nextSmallestNode;
            currentNode = nextSmallestNode;

            if (nextSmallestNode.next != null) {
                minHeap.add(nextSmallestNode.next);
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
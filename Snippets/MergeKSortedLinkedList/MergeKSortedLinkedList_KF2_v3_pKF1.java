package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    Node mergeKLists(Node[] listHeads, int numberOfLists) {
        if (listHeads == null || numberOfLists == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.data));

        for (Node head : listHeads) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        Node mergedListHead = minHeap.poll();
        if (mergedListHead != null && mergedListHead.next != null) {
            minHeap.add(mergedListHead.next);
        }

        Node currentTail = mergedListHead;
        while (!minHeap.isEmpty()) {
            Node smallestNode = minHeap.poll();
            currentTail.next = smallestNode;
            currentTail = smallestNode;

            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }

        return mergedListHead;
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
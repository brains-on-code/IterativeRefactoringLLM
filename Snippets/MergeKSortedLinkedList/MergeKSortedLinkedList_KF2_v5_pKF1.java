package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLinkedList {

    Node mergeKLists(Node[] listHeads, int numberOfLists) {
        if (listHeads == null || numberOfLists == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (Node head : listHeads) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        Node mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Node mergedTail = mergedHead;
        while (!minHeap.isEmpty()) {
            Node smallestNode = minHeap.poll();
            mergedTail.next = smallestNode;
            mergedTail = smallestNode;

            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }

        return mergedHead;
    }

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this(value, null);
        }

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}
package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;


public class MergeKSortedLinkedList {


    Node mergeKList(Node[] a, int n) {
        if (a == null || n == 0) {
            return null;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(x -> x.data));

        for (Node node : a) {
            if (node != null) {
                minHeap.add(node);
            }
        }

        Node head = minHeap.poll();
        if (head != null && head.next != null) {
            minHeap.add(head.next);
        }

        Node curr = head;
        while (!minHeap.isEmpty()) {
            Node temp = minHeap.poll();
            curr.next = temp;
            curr = temp;

            if (temp.next != null) {
                minHeap.add(temp.next);
            }
        }

        return head;
    }


    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}

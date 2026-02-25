package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for operations on linked lists.
 */
public class Class1 {

    /**
     * Merges k sorted linked lists into a single sorted linked list.
     *
     * @param lists an array of heads of sorted linked lists
     * @param k     the number of lists
     * @return the head of the merged sorted linked list, or null if input is invalid
     */
    Class2 method1(Class2[] lists, int k) {
        if (lists == null || k == 0) {
            return null;
        }

        // Min-heap based on node value
        PriorityQueue<Class2> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        // Add the head of each non-null list to the heap
        for (Class2 head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        // Initialize the merged list with the smallest element
        Class2 mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Class2 current = mergedHead;

        // Continuously extract the smallest node and add its successor to the heap
        while (!minHeap.isEmpty()) {
            Class2 smallest = minHeap.poll();
            current.next = smallest;
            current = smallest;

            if (smallest.next != null) {
                minHeap.add(smallest.next);
            }
        }

        return mergedHead;
    }

    /**
     * Node of a singly linked list.
     */
    static class Class2 {
        int value;
        Class2 next;

        Class2(int value) {
            this.value = value;
            this.next = null;
        }

        Class2(int value, Class2 next) {
            this.value = value;
            this.next = next;
        }
    }
}
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

        PriorityQueue<Class2> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (Class2 head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        Class2 mergedHead = minHeap.poll();
        if (mergedHead == null) {
            return null;
        }

        if (mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Class2 current = mergedHead;

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
        }

        Class2(int value, Class2 next) {
            this.value = value;
            this.next = next;
        }
    }
}
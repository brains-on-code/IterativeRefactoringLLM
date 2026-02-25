package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Class1 {

    /**
     * Merges k sorted linked lists into a single sorted list.
     *
     * @param lists array of list heads
     * @param k     number of lists
     * @return head of merged sorted list, or null if input is invalid
     */
    Class2 mergeKSortedLists(Class2[] lists, int k) {
        if (lists == null || k == 0) {
            return null;
        }

        PriorityQueue<Class2> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (Class2 head : lists) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        Class2 mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        Class2 current = mergedHead;
        while (!minHeap.isEmpty()) {
            Class2 smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = smallestNode;

            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }

        return mergedHead;
    }

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
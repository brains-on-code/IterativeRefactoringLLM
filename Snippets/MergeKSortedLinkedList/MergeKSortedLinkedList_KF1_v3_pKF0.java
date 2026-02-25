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
        if (lists == null || k <= 0) {
            return null;
        }

        PriorityQueue<Class2> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (Class2 head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        if (minHeap.isEmpty()) {
            return null;
        }

        Class2 dummyHead = new Class2(0);
        Class2 tail = dummyHead;

        while (!minHeap.isEmpty()) {
            Class2 smallestNode = minHeap.poll();
            tail.next = smallestNode;
            tail = tail.next;

            if (smallestNode.next != null) {
                minHeap.offer(smallestNode.next);
            }
        }

        return dummyHead.next;
    }

    static class Class2 {
        int value;
        Class2 next;

        Class2(int value) {
            this(value, null);
        }

        Class2(int value, Class2 next) {
            this.value = value;
            this.next = next;
        }
    }
}
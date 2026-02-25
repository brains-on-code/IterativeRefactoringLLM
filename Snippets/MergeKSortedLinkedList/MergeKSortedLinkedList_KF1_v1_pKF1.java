package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Class1 {

    Class2 mergeKSortedLists(Class2[] lists, int numberOfLists) {
        if (lists == null || numberOfLists == 0) {
            return null;
        }

        PriorityQueue<Class2> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (Class2 listHead : lists) {
            if (listHead != null) {
                minHeap.add(listHead);
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
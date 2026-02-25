package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KSortedListMerger {

    ListNode mergeKSortedLists(ListNode[] lists, int listCount) {
        if (lists == null || listCount == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (ListNode listHead : lists) {
            if (listHead != null) {
                minHeap.add(listHead);
            }
        }

        ListNode mergedHead = minHeap.poll();
        if (mergedHead != null && mergedHead.next != null) {
            minHeap.add(mergedHead.next);
        }

        ListNode mergedTail = mergedHead;
        while (!minHeap.isEmpty()) {
            ListNode smallestNode = minHeap.poll();
            mergedTail.next = smallestNode;
            mergedTail = smallestNode;

            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }

        return mergedHead;
    }

    static class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
            this.next = null;
        }

        ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }
}
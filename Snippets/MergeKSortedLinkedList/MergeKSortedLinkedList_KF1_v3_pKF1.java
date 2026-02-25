package com.thealgorithms.datastructures.lists;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KSortedListMerger {

    ListNode mergeKSortedLists(ListNode[] listHeads, int numberOfLists) {
        if (listHeads == null || numberOfLists == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        for (ListNode head : listHeads) {
            if (head != null) {
                minHeap.add(head);
            }
        }

        ListNode mergedListHead = minHeap.poll();
        if (mergedListHead != null && mergedListHead.next != null) {
            minHeap.add(mergedListHead.next);
        }

        ListNode mergedListTail = mergedListHead;
        while (!minHeap.isEmpty()) {
            ListNode nextSmallestNode = minHeap.poll();
            mergedListTail.next = nextSmallestNode;
            mergedListTail = nextSmallestNode;

            if (nextSmallestNode.next != null) {
                minHeap.add(nextSmallestNode.next);
            }
        }

        return mergedListHead;
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
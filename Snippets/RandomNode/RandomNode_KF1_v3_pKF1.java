package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNodeSelector {

    private final List<Integer> values;
    private final int size;
    private static final Random RANDOM = new Random();

    static class ListNode {

        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
        }
    }

    public RandomNodeSelector(ListNode head) {
        values = new ArrayList<>();
        int count = 0;
        ListNode current = head;

        while (current != null) {
            values.add(current.value);
            current = current.next;
            count++;
        }
        this.size = count;
    }

    public int getRandomNodeValue() {
        int randomIndex = RANDOM.nextInt(size);
        return values.get(randomIndex);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(15);
        head.next = new ListNode(25);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(1);
        head.next.next.next.next = new ListNode(78);
        head.next.next.next.next.next = new ListNode(63);

        RandomNodeSelector selector = new RandomNodeSelector(head);
        int randomNodeValue = selector.getRandomNodeValue();
        System.out.println("Random Node : " + randomNodeValue);
    }
}
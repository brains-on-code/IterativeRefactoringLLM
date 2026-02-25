package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RandomNode {

    private final List<Integer> list;
    private int size;
    private static final Random RAND = new Random();

    static class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public RandomNode(ListNode head) {
        list = new ArrayList<>();
        ListNode temp = head;

        while (temp != null) {
            list.add(temp.val);
            temp = temp.next;
            size++;
        }
    }

    public int getRandom() {
        int index = RAND.nextInt(size);
        return list.get(index);
    }


    public static void main(String[] args) {
        ListNode head = new ListNode(15);
        head.next = new ListNode(25);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(1);
        head.next.next.next.next = new ListNode(78);
        head.next.next.next.next.next = new ListNode(63);
        RandomNode list = new RandomNode(head);
        int randomNum = list.getRandom();
        System.out.println("Random Node : " + randomNum);
    }
}

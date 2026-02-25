package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Class1 {

    private final List<Integer> values;
    private int size;
    private static final Random RANDOM = new Random();

    static class Node {

        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public Class1(Node head) {
        values = new ArrayList<>();
        Node current = head;

        while (current != null) {
            values.add(current.value);
            current = current.next;
            size++;
        }
    }

    public int getRandomValue() {
        int index = RANDOM.nextInt(size);
        return values.get(index);
    }

    public static void main(String[] args) {
        Node head = new Node(15);
        head.next = new Node(25);
        head.next.next = new Node(4);
        head.next.next.next = new Node(1);
        head.next.next.next.next = new Node(78);
        head.next.next.next.next.next = new Node(63);

        Class1 listWrapper = new Class1(head);
        int randomValue = listWrapper.getRandomValue();
        System.out.println("Random Node : " + randomValue);
    }
}
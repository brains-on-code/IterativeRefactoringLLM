package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkedList {
    private Node head;
    private Node tail;

    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insert(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        if (value < head.value) {
            newNode.next = head;
            head = newNode;
            return;
        }

        if (value > tail.value) {
            tail.next = newNode;
            tail = newNode;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.value < value) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            tail = newNode;
        }
    }

    public boolean delete(int value) {
        if (head == null) {
            return false;
        }

        if (head.value == value) {
            if (head.next == null) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.value == value) {
                if (current.next == tail) {
                    tail = current;
                }
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean contains(int value) {
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        List<String> values = new ArrayList<>();
        Node current = head;
        while (current != null) {
            values.add(String.valueOf(current.value));
            current = current.next;
        }
        return "[" + String.join(", ", values) + "]";
    }

    public final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
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

        Node currentNode = head;
        while (currentNode.next != null && currentNode.next.value < value) {
            currentNode = currentNode.next;
        }

        newNode.next = currentNode.next;
        currentNode.next = newNode;

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

        Node currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.next.value == value) {
                if (currentNode.next == tail) {
                    tail = currentNode;
                }
                currentNode.next = currentNode.next.next;
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    public boolean contains(int value) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == value) {
                return true;
            }
            currentNode = currentNode.next;
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
        Node currentNode = head;
        while (currentNode != null) {
            values.add(String.valueOf(currentNode.value));
            currentNode = currentNode.next;
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
package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkedList {

    private Node head;
    private Node tail;

    public SortedLinkedList() {
        head = null;
        tail = null;
    }

    /**
     * Inserts a value into the list while maintaining sorted order.
     */
    public void insert(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            insertIntoEmptyList(newNode);
            return;
        }

        if (value <= head.value) {
            insertAtHead(newNode);
            return;
        }

        if (value >= tail.value) {
            insertAtTail(newNode);
            return;
        }

        insertInMiddle(newNode);
    }

    private void insertIntoEmptyList(Node newNode) {
        head = newNode;
        tail = newNode;
    }

    private void insertAtHead(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    private void insertAtTail(Node newNode) {
        tail.next = newNode;
        tail = newNode;
    }

    private void insertInMiddle(Node newNode) {
        Node current = head;

        while (current.next != null && current.next.value < newNode.value) {
            current = current.next;
        }

        linkAfter(current, newNode);
    }

    private void linkAfter(Node current, Node newNode) {
        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            tail = newNode;
        }
    }

    /**
     * Removes the first occurrence of the given value from the list.
     *
     * @return true if a node was removed, false otherwise
     */
    public boolean remove(int value) {
        if (isEmpty()) {
            return false;
        }

        if (head.value == value) {
            removeHead();
            return true;
        }

        return removeNonHead(value);
    }

    private void removeHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
    }

    private boolean removeNonHead(int value) {
        Node current = head;

        while (current.next != null) {
            if (current.next.value == value) {
                unlinkNext(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private void unlinkNext(Node current) {
        if (current.next == tail) {
            tail = current;
        }
        current.next = current.next.next;
    }

    /**
     * Checks whether the list contains the given value.
     */
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

    /**
     * Checks whether the list is empty.
     */
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
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
        }
    }
}
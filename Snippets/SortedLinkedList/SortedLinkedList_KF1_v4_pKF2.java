package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * A sorted singly linked list of integers.
 *
 * <p>Elements are always kept in ascending order.</p>
 */
public class SortedSinglyLinkedList {

    private Node head;
    private Node tail;

    public SortedSinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a value while preserving ascending order.
     *
     * @param value the value to insert
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

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            tail = newNode;
        }
    }

    /**
     * Removes the first occurrence of {@code value}.
     *
     * @param value the value to remove
     * @return {@code true} if removed, {@code false} otherwise
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

    /**
     * Checks whether {@code value} exists in the list.
     *
     * @param value the value to search for
     * @return {@code true} if found, {@code false} otherwise
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
     * Returns whether the list has no elements.
     *
     * @return {@code true} if the list has no elements
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the list as {@code [v1, v2, ...]}.
     */
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

    /**
     * Node of the singly linked list.
     */
    public final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple sorted singly linked list of integers.
 *
 * <p>Elements are kept in ascending order. Supports insertion, deletion,
 * search, emptiness check, and string representation.</p>
 */
public class SortedSinglyLinkedList {
    /** First node of the list. */
    private Node head;

    /** Last node of the list. */
    private Node tail;

    /** Creates an empty sorted list. */
    public SortedSinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a value into the list, keeping it sorted in ascending order.
     *
     * @param value the value to insert
     */
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

    /**
     * Removes the first occurrence of the given value from the list.
     *
     * @param value the value to remove
     * @return {@code true} if a node was removed; {@code false} otherwise
     */
    public boolean remove(int value) {
        if (head == null) {
            return false;
        }

        if (head.value == value) {
            head = head.next;
            if (head == null) {
                tail = null;
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

    /**
     * Checks whether the list contains the given value.
     *
     * @param value the value to search for
     * @return {@code true} if the value is found; {@code false} otherwise
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
     *
     * @return {@code true} if the list is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns a string representation of the list in the form
     * {@code [v1, v2, ...]}.
     *
     * @return a string representation of the list
     */
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

    /** Node of the singly linked list. */
    public final class Node {
        /** Stored integer value. */
        public final int value;

        /** Reference to the next node in the list. */
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
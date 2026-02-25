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
        head = null;
        tail = null;
    }

    /**
     * Inserts a value while preserving ascending order.
     *
     * @param value value to insert
     */
    public void insert(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            return;
        }

        if (value <= head.value) {
            newNode.next = head;
            head = newNode;
            return;
        }

        if (value >= tail.value) {
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
     * Removes the first occurrence of {@code value}.
     *
     * @param value value to remove
     * @return {@code true} if removed, {@code false} otherwise
     */
    public boolean remove(int value) {
        if (isEmpty()) {
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
     * Checks whether {@code value} exists in the list.
     *
     * @param value value to search for
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
            next = null;
        }
    }
}
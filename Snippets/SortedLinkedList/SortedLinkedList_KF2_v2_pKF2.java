package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * A singly linked list that maintains its elements in ascending sorted order.
 */
public class SortedLinkedList {

    private Node head;
    private Node tail;

    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a value into the list while preserving sorted order.
     *
     * @param value the value to insert
     */
    public void insert(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            insertIntoEmptyList(newNode);
            return;
        }

        if (value < head.value) {
            insertAtHead(newNode);
            return;
        }

        if (value > tail.value) {
            insertAtTail(newNode);
            return;
        }

        insertInMiddle(newNode);
    }

    /**
     * Inserts the first node into an empty list.
     */
    private void insertIntoEmptyList(Node newNode) {
        head = newNode;
        tail = newNode;
    }

    /**
     * Inserts a node at the beginning of the list.
     */
    private void insertAtHead(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    /**
     * Inserts a node at the end of the list.
     */
    private void insertAtTail(Node newNode) {
        tail.next = newNode;
        tail = newNode;
    }

    /**
     * Inserts a node somewhere between head and tail, preserving sorted order.
     */
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
     * Deletes the first occurrence of the given value from the list.
     *
     * @param value the value to delete
     * @return true if a node was deleted; false otherwise
     */
    public boolean delete(int value) {
        if (isEmpty()) {
            return false;
        }

        if (head.value == value) {
            deleteHead();
            return true;
        }

        return deleteNonHead(value);
    }

    /**
     * Deletes the head node from the list.
     */
    private void deleteHead() {
        if (head.next == null) {
            head = null;
            tail = null;
            return;
        }
        head = head.next;
    }

    /**
     * Deletes a node that is not the head.
     *
     * @param value the value to delete
     * @return true if a node was deleted; false otherwise
     */
    private boolean deleteNonHead(int value) {
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
     * Searches for a value in the list.
     *
     * @param value the value to search for
     * @return true if the value exists in the list; false otherwise
     */
    public boolean search(int value) {
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
     * @return true if the list has no elements; false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns a string representation of the list in the form [a, b, c].
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        List<String> elements = new ArrayList<>();
        Node current = head;

        while (current != null) {
            elements.add(String.valueOf(current.value));
            current = current.next;
        }

        return "[" + String.join(", ", elements) + "]";
    }

    /**
     * Node of a singly linked list storing an integer value.
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
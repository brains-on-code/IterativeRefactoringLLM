package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * A singly linked list that keeps its integer elements in ascending order.
 *
 * <p>Elements are inserted into their correct sorted position. The list supports
 * insertion, deletion, search, emptiness check, and string representation.</p>
 *
 * <p>Reference:
 * https://runestone.academy/ns/books/published/cppds/LinearLinked/ImplementinganOrderedList.html
 * </p>
 *
 * <pre>
 * SortedLinkedList list = new SortedLinkedList();
 * list.insert(10);
 * list.insert(5);
 * list.insert(20);
 * System.out.println(list); // [5, 10, 20]
 * </pre>
 */
public class SortedLinkedList {
    private Node head;
    private Node tail;

    /** Creates an empty sorted linked list. */
    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a value into the list, preserving ascending order.
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
     * Deletes the first occurrence of the given value from the list.
     *
     * @param value the value to delete
     * @return {@code true} if a node was deleted; {@code false} otherwise
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

    private void deleteHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
    }

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
     * Checks whether the given value exists in the list.
     *
     * @param value the value to search for
     * @return {@code true} if the value is found; {@code false} otherwise
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
     * Returns whether the list contains no elements.
     *
     * @return {@code true} if the list is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns a string representation of the list in the form
     * {@code [v1, v2, v3]}.
     *
     * @return a string representation of the list
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

    /** A node in the singly linked list. */
    public final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
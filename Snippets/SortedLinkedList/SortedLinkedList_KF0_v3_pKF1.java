package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * The SortedLinkedList class represents a singly linked list that maintains its elements in sorted order.
 * Elements are ordered based on their natural ordering, with smaller elements at the head and larger elements toward the tail.
 * The class provides methods for inserting, deleting, and searching elements, as well as checking if the list is empty.
 * <p>
 * This implementation utilizes a singly linked list to maintain a dynamically sorted list.
 * </p>
 * <p>
 * Further information can be found here:
 * https://runestone.academy/ns/books/published/cppds/LinearLinked/ImplementinganOrderedList.html
 * </p>
 *
 * <b>Usage Example:</b>
 * <pre>
 *     SortedLinkedList list = new SortedLinkedList();
 *     list.insert(10);
 *     list.insert(5);
 *     list.insert(20);
 *     System.out.println(list); // Outputs: [5, 10, 20]
 * </pre>
 */
public class SortedLinkedList {
    private Node head;
    private Node tail;

    /**
     * Initializes an empty sorted linked list.
     */
    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a new integer into the list, maintaining sorted order.
     *
     * @param value the integer to insert
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
     * Deletes the first occurrence of a specified integer in the list.
     *
     * @param value the integer to delete
     * @return {@code true} if the element was found and deleted; {@code false} otherwise
     */
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

    /**
     * Searches for a specified integer in the list.
     *
     * @param value the integer to search for
     * @return {@code true} if the value is present in the list; {@code false} otherwise
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
     * Checks if the list is empty.
     *
     * @return {@code true} if the list is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns a string representation of the sorted linked list in the format [element1, element2, ...].
     *
     * @return a string representation of the sorted linked list
     */
    @Override
    public String toString() {
        if (head == null) {
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
     * Node represents an element in the sorted linked list.
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
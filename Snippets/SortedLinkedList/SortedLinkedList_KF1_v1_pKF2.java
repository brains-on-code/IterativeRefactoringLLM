package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple sorted singly linked list of integers.
 *
 * <p>Elements are kept in ascending order. Supports insertion, deletion,
 * search, emptiness check, and string representation.</p>
 */
public class Class1 {
    /** Head (first) node of the list. */
    private Class2 head;

    /** Tail (last) node of the list. */
    private Class2 tail;

    /** Creates an empty sorted list. */
    public Class1() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Inserts a value into the list, keeping it sorted in ascending order.
     *
     * @param value the value to insert
     */
    public void method1(int value) {
        Class2 newNode = new Class2(value);

        if (head == null) {
            // List is empty: new node becomes both head and tail
            this.head = newNode;
            this.tail = newNode;
        } else if (value < head.value) {
            // Insert at the beginning
            newNode.next = this.head;
            this.head = newNode;
        } else if (value > tail.value) {
            // Insert at the end
            this.tail.next = newNode;
            this.tail = newNode;
        } else {
            // Insert somewhere in the middle
            Class2 current = head;
            while (current.next != null && current.next.value < value) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;

            if (newNode.next == null) {
                this.tail = newNode;
            }
        }
    }

    /**
     * Removes the first occurrence of the given value from the list.
     *
     * @param value the value to remove
     * @return {@code true} if a node was removed; {@code false} otherwise
     */
    public boolean method2(int value) {
        if (this.head == null) {
            return false;
        }

        // Remove from head
        if (this.head.value == value) {
            if (this.head.next == null) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = this.head.next;
            }
            return true;
        }

        // Remove from middle or tail
        Class2 current = this.head;
        while (current.next != null) {
            if (current.next.value == value) {
                if (current.next == this.tail) {
                    this.tail = current;
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
    public boolean method3(int value) {
        Class2 current = this.head;
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
    public boolean method4() {
        return head == null;
    }

    /**
     * Returns a string representation of the list in the form
     * {@code [v1, v2, ...]}.
     *
     * @return a string representation of the list
     */
    @Override
    public String method5() {
        if (this.head == null) {
            return "[]";
        }

        List<String> values = new ArrayList<>();
        Class2 current = this.head;
        while (current != null) {
            values.add(String.valueOf(current.value));
            current = current.next;
        }
        return "[" + String.join(", ", values) + "]";
    }

    /** Node of the singly linked list. */
    public final class Class2 {
        /** Stored integer value. */
        public final int value;

        /** Reference to the next node in the list. */
        public Class2 next;

        public Class2(int value) {
            this.value = value;
            this.next = null;
        }
    }
}
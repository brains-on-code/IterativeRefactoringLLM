package com.thealgorithms.datastructures.lists;

public class CircleLinkedList<E> {

    /**
     * Node of a circular singly linked list.
     * Uses a sentinel head node; real data starts from head.next.
     */
    static final class Node<E> {
        Node<E> next;
        E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    /** Number of elements in the list (excluding the sentinel). */
    private int size;

    /** Sentinel node; does not store user data. */
    private final Node<E> head;

    /** Last data node; tail.next always points to head. */
    private Node<E> tail;

    public CircleLinkedList() {
        head = new Node<>(null, null);
        head.next = head; // empty list: head points to itself
        tail = head;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    /**
     * Appends a non-null value to the end of the circular list.
     *
     * @param value element to append
     * @throws NullPointerException if value is null
     */
    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, head);

        if (isEmpty()) {
            head.next = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[ ");
        Node<E> current = head.next;

        while (current != head) {
            sb.append(current.value);
            current = current.next;
            if (current != head) {
                sb.append(", ");
            }
        }

        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Removes and returns the element at the specified position.
     *
     * @param pos zero-based index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if pos is out of range
     */
    public E remove(int pos) {
        validatePosition(pos);

        Node<E> before = head;
        for (int i = 0; i < pos; i++) {
            before = before.next;
        }

        Node<E> target = before.next;
        E removedValue = target.value;

        before.next = target.next;
        if (target == tail) {
            tail = before;
        }

        size--;
        return removedValue;
    }

    /** Returns true if the list contains no elements. */
    private boolean isEmpty() {
        return size == 0;
    }

    /** Validates that the given position is within the bounds of the list. */
    private void validatePosition(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + pos);
        }
    }
}
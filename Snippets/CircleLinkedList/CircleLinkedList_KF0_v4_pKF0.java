package com.thealgorithms.datastructures.lists;

/**
 * Circular singly linked list implementation.
 *
 * <p>The last node points back to the first node, forming a circle. A dummy head
 * node is used to simplify edge cases; it does not store user data.
 *
 * @param <E> the type of elements held in this list
 */
public class CircleLinkedList<E> {

    /**
     * Node of a circular singly linked list.
     *
     * @param <E> the type of element stored in the node
     */
    static final class Node<E> {
        E value;
        Node<E> next;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    /** Number of elements in the list. */
    private int size;

    /** Dummy head node; head.next is the first real element or head itself if empty. */
    private final Node<E> head;

    /** Tail node; tail.next always points to head. */
    private Node<E> tail;

    /** Initializes an empty circular linked list with a dummy head node. */
    public CircleLinkedList() {
        head = new Node<>(null, null);
        head.next = head; // circular reference for empty list
        tail = head;
        size = 0;
    }

    /** Returns the current size of the list. */
    public int getSize() {
        return size;
    }

    /**
     * Appends a new element to the end of the list.
     *
     * @param value the value to append to the list
     * @throws NullPointerException if the value is null
     */
    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, head);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    /**
     * Returns a string representation of the list in the format "[ element1, element2, ... ]".
     * An empty list is represented as "[]".
     *
     * @return the string representation of the list
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[ ");
        Node<E> current = head.next;

        while (current != head) {
            builder.append(current.value);
            current = current.next;
            if (current != head) {
                builder.append(", ");
            }
        }

        builder.append(" ]");
        return builder.toString();
    }

    /**
     * Removes and returns the element at the specified position in the list.
     *
     * @param pos the position of the element to remove (0-based)
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the position is out of range
     */
    public E remove(int pos) {
        ensureValidIndex(pos);

        Node<E> previous = getNodeBefore(pos);
        Node<E> toRemove = previous.next;

        previous.next = toRemove.next;
        if (toRemove == tail) {
            tail = previous;
        }

        size--;
        return toRemove.value;
    }

    /** Returns {@code true} if the list contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the node immediately before the node at the given position.
     *
     * @param pos the position whose previous node is requested
     * @return the node before the specified position
     */
    private Node<E> getNodeBefore(int pos) {
        Node<E> current = head;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Validates that the given position is within the bounds of the list.
     *
     * @param pos the position to validate
     * @throws IndexOutOfBoundsException if the position is out of range
     */
    private void ensureValidIndex(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + pos);
        }
    }
}
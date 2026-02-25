package com.thealgorithms.datastructures.lists;

/**
 * Circular singly linked list implementation.
 *
 * <p>The list uses a dummy head node whose {@code next} reference points to the
 * first real element (or to itself when the list is empty). The {@code tail}
 * reference always points to the last real element (or to {@code head} when
 * the list is empty). The last real element's {@code next} reference points
 * back to {@code head}, forming a circle.
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
        Node<E> next;
        E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private final Node<E> head;
    private Node<E> tail;

    /**
     * Constructs an empty circular linked list.
     *
     * <p>The dummy head node's {@code next} reference initially points to itself,
     * representing an empty circular structure.
     */
    public CircleLinkedList() {
        head = new Node<>(null, null);
        head.next = head;
        tail = head;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements
     */
    public int getSize() {
        return size;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param value the element to append
     * @throws NullPointerException if {@code value} is {@code null}
     */
    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, head);

        if (size == 0) {
            head.next = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    /**
     * Returns a string representation of this list.
     *
     * <p>The representation has the form {@code [ e1, e2, ..., en ]}.
     * An empty list is represented as {@code []}.
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        if (size == 0) {
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
     * Removes and returns the element at the specified position in this list.
     *
     * @param pos index of the element to remove (0-based)
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if {@code pos} is out of range
     *                                   ({@code pos < 0 || pos >= size})
     */
    public E remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + pos);
        }

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
}
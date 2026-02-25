package com.thealgorithms.datastructures.lists;

/**
 * A simple generic circular singly linked list implementation with a sentinel (dummy head) node.
 *
 * @param <E> the type of elements held in this list
 */
public class Class1<E> {

    /**
     * Node of a singly linked list.
     *
     * @param <E> the type of element stored in the node
     */
    static final class Node<E> {

        Node<E> next;
        E element;

        private Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    /** Number of elements in the list. */
    private int size;

    /** Sentinel (dummy head) node of the circular list. */
    Node<E> head = null;

    /** Last (tail) node in the list; points back to {@code head}. */
    private Node<E> tail;

    /**
     * Constructs an empty list with a sentinel node.
     */
    public Class1() {
        head = new Node<>(null, head);
        tail = head;
        size = 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @throws NullPointerException if the specified element is {@code null}
     */
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        if (tail == null) {
            tail = new Node<>(element, head);
            head.next = tail;
        } else {
            tail.next = new Node<>(element, head);
            tail = tail.next;
        }
        size++;
    }

    /**
     * Returns a string representation of this list.
     * The representation is of the form {@code [ e1, e2, ... ]}.
     *
     * @return a string representation of the list
     */
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[ ");
        Node<E> current = head.next;
        while (current != head) {
            builder.append(current.element);
            if (current.next != head) {
                builder.append(", ");
            }
            current = current.next;
        }
        builder.append(" ]");
        return builder.toString();
    }

    /**
     * Removes the element at the specified position in this list and returns it.
     *
     * @param index index of the element to be removed (0-based)
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        Node<E> previous = head;
        for (int i = 1; i <= index; i++) {
            previous = previous.next;
        }
        Node<E> target = previous.next;
        E removedElement = target.element;
        previous.next = target.next;

        if (target == tail) {
            tail = previous;
        }
        target = null;
        size--;
        return removedElement;
    }
}
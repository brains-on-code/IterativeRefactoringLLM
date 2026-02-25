package com.thealgorithms.datastructures.lists;

/**
 * This class is a circular singly linked list implementation. In a circular linked list,
 * the last node points back to the first node, creating a circular chain.
 *
 * <p>This implementation includes basic operations such as appending elements
 * to the end, removing elements from a specified position, and converting
 * the list to a string representation.
 *
 * @param <E> the type of elements held in this list
 */
public class CircleLinkedList<E> {

    /**
     * A static nested class representing a node in the circular linked list.
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

    private int size;
    private final Node<E> sentinel;
    private Node<E> tail;

    /**
     * Initializes a new circular linked list. A dummy sentinel node is used for simplicity,
     * pointing initially to itself to ensure the list is never empty.
     */
    public CircleLinkedList() {
        sentinel = new Node<>(null, null);
        sentinel.next = sentinel;
        tail = sentinel;
        size = 0;
    }

    /**
     * Returns the current size of the list.
     *
     * @return the number of elements in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Appends a new element to the end of the list. Throws a NullPointerException if
     * a null value is provided.
     *
     * @param element the value to append to the list
     * @throws NullPointerException if the value is null
     */
    public void append(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(element, sentinel);

        if (tail == sentinel) {
            sentinel.next = newNode;
        } else {
            tail.next = newNode;
        }

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
        if (size == 0) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[ ");
        Node<E> current = sentinel.next;

        while (current != sentinel) {
            builder.append(current.element);
            if (current.next != sentinel) {
                builder.append(", ");
            }
            current = current.next;
        }

        builder.append(" ]");
        return builder.toString();
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Throws an IndexOutOfBoundsException if the position is invalid.
     *
     * @param index the position of the element to remove
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the position is out of range
     */
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        Node<E> previous = sentinel;
        for (int i = 0; i < index; i++) {
            previous = previous.next;
        }

        Node<E> nodeToRemove = previous.next;
        E removedElement = nodeToRemove.element;
        previous.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = previous;
        }

        size--;
        return removedElement;
    }
}
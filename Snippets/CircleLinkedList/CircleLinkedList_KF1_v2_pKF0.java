package com.thealgorithms.datastructures.lists;

public class CircularLinkedList<E> {

    private static final class Node<E> {
        private Node<E> next;
        private final E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private final Node<E> sentinel;
    private Node<E> tail;

    public CircularLinkedList() {
        sentinel = new Node<>(null, null);
        sentinel.next = sentinel;
        tail = sentinel;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(element, sentinel);

        if (isEmpty()) {
            sentinel.next = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    public E remove(int index) {
        validateIndex(index);

        Node<E> previous = sentinel;
        for (int i = 0; i < index; i++) {
            previous = previous.next;
        }

        Node<E> toRemove = previous.next;
        E removedValue = toRemove.value;
        previous.next = toRemove.next;

        if (toRemove == tail) {
            tail = previous;
        }

        size--;
        return removedValue;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[ ");
        Node<E> current = sentinel.next;

        while (current != sentinel) {
            builder.append(current.value);
            if (current.next != sentinel) {
                builder.append(", ");
            }
            current = current.next;
        }

        builder.append(" ]");
        return builder.toString();
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + index);
        }
    }
}
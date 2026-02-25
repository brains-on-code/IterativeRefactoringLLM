package com.thealgorithms.datastructures.lists;

public class CircularLinkedList<E> {

    private static final class Node<E> {
        Node<E> next;
        E value;

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

        if (tail == sentinel) {
            tail = new Node<>(element, sentinel);
            sentinel.next = tail;
        } else {
            tail.next = new Node<>(element, sentinel);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public String toString() {
        if (size == 0) {
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

    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

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
}
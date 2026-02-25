package com.thealgorithms.datastructures.lists;

public class CircularLinkedList<E> {

    static final class Node<E> {

        Node<E> next;
        E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private final Node<E> sentinelHead;
    private Node<E> tail;

    public CircularLinkedList() {
        sentinelHead = new Node<>(null, null);
        sentinelHead.next = sentinelHead;
        tail = sentinelHead;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, sentinelHead);

        if (tail == sentinelHead) {
            sentinelHead.next = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder("[ ");
        Node<E> current = sentinelHead.next;

        while (current != sentinelHead) {
            builder.append(current.value);
            if (current.next != sentinelHead) {
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

        Node<E> previous = sentinelHead;
        for (int i = 0; i < index; i++) {
            previous = previous.next;
        }

        Node<E> nodeToRemove = previous.next;
        E removedValue = nodeToRemove.value;
        previous.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = previous;
        }

        size--;
        return removedValue;
    }
}
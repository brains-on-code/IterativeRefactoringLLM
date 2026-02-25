package com.thealgorithms.datastructures.lists;

public class CircleLinkedList<E> {

    private static final class Node<E> {
        private Node<E> next;
        private final E value;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private final Node<E> head;
    private Node<E> tail;

    public CircleLinkedList() {
        head = new Node<>(null, null);
        head.next = head; // sentinel points to itself
        tail = head;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, head);

        if (tail == head && size == 0) {
            head.next = newNode;
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

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + index);
        }

        Node<E> previous = head;
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
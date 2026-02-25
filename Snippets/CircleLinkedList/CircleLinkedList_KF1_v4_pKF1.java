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
    private Node<E> headSentinel;
    private Node<E> tail;

    public CircularLinkedList() {
        headSentinel = new Node<>(null, null);
        headSentinel.next = headSentinel;
        tail = headSentinel;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(element, headSentinel);

        if (tail == headSentinel) {
            headSentinel.next = newNode;
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
        Node<E> current = headSentinel.next;

        while (current != headSentinel) {
            builder.append(current.value);
            if (current.next != headSentinel) {
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

        Node<E> previous = headSentinel;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
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
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
        this.sentinel = new Node<>(null, null);
        this.sentinel.next = this.sentinel;
        this.tail = this.sentinel;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        requireNonNull(element);
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
        checkIndex(index);

        Node<E> previous = getNodeBefore(index);
        Node<E> nodeToRemove = previous.next;

        previous.next = nodeToRemove.next;
        if (nodeToRemove == tail) {
            tail = previous;
        }

        size--;
        return nodeToRemove.value;
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

    private void requireNonNull(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
    }

    private Node<E> getNodeBefore(int index) {
        Node<E> current = sentinel;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + index);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }
}
package com.thealgorithms.datastructures.lists;

public class CircleLinkedList<E> {

    static final class Node<E> {

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

    public CircleLinkedList() {
        sentinel = new Node<>(null, null);
        sentinel.next = sentinel;
        tail = sentinel;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public void append(E value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }

        Node<E> newNode = new Node<>(value, sentinel);

        if (tail == sentinel) {
            sentinel.next = newNode;
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
        Node<E> currentNode = sentinel.next;

        while (currentNode != sentinel) {
            builder.append(currentNode.value);
            if (currentNode.next != sentinel) {
                builder.append(", ");
            }
            currentNode = currentNode.next;
        }

        builder.append(" ]");
        return builder.toString();
    }

    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        Node<E> previousNode = sentinel;
        for (int i = 0; i < index; i++) {
            previousNode = previousNode.next;
        }

        Node<E> nodeToRemove = previousNode.next;
        E removedValue = nodeToRemove.value;
        previousNode.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = previousNode;
        }

        size--;
        return removedValue;
    }
}
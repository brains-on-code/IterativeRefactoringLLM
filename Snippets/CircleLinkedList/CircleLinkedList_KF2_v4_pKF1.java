package com.thealgorithms.datastructures.lists;

public class CircularLinkedList<E> {

    static final class Node<E> {

        Node<E> next;
        E element;

        private Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private int size;
    private final Node<E> headSentinel;
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
        Node<E> currentNode = headSentinel.next;

        while (currentNode != headSentinel) {
            builder.append(currentNode.element);
            if (currentNode.next != headSentinel) {
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

        Node<E> previousNode = headSentinel;
        for (int i = 0; i < index; i++) {
            previousNode = previousNode.next;
        }

        Node<E> nodeToRemove = previousNode.next;
        E removedElement = nodeToRemove.element;
        previousNode.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = previousNode;
        }

        size--;
        return removedElement;
    }
}
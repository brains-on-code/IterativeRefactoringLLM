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
    private Node<E> sentinelHead;
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

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        if (tail == sentinelHead) {
            tail = new Node<>(element, sentinelHead);
            sentinelHead.next = tail;
        } else {
            tail.next = new Node<>(element, sentinelHead);
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
        Node<E> currentNode = sentinelHead.next;
        while (currentNode != sentinelHead) {
            builder.append(currentNode.value);
            if (currentNode.next != sentinelHead) {
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

        Node<E> previousNode = sentinelHead;
        for (int currentIndex = 1; currentIndex <= index; currentIndex++) {
            previousNode = previousNode.next;
        }
        Node<E> nodeToRemove = previousNode.next;
        E removedValue = nodeToRemove.value;
        previousNode.next = nodeToRemove.next;

        if (nodeToRemove == tail) {
            tail = previousNode;
        }
        nodeToRemove = null;
        size--;
        return removedValue;
    }
}
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
    private Node<E> head;
    private Node<E> tail;

    public CircularLinkedList() {
        head = new Node<>(null, null);
        head.next = head;
        tail = head;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the list");
        }
        if (tail == head) {
            tail = new Node<>(element, head);
            head.next = tail;
        } else {
            tail.next = new Node<>(element, head);
            tail = tail.next;
        }
        size++;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[ ");
        Node<E> currentNode = head.next;
        while (currentNode != head) {
            builder.append(currentNode.value);
            if (currentNode.next != head) {
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

        Node<E> previousNode = head;
        for (int i = 1; i <= index; i++) {
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
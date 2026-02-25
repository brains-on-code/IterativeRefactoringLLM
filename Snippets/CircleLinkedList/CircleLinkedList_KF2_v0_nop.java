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
    Node<E> head = null;
    private Node<E> tail;


    public CircleLinkedList() {
        head = new Node<>(null, head);
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
        if (tail == null) {
            tail = new Node<>(value, head);
            head.next = tail;
        } else {
            tail.next = new Node<>(value, head);
            tail = tail.next;
        }
        size++;
    }


    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[ ");
        Node<E> current = head.next;
        while (current != head) {
            sb.append(current.value);
            if (current.next != head) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append(" ]");
        return sb.toString();
    }


    public E remove(int pos) {
        if (pos >= size || pos < 0) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        Node<E> before = head;
        for (int i = 1; i <= pos; i++) {
            before = before.next;
        }
        Node<E> destroy = before.next;
        E saved = destroy.value;
        before.next = destroy.next;

        if (destroy == tail) {
            tail = before;
        }
        destroy = null;
        size--;
        return saved;
    }
}

package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class Node<E> {
        E value;
        Node<E> next = null;
        Node<E> previous = null;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }

        size++;
    }

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public T pollFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = head.value;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }

        size--;
        return removedValue;
    }

    public T pollLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = tail.value;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }

        size--;
        return removedValue;
    }

    public T peekFirst() {
        return head != null ? head.value : null;
    }

    public T peekLast() {
        return tail != null ? tail.value : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder dequeRepresentation = new StringBuilder("Head -> ");
        Node<T> current = head;

        while (current != null) {
            dequeRepresentation.append(current.value);
            if (current.next != null) {
                dequeRepresentation.append(" <-> ");
            }
            current = current.next;
        }

        dequeRepresentation.append(" <- Tail");
        return dequeRepresentation.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        for (int i = 0; i < 42; i++) {
            if (i / 42.0 < 0.5) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }
        }

        System.out.println(deque);
        System.out.println("Size: " + deque.size());
        System.out.println();

        deque.pollFirst();
        deque.pollFirst();
        deque.pollLast();
        System.out.println(deque);
        System.out.println("Size: " + deque.size());
        System.out.println();

        int initialSize = deque.size();
        for (int i = 0; i < initialSize; i++) {
            int removedValue;
            if (i / 39.0 < 0.5) {
                removedValue = deque.pollFirst();
            } else {
                removedValue = deque.pollLast();
            }

            System.out.println("Removing: " + removedValue);
        }

        System.out.println(deque);
        System.out.println(deque.size());
    }
}
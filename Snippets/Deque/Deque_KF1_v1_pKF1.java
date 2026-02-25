package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class Node<S> {
        S value;
        Node<S> next = null;
        Node<S> prev = null;

        Node(S value) {
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
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = head.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removedValue;
    }

    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = tail.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
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
        StringBuilder builder = new StringBuilder("Head -> ");
        Node<T> current = head;
        while (current != null) {
            builder.append(current.value);
            if (current.next != null) {
                builder.append(" <-> ");
            }
            current = current.next;
        }
        builder.append(" <- Tail");
        return builder.toString();
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

        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque);
        System.out.println("Size: " + deque.size());
        System.out.println();

        int initialSize = deque.size();
        for (int i = 0; i < initialSize; i++) {
            int removed;
            if (i / 39.0 < 0.5) {
                removed = deque.removeFirst();
            } else {
                removed = deque.removeLast();
            }

            System.out.println("Removing: " + removed);
        }

        System.out.println(deque);
        System.out.println(deque.size());
    }
}
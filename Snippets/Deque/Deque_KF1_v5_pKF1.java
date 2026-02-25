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

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);

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

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previous = tail;
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
            head.previous = null;
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
        StringBuilder dequeStringBuilder = new StringBuilder("Head -> ");
        Node<T> current = head;
        while (current != null) {
            dequeStringBuilder.append(current.value);
            if (current.next != null) {
                dequeStringBuilder.append(" <-> ");
            }
            current = current.next;
        }
        dequeStringBuilder.append(" <- Tail");
        return dequeStringBuilder.toString();
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
            int removedElement;
            if (i / 39.0 < 0.5) {
                removedElement = deque.removeFirst();
            } else {
                removedElement = deque.removeLast();
            }

            System.out.println("Removing: " + removedElement);
        }

        System.out.println(deque);
        System.out.println(deque.size());
    }
}
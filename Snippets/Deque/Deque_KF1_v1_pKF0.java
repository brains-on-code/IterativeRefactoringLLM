package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A generic double-ended queue (deque) implementation using a doubly linked list.
 *
 * @param <T> the type of elements held in this deque
 */
public class Class1<T> {

    /**
     * Node of a doubly linked list.
     */
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

    /**
     * Adds an element to the front of the deque.
     *
     * @param value the element to add
     */
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

    /**
     * Adds an element to the back of the deque.
     *
     * @param value the element to add
     */
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

    /**
     * Removes and returns the element at the front of the deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = head.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;
        return value;
    }

    /**
     * Removes and returns the element at the back of the deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = tail.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return value;
    }

    /**
     * Returns the element at the front of the deque without removing it.
     *
     * @return the first element, or {@code null} if the deque is empty
     */
    public T peekFirst() {
        return head != null ? head.value : null;
    }

    /**
     * Returns the element at the back of the deque without removing it.
     *
     * @return the last element, or {@code null} if the deque is empty
     */
    public T peekLast() {
        return tail != null ? tail.value : null;
    }

    /**
     * Returns the number of elements in the deque.
     *
     * @return the size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the deque contains no elements.
     *
     * @return {@code true} if the deque is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a string representation of the deque.
     *
     * @return a string in the form "Head -> a <-> b <-> c <- Tail"
     */
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
        Class1<Integer> deque = new Class1<>();

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
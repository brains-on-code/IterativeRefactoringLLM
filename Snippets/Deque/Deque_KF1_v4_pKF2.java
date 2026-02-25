package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A generic double-ended queue (deque) backed by a doubly linked list.
 *
 * @param <T> the type of elements held in this deque
 */
public class Deque<T> {

    /**
     * Doubly linked list node.
     *
     * @param <S> the type of the stored value
     */
    private static class Node<S> {
        S value;
        Node<S> next;
        Node<S> prev;

        Node(S value) {
            this.value = value;
        }
    }

    /** Front of the deque. */
    private Node<T> head;

    /** Back of the deque. */
    private Node<T> tail;

    /** Number of elements in the deque. */
    private int size;

    /**
     * Adds an element to the front of the deque.
     *
     * @param value the element to add
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            head = tail = newNode;
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

        if (isEmpty()) {
            head = tail = newNode;
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
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = head.value;

        if (head == tail) {
            clear();
        } else {
            head = head.next;
            head.prev = null;
            size--;
        }

        return value;
    }

    /**
     * Removes and returns the element at the back of the deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = tail.value;

        if (head == tail) {
            clear();
        } else {
            tail = tail.prev;
            tail.next = null;
            size--;
        }

        return value;
    }

    /**
     * Returns the element at the front of the deque without removing it.
     *
     * @return the front element, or {@code null} if the deque is empty
     */
    public T peekFirst() {
        return head == null ? null : head.value;
    }

    /**
     * Returns the element at the back of the deque without removing it.
     *
     * @return the back element, or {@code null} if the deque is empty
     */
    public T peekLast() {
        return tail == null ? null : tail.value;
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

    /** Removes all elements from the deque. */
    private void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns a string representation of the deque in the form:
     * {@code Head -> e1 <-> e2 <-> ... <- Tail}
     *
     * @return a string representation of this deque
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
        Deque<Integer> deque = new Deque<>();

        for (int i = 0; i < 42; i++) {
            if (i < 21) {
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
            if (i < initialSize / 2) {
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
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
        Node<S> next;
        Node<S> prev;

        Node(S value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Adds an element to the front of the deque.
     *
     * @param value the element to add
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            initializeSingleNodeDeque(newNode);
        } else {
            linkBeforeHead(newNode);
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
            initializeSingleNodeDeque(newNode);
        } else {
            linkAfterTail(newNode);
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
        ensureNotEmpty();

        T value = head.value;
        if (isSingleElement()) {
            clear();
        } else {
            unlinkHead();
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
        ensureNotEmpty();

        T value = tail.value;
        if (isSingleElement()) {
            clear();
        } else {
            unlinkTail();
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
        return head == null ? null : head.value;
    }

    /**
     * Returns the element at the back of the deque without removing it.
     *
     * @return the last element, or {@code null} if the deque is empty
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

    private void initializeSingleNodeDeque(Node<T> node) {
        head = node;
        tail = node;
    }

    private void linkBeforeHead(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void linkAfterTail(Node<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void unlinkHead() {
        head = head.next;
        head.prev = null;
    }

    private void unlinkTail() {
        tail = tail.prev;
        tail.next = null;
    }

    private void clear() {
        head = null;
        tail = null;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    private boolean isSingleElement() {
        return head == tail;
    }

    public static void main(String[] args) {
        Class1<Integer> deque = new Class1<>();

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
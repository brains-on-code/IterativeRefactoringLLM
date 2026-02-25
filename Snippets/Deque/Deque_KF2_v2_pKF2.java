package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A generic double-ended queue (deque) implementation backed by a doubly linked list.
 *
 * @param <T> the type of elements held in this deque
 */
public class Deque<T> {

    /**
     * Node of a doubly linked list used internally by {@link Deque}.
     *
     * @param <S> the type of the value stored in the node
     */
    private static class DequeNode<S> {
        S value;
        DequeNode<S> next;
        DequeNode<S> prev;

        DequeNode(S value) {
            this.value = value;
        }
    }

    private DequeNode<T> head;
    private DequeNode<T> tail;
    private int size;

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param value element to be added
     */
    public void addFirst(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);

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
     * Inserts the specified element at the end of this deque.
     *
     * @param value element to be added
     */
    public void addLast(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);

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
     * Removes and returns the first element of this deque.
     *
     * @return the element at the front of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    public T pollFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = head.value;

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;
        return value;
    }

    /**
     * Removes and returns the last element of this deque.
     *
     * @return the element at the end of this deque
     * @throws NoSuchElementException if this deque is empty
     */
    public T pollLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = tail.value;

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return value;
    }

    /**
     * Retrieves, but does not remove, the first element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the first element, or {@code null} if this deque is empty
     */
    public T peekFirst() {
        return head != null ? head.value : null;
    }

    /**
     * Retrieves, but does not remove, the last element of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the last element, or {@code null} if this deque is empty
     */
    public T peekLast() {
        return tail != null ? tail.value : null;
    }

    /**
     * Returns the number of elements in this deque.
     *
     * @return the size of this deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a string representation of this deque from head to tail.
     *
     * @return a string representation of this deque
     */
    @Override
    public String toString() {
        StringBuilder dequeString = new StringBuilder("Head -> ");
        DequeNode<T> current = head;

        while (current != null) {
            dequeString.append(current.value);
            if (current.next != null) {
                dequeString.append(" <-> ");
            }
            current = current.next;
        }

        dequeString.append(" <- Tail");
        return dequeString.toString();
    }

    /**
     * Demonstrates basic usage of the {@link Deque} class.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Deque<Integer> myDeque = new Deque<>();

        for (int i = 0; i < 42; i++) {
            if (i < 21) {
                myDeque.addFirst(i);
            } else {
                myDeque.addLast(i);
            }
        }

        System.out.println(myDeque);
        System.out.println("Size: " + myDeque.size());
        System.out.println();

        myDeque.pollFirst();
        myDeque.pollFirst();
        myDeque.pollLast();

        System.out.println(myDeque);
        System.out.println("Size: " + myDeque.size());
        System.out.println();

        int dequeSize = myDeque.size();
        for (int i = 0; i < dequeSize; i++) {
            int removed;
            if (i < dequeSize / 2) {
                removed = myDeque.pollFirst();
            } else {
                removed = myDeque.pollLast();
            }

            System.out.println("Removing: " + removed);
        }

        System.out.println(myDeque);
        System.out.println(myDeque.size());
    }
}
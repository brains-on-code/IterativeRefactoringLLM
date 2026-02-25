package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A {@code Deque} (double-ended queue) is a data structure that allows
 * insertion and removal of elements at both the head and the tail.
 *
 * @param <T> the type of elements held in this deque
 */
public class Deque<T> {

    /**
     * Doubly linked list node used internally by the deque.
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
     * Inserts the specified element at the head of this deque.
     *
     * @param value the element to add
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
     * Inserts the specified element at the tail of this deque.
     *
     * @param value the element to add
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
     * Removes and returns the element at the head of this deque.
     *
     * @return the element previously at the head
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
     * Removes and returns the element at the tail of this deque.
     *
     * @return the element previously at the tail
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
     * Retrieves, but does not remove, the element at the head of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the element at the head, or {@code null} if empty
     */
    public T peekFirst() {
        return head == null ? null : head.value;
    }

    /**
     * Retrieves, but does not remove, the element at the tail of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the element at the tail, or {@code null} if empty
     */
    public T peekLast() {
        return tail == null ? null : tail.value;
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
     * Returns a string representation of this deque in the form:
     *
     * <pre>
     * Head -> 1 <-> 2 <-> 3 <- Tail
     * </pre>
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
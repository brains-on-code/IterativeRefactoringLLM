package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A double-ended queue (deque) implementation backed by a doubly linked list.
 *
 * @param <T> the type of elements held in this deque
 */
public class Deque<T> {

    /**
     * Node of a doubly linked list used internally by the deque.
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
     * Inserts the specified element at the front (head) of this deque.
     *
     * @param value the element to add
     */
    public void addFirst(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            linkBeforeHead(newNode);
        }

        size++;
    }

    /**
     * Inserts the specified element at the back (tail) of this deque.
     *
     * @param value the element to add
     */
    public void addLast(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            linkAfterTail(newNode);
        }

        size++;
    }

    /**
     * Removes and returns the element at the front (head) of this deque.
     *
     * @return the element previously at the head
     * @throws NoSuchElementException if this deque is empty
     */
    public T pollFirst() {
        ensureNotEmpty();

        T value = head.value;

        if (head == tail) {
            clearDeque();
        } else {
            unlinkHead();
        }

        size--;
        return value;
    }

    /**
     * Removes and returns the element at the back (tail) of this deque.
     *
     * @return the element previously at the tail
     * @throws NoSuchElementException if this deque is empty
     */
    public T pollLast() {
        ensureNotEmpty();

        T value = tail.value;

        if (head == tail) {
            clearDeque();
        } else {
            unlinkTail();
        }

        size--;
        return value;
    }

    /**
     * Retrieves, but does not remove, the element at the front (head) of this deque,
     * or returns {@code null} if this deque is empty.
     *
     * @return the element at the head, or {@code null} if empty
     */
    public T peekFirst() {
        return head == null ? null : head.value;
    }

    /**
     * Retrieves, but does not remove, the element at the back (tail) of this deque,
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

    private void linkBeforeHead(DequeNode<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void linkAfterTail(DequeNode<T> newNode) {
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

    private void clearDeque() {
        head = null;
        tail = null;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
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
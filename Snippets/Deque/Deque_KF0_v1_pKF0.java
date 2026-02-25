package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A deque (double-ended queue) is a data structure based on a doubly linked
 * list that supports adding and removing elements from both the head and tail.
 *
 * @author [Ian Cowan](https://github.com/iccowan)
 */
public class Deque<T> {

    /**
     * Node for the deque.
     */
    private static class DequeNode<S> {
        final S value;
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
     * Adds the specified value to the head of the deque.
     *
     * @param value value to add to the deque
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
     * Adds the specified value to the tail of the deque.
     *
     * @param value value to add to the deque
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
     * Removes and returns the first (head) value in the deque.
     *
     * @return the value at the head of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollFirst() {
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
     * Removes and returns the last (tail) value in the deque.
     *
     * @return the value at the tail of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollLast() {
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
     * Returns the first (head) value of the deque without removing it.
     *
     * @return the value at the head of the deque, or null if empty
     */
    public T peekFirst() {
        return isEmpty() ? null : head.value;
    }

    /**
     * Returns the last (tail) value of the deque without removing it.
     *
     * @return the value at the tail of the deque, or null if empty
     */
    public T peekLast() {
        return isEmpty() ? null : tail.value;
    }

    /**
     * Returns the size of the deque.
     *
     * @return the size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether or not the deque is empty.
     *
     * @return true if the deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the deque.
     */
    private void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns a stringified deque in a pretty form:
     *
     * <p>
     * Head -> 1 <-> 2 <-> 3 <- Tail
     *
     * @return the stringified deque
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
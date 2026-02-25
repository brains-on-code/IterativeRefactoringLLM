package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A [deque](https://en.wikipedia.org/wiki/Double-ended_queue) is short for a
 * double ended queue pronounced "deck" and sometimes referred to as a head-tail
 * linked list. A deque is a data structure based on a doubly linked list, but
 * only supports adding and removal of nodes from the beginning and the end of
 * the list.
 *
 * @author [Ian Cowan](https://github.com/iccowan)
 */
public class Deque<T> {

    /**
     * Node for the deque
     */
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

    /**
     * Adds the specified value to the head of the deque
     *
     * @param value Value to add to the deque
     */
    public void addFirst(T value) {
        Node<T> newHead = new Node<>(value);

        if (isEmpty()) {
            head = newHead;
            tail = newHead;
        } else {
            newHead.next = head;
            head.previous = newHead;
            head = newHead;
        }

        size++;
    }

    /**
     * Adds the specified value to the tail of the deque
     *
     * @param value Value to add to the deque
     */
    public void addLast(T value) {
        Node<T> newTail = new Node<>(value);
        if (tail == null) {
            head = newTail;
            tail = newTail;
        } else {
            newTail.previous = tail;
            tail.next = newTail;
            tail = newTail;
        }
        size++;
    }

    /**
     * Removes and returns the first (head) value in the deque
     *
     * @return the value of the head of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = head.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<T> newHead = head.next;
            head.next = null;
            if (newHead != null) {
                newHead.previous = null;
            }
            head = newHead;
        }
        size--;
        return removedValue;
    }

    /**
     * Removes and returns the last (tail) value in the deque
     *
     * @return the value of the tail of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = tail.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<T> newTail = tail.previous;
            tail.previous = null;
            if (newTail != null) {
                newTail.next = null;
            }
            tail = newTail;
        }
        size--;
        return removedValue;
    }

    /**
     * Returns the first (head) value of the deque WITHOUT removing
     *
     * @return the value of the head of the deque, or null if empty
     */
    public T peekFirst() {
        return head != null ? head.value : null;
    }

    /**
     * Returns the last (tail) value of the deque WITHOUT removing
     *
     * @return the value of the tail of the deque, or null if empty
     */
    public T peekLast() {
        return tail != null ? tail.value : null;
    }

    /**
     * Returns the size of the deque
     *
     * @return the size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether or not the deque is empty
     *
     * @return whether or not the deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
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
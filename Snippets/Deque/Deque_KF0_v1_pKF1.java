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
    private static class DequeNode<E> {
        E value;
        DequeNode<E> next = null;
        DequeNode<E> previous = null;

        DequeNode(E value) {
            this.value = value;
        }
    }

    private DequeNode<T> head = null;
    private DequeNode<T> tail = null;
    private int size = 0;

    /**
     * Adds the specified value to the head of the deque
     *
     * @param value Value to add to the deque
     */
    public void addFirst(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);

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

    /**
     * Adds the specified value to the tail of the deque
     *
     * @param value Value to add to the deque
     */
    public void addLast(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);
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
            head = head.next;
            head.previous = null;
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
            tail = tail.previous;
            tail.next = null;
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
        StringBuilder dequeString = new StringBuilder("Head -> ");
        DequeNode<T> currentNode = head;
        while (currentNode != null) {
            dequeString.append(currentNode.value);
            if (currentNode.next != null) {
                dequeString.append(" <-> ");
            }
            currentNode = currentNode.next;
        }
        dequeString.append(" <- Tail");
        return dequeString.toString();
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

        int dequeSize = deque.size();
        for (int i = 0; i < dequeSize; i++) {
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
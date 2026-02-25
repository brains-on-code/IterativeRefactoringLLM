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
        E element;
        DequeNode<E> next = null;
        DequeNode<E> previous = null;

        DequeNode(E element) {
            this.element = element;
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
        DequeNode<T> newHeadNode = new DequeNode<>(value);

        if (isEmpty()) {
            head = newHeadNode;
            tail = newHeadNode;
        } else {
            newHeadNode.next = head;
            head.previous = newHeadNode;
            head = newHeadNode;
        }

        size++;
    }

    /**
     * Adds the specified value to the tail of the deque
     *
     * @param value Value to add to the deque
     */
    public void addLast(T value) {
        DequeNode<T> newTailNode = new DequeNode<>(value);
        if (tail == null) {
            head = newTailNode;
            tail = newTailNode;
        } else {
            newTailNode.previous = tail;
            tail.next = newTailNode;
            tail = newTailNode;
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

        T removedElement = head.element;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            DequeNode<T> newHead = head.next;
            head.next = null;
            if (newHead != null) {
                newHead.previous = null;
            }
            head = newHead;
        }
        size--;
        return removedElement;
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

        T removedElement = tail.element;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            DequeNode<T> newTail = tail.previous;
            tail.previous = null;
            if (newTail != null) {
                newTail.next = null;
            }
            tail = newTail;
        }
        size--;
        return removedElement;
    }

    /**
     * Returns the first (head) value of the deque WITHOUT removing
     *
     * @return the value of the head of the deque, or null if empty
     */
    public T peekFirst() {
        return head != null ? head.element : null;
    }

    /**
     * Returns the last (tail) value of the deque WITHOUT removing
     *
     * @return the value of the tail of the deque, or null if empty
     */
    public T peekLast() {
        return tail != null ? tail.element : null;
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
        StringBuilder dequeStringBuilder = new StringBuilder("Head -> ");
        DequeNode<T> currentNode = head;
        while (currentNode != null) {
            dequeStringBuilder.append(currentNode.element);
            if (currentNode.next != null) {
                dequeStringBuilder.append(" <-> ");
            }
            currentNode = currentNode.next;
        }
        dequeStringBuilder.append(" <- Tail");
        return dequeStringBuilder.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> integerDeque = new Deque<>();
        for (int index = 0; index < 42; index++) {
            if (index / 42.0 < 0.5) {
                integerDeque.addFirst(index);
            } else {
                integerDeque.addLast(index);
            }
        }

        System.out.println(integerDeque);
        System.out.println("Size: " + integerDeque.size());
        System.out.println();

        integerDeque.pollFirst();
        integerDeque.pollFirst();
        integerDeque.pollLast();
        System.out.println(integerDeque);
        System.out.println("Size: " + integerDeque.size());
        System.out.println();

        int currentDequeSize = integerDeque.size();
        for (int index = 0; index < currentDequeSize; index++) {
            int removedValue;
            if (index / 39.0 < 0.5) {
                removedValue = integerDeque.pollFirst();
            } else {
                removedValue = integerDeque.pollLast();
            }

            System.out.println("Removing: " + removedValue);
        }

        System.out.println(integerDeque);
        System.out.println(integerDeque.size());
    }
}
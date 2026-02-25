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
        E element;
        Node<E> next = null;
        Node<E> previous = null;

        Node(E element) {
            this.element = element;
        }
    }

    private Node<T> headNode = null;
    private Node<T> tailNode = null;
    private int elementCount = 0;

    /**
     * Adds the specified value to the head of the deque
     *
     * @param value Value to add to the deque
     */
    public void addFirst(T value) {
        Node<T> newHeadNode = new Node<>(value);

        if (isEmpty()) {
            headNode = newHeadNode;
            tailNode = newHeadNode;
        } else {
            newHeadNode.next = headNode;
            headNode.previous = newHeadNode;
            headNode = newHeadNode;
        }

        elementCount++;
    }

    /**
     * Adds the specified value to the tail of the deque
     *
     * @param value Value to add to the deque
     */
    public void addLast(T value) {
        Node<T> newTailNode = new Node<>(value);
        if (tailNode == null) {
            headNode = newTailNode;
            tailNode = newTailNode;
        } else {
            newTailNode.previous = tailNode;
            tailNode.next = newTailNode;
            tailNode = newTailNode;
        }
        elementCount++;
    }

    /**
     * Removes and returns the first (head) value in the deque
     *
     * @return the value of the head of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollFirst() {
        if (headNode == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = headNode.element;
        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            Node<T> newHeadNode = headNode.next;
            headNode.next = null;
            if (newHeadNode != null) {
                newHeadNode.previous = null;
            }
            headNode = newHeadNode;
        }
        elementCount--;
        return removedElement;
    }

    /**
     * Removes and returns the last (tail) value in the deque
     *
     * @return the value of the tail of the deque
     * @throws NoSuchElementException if the deque is empty
     */
    public T pollLast() {
        if (tailNode == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = tailNode.element;
        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            Node<T> newTailNode = tailNode.previous;
            tailNode.previous = null;
            if (newTailNode != null) {
                newTailNode.next = null;
            }
            tailNode = newTailNode;
        }
        elementCount--;
        return removedElement;
    }

    /**
     * Returns the first (head) value of the deque WITHOUT removing
     *
     * @return the value of the head of the deque, or null if empty
     */
    public T peekFirst() {
        return headNode != null ? headNode.element : null;
    }

    /**
     * Returns the last (tail) value of the deque WITHOUT removing
     *
     * @return the value of the tail of the deque, or null if empty
     */
    public T peekLast() {
        return tailNode != null ? tailNode.element : null;
    }

    /**
     * Returns the size of the deque
     *
     * @return the size of the deque
     */
    public int size() {
        return elementCount;
    }

    /**
     * Returns whether or not the deque is empty
     *
     * @return whether or not the deque is empty
     */
    public boolean isEmpty() {
        return elementCount == 0;
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
        Node<T> currentNode = headNode;
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

        int initialSize = integerDeque.size();
        for (int index = 0; index < initialSize; index++) {
            int removedElement;
            if (index / 39.0 < 0.5) {
                removedElement = integerDeque.pollFirst();
            } else {
                removedElement = integerDeque.pollLast();
            }

            System.out.println("Removing: " + removedElement);
        }

        System.out.println(integerDeque);
        System.out.println(integerDeque.size());
    }
}
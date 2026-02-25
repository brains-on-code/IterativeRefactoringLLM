package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

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

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.next = headNode;
            headNode.previous = newNode;
            headNode = newNode;
        }

        elementCount++;
    }

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tailNode == null) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.previous = tailNode;
            tailNode.next = newNode;
            tailNode = newNode;
        }
        elementCount++;
    }

    public T removeFirst() {
        if (headNode == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = headNode.element;
        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            headNode = headNode.next;
            headNode.previous = null;
        }
        elementCount--;
        return removedElement;
    }

    public T removeLast() {
        if (tailNode == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = tailNode.element;
        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            tailNode = tailNode.previous;
            tailNode.next = null;
        }
        elementCount--;
        return removedElement;
    }

    public T peekFirst() {
        return headNode != null ? headNode.element : null;
    }

    public T peekLast() {
        return tailNode != null ? tailNode.element : null;
    }

    public int size() {
        return elementCount;
    }

    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public String toString() {
        StringBuilder dequeRepresentation = new StringBuilder("Head -> ");
        Node<T> currentNode = headNode;
        while (currentNode != null) {
            dequeRepresentation.append(currentNode.element);
            if (currentNode.next != null) {
                dequeRepresentation.append(" <-> ");
            }
            currentNode = currentNode.next;
        }
        dequeRepresentation.append(" <- Tail");
        return dequeRepresentation.toString();
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

        integerDeque.removeFirst();
        integerDeque.removeFirst();
        integerDeque.removeLast();
        System.out.println(integerDeque);
        System.out.println("Size: " + integerDeque.size());
        System.out.println();

        int initialSize = integerDeque.size();
        for (int index = 0; index < initialSize; index++) {
            int removedElement;
            if (index / 39.0 < 0.5) {
                removedElement = integerDeque.removeFirst();
            } else {
                removedElement = integerDeque.removeLast();
            }

            System.out.println("Removing: " + removedElement);
        }

        System.out.println(integerDeque);
        System.out.println(integerDeque.size());
    }
}
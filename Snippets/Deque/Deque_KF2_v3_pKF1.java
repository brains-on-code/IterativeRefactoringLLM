package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class Node<E> {
        E value;
        Node<E> next = null;
        Node<E> previous = null;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<T> headNode = null;
    private Node<T> tailNode = null;
    private int elementCount = 0;

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

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

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.previous = tailNode;
            tailNode.next = newNode;
            tailNode = newNode;
        }

        elementCount++;
    }

    public T pollFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = headNode.value;

        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            headNode = headNode.next;
            headNode.previous = null;
        }

        elementCount--;
        return removedValue;
    }

    public T pollLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedValue = tailNode.value;

        if (headNode == tailNode) {
            headNode = null;
            tailNode = null;
        } else {
            tailNode = tailNode.previous;
            tailNode.next = null;
        }

        elementCount--;
        return removedValue;
    }

    public T peekFirst() {
        return headNode != null ? headNode.value : null;
    }

    public T peekLast() {
        return tailNode != null ? tailNode.value : null;
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
            dequeRepresentation.append(currentNode.value);
            if (currentNode.next != null) {
                dequeRepresentation.append(" <-> ");
            }
            currentNode = currentNode.next;
        }

        dequeRepresentation.append(" <- Tail");
        return dequeRepresentation.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        for (int index = 0; index < 42; index++) {
            if (index / 42.0 < 0.5) {
                deque.addFirst(index);
            } else {
                deque.addLast(index);
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
        for (int index = 0; index < initialSize; index++) {
            int removedValue;
            if (index / 39.0 < 0.5) {
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
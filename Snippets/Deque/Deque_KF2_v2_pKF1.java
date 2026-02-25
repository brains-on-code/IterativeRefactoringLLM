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

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);

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

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public T pollFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = head.element;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }

        size--;
        return removedElement;
    }

    public T pollLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        T removedElement = tail.element;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }

        size--;
        return removedElement;
    }

    public T peekFirst() {
        return head != null ? head.element : null;
    }

    public T peekLast() {
        return tail != null ? tail.element : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder dequeRepresentation = new StringBuilder("Head -> ");
        Node<T> currentNode = head;

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
            int removedElement;
            if (index / 39.0 < 0.5) {
                removedElement = deque.pollFirst();
            } else {
                removedElement = deque.pollLast();
            }

            System.out.println("Removing: " + removedElement);
        }

        System.out.println(deque);
        System.out.println(deque.size());
    }
}
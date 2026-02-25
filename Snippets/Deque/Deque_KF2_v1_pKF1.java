package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class DequeNode<S> {
        S value;
        DequeNode<S> next = null;
        DequeNode<S> previous = null;

        DequeNode(S value) {
            this.value = value;
        }
    }

    private DequeNode<T> head = null;
    private DequeNode<T> tail = null;
    private int size = 0;

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

    public T peekFirst() {
        return head != null ? head.value : null;
    }

    public T peekLast() {
        return tail != null ? tail.value : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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

        int initialSize = deque.size();
        for (int i = 0; i < initialSize; i++) {
            int removedElement;
            if (i / 39.0 < 0.5) {
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
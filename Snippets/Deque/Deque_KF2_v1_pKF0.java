package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

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

    public T peekFirst() {
        return isEmpty() ? null : head.value;
    }

    public T peekLast() {
        return isEmpty() ? null : tail.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void clear() {
        head = null;
        tail = null;
        size = 0;
    }

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
package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class DequeNode<S> {
        private final S value;
        private DequeNode<S> next;
        private DequeNode<S> prev;

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
            initializeSingleNodeDeque(newNode);
        } else {
            linkBeforeHead(newNode);
        }
        size++;
    }

    public void addLast(T value) {
        DequeNode<T> newNode = new DequeNode<>(value);
        if (isEmpty()) {
            initializeSingleNodeDeque(newNode);
        } else {
            linkAfterTail(newNode);
        }
        size++;
    }

    public T pollFirst() {
        ensureNotEmpty();
        T value = head.value;

        if (hasSingleElement()) {
            clear();
        } else {
            unlinkHead();
            size--;
        }

        return value;
    }

    public T pollLast() {
        ensureNotEmpty();
        T value = tail.value;

        if (hasSingleElement()) {
            clear();
        } else {
            unlinkTail();
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

    private boolean hasSingleElement() {
        return head == tail;
    }

    private void initializeSingleNodeDeque(DequeNode<T> node) {
        head = node;
        tail = node;
    }

    private void linkBeforeHead(DequeNode<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void linkAfterTail(DequeNode<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void unlinkHead() {
        DequeNode<T> newHead = head.next;
        head.next = null;
        if (newHead != null) {
            newHead.prev = null;
        }
        head = newHead;
    }

    private void unlinkTail() {
        DequeNode<T> newTail = tail.prev;
        tail.prev = null;
        if (newTail != null) {
            newTail.next = null;
        }
        tail = newTail;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
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
            int removed = (i < dequeSize / 2) ? myDeque.pollFirst() : myDeque.pollLast();
            System.out.println("Removing: " + removed);
        }

        System.out.println(myDeque);
        System.out.println(myDeque.size());
    }
}
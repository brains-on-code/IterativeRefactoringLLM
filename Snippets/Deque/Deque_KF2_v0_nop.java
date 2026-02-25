package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;


public class Deque<T> {


    private static class DequeNode<S> {
        S val;
        DequeNode<S> next = null;
        DequeNode<S> prev = null;

        DequeNode(S val) {
            this.val = val;
        }
    }

    private DequeNode<T> head = null;
    private DequeNode<T> tail = null;
    private int size = 0;


    public void addFirst(T val) {
        DequeNode<T> newNode = new DequeNode<>(val);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }


    public void addLast(T val) {
        DequeNode<T> newNode = new DequeNode<>(val);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }


    public T pollFirst() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T oldHeadVal = head.val;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return oldHeadVal;
    }


    public T pollLast() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T oldTailVal = tail.val;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return oldTailVal;
    }


    public T peekFirst() {
        return head != null ? head.val : null;
    }


    public T peekLast() {
        return tail != null ? tail.val : null;
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
        DequeNode<T> currNode = head;
        while (currNode != null) {
            dequeString.append(currNode.val);
            if (currNode.next != null) {
                dequeString.append(" <-> ");
            }
            currNode = currNode.next;
        }
        dequeString.append(" <- Tail");
        return dequeString.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> myDeque = new Deque<>();
        for (int i = 0; i < 42; i++) {
            if (i / 42.0 < 0.5) {
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
            int removing = -1;
            if (i / 39.0 < 0.5) {
                removing = myDeque.pollFirst();
            } else {
                removing = myDeque.pollLast();
            }

            System.out.println("Removing: " + removing);
        }

        System.out.println(myDeque);
        System.out.println(myDeque.size());
    }
}

package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

public class Deque<T> {

    private static class Node<E> {
        E element;
        Node<E> nextNode = null;
        Node<E> previousNode = null;

        Node(E element) {
            this.element = element;
        }
    }

    private Node<T> headNode = null;
    private Node<T> tailNode = null;
    private int numberOfElements = 0;

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);

        if (isEmpty()) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.nextNode = headNode;
            headNode.previousNode = newNode;
            headNode = newNode;
        }

        numberOfElements++;
    }

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tailNode == null) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.previousNode = tailNode;
            tailNode.nextNode = newNode;
            tailNode = newNode;
        }
        numberOfElements++;
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
            headNode = headNode.nextNode;
            headNode.previousNode = null;
        }
        numberOfElements--;
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
            tailNode = tailNode.previousNode;
            tailNode.nextNode = null;
        }
        numberOfElements--;
        return removedElement;
    }

    public T peekFirst() {
        return headNode != null ? headNode.element : null;
    }

    public T peekLast() {
        return tailNode != null ? tailNode.element : null;
    }

    public int size() {
        return numberOfElements;
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    @Override
    public String toString() {
        StringBuilder dequeStringBuilder = new StringBuilder("Head -> ");
        Node<T> currentNode = headNode;
        while (currentNode != null) {
            dequeStringBuilder.append(currentNode.element);
            if (currentNode.nextNode != null) {
                dequeStringBuilder.append(" <-> ");
            }
            currentNode = currentNode.nextNode;
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
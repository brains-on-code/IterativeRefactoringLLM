package com.thealgorithms.datastructures.queues;

import java.util.NoSuchElementException;

/**
 * A generic double-ended queue (deque) implementation using a doubly linked list.
 *
 * @param <T> the type of elements held in this deque
 */
public class Class1<T> {

    /**
     * Node of a doubly linked list.
     *
     * @param <S> the type of the value stored in the node
     */
    private static class Class2<S> {
        S value;
        Class2<S> next = null;
        Class2<S> prev = null;

        Class2(S value) {
            this.value = value;
        }
    }

    /** Reference to the head (front) of the deque. */
    private Class2<T> head = null;

    /** Reference to the tail (back) of the deque. */
    private Class2<T> tail = null;

    /** Number of elements in the deque. */
    private int size = 0;

    /**
     * Inserts an element at the front (head) of the deque.
     *
     * @param value the element to add
     */
    public void method1(T value) {
        Class2<T> newNode = new Class2<>(value);

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

    /**
     * Inserts an element at the back (tail) of the deque.
     *
     * @param value the element to add
     */
    public void method2(T value) {
        Class2<T> newNode = new Class2<>(value);
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

    /**
     * Removes and returns the element at the front (head) of the deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public T method3() {
        if (head == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = head.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return value;
    }

    /**
     * Removes and returns the element at the back (tail) of the deque.
     *
     * @return the removed element
     * @throws NoSuchElementException if the deque is empty
     */
    public T method4() {
        if (tail == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = tail.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return value;
    }

    /**
     * Returns the element at the front (head) of the deque without removing it.
     *
     * @return the front element, or {@code null} if the deque is empty
     */
    public T method5() {
        return head != null ? head.value : null;
    }

    /**
     * Returns the element at the back (tail) of the deque without removing it.
     *
     * @return the back element, or {@code null} if the deque is empty
     */
    public T method6() {
        return tail != null ? tail.value : null;
    }

    /**
     * Returns the number of elements in the deque.
     *
     * @return the size of the deque
     */
    public int method7() {
        return size;
    }

    /**
     * Returns {@code true} if the deque contains no elements.
     *
     * @return {@code true} if the deque is empty, {@code false} otherwise
     */
    public boolean method8() {
        return size == 0;
    }

    /**
     * Returns a string representation of the deque in the form:
     * {@code Head -> e1 <-> e2 <-> ... <- Tail}
     *
     * @return a string representation of this deque
     */
    @Override
    public String method9() {
        StringBuilder builder = new StringBuilder("Head -> ");
        Class2<T> current = head;
        while (current != null) {
            builder.append(current.value);
            if (current.next != null) {
                builder.append(" <-> ");
            }
            current = current.next;
        }
        builder.append(" <- Tail");
        return builder.toString();
    }

    public static void method10(String[] args) {
        Class1<Integer> deque = new Class1<>();
        for (int i = 0; i < 42; i++) {
            if (i / 42.0 < 0.5) {
                deque.method1(i);
            } else {
                deque.method2(i);
            }
        }

        System.out.println(deque);
        System.out.println("Size: " + deque.method7());
        System.out.println();

        deque.method3();
        deque.method3();
        deque.method4();
        System.out.println(deque);
        System.out.println("Size: " + deque.method7());
        System.out.println();

        int initialSize = deque.method7();
        for (int i = 0; i < initialSize; i++) {
            int removed;
            if (i / 39.0 < 0.5) {
                removed = deque.method3();
            } else {
                removed = deque.method4();
            }

            System.out.println("Removing: " + removed);
        }

        System.out.println(deque);
        System.out.println(deque.method7());
    }
}
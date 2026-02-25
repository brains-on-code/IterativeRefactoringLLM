package com.thealgorithms.datastructures.lists;

import java.util.Objects;

public class CursorLinkedList<T> {

    private static class Node<T> {
        T element;
        int next;

        Node(T element, int next) {
            this.element = element;
            this.next = next;
        }
    }

    private static final int CURSOR_SPACE_SIZE = 100;

    /** Index of the special node that manages the free list. */
    private final int os;

    /** Index of the first element in the list, or -1 if the list is empty. */
    private int head;

    /** Array-backed cursor space used to simulate linked list nodes. */
    private final Node<T>[] cursorSpace;

    /** Number of elements currently stored in the list. */
    private int count;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = 0;
    }

    public CursorLinkedList() {
        this.os = 0;
        this.count = 0;
        this.head = -1;
    }

    public void printList() {
        int currentIndex = head;
        while (currentIndex != -1) {
            System.out.println(cursorSpace[currentIndex].element);
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = head;
        for (int i = 0; i < count && currentIndex != -1; i++) {
            if (cursorSpace[currentIndex].element.equals(element)) {
                return i;
            }
            currentIndex = cursorSpace[currentIndex].next;
        }
        return -1;
    }

    public T get(int position) {
        if (position < 0 || position >= count) {
            return null;
        }

        int currentIndex = head;
        int currentPos = 0;

        while (currentIndex != -1) {
            if (currentPos == position) {
                return cursorSpace[currentIndex].element;
            }
            currentIndex = cursorSpace[currentIndex].next;
            currentPos++;
        }

        return null;
    }

    public void removeByIndex(int index) {
        if (index < 0 || index >= count) {
            return;
        }
        T element = get(index);
        if (element != null) {
            remove(element);
        }
    }

    public void remove(T element) {
        Objects.requireNonNull(element);

        if (head == -1) {
            return;
        }

        if (cursorSpace[head].element.equals(element)) {
            int nextIndex = cursorSpace[head].next;
            free(head);
            head = nextIndex;
            count--;
            return;
        }

        int prevIndex = head;
        int currentIndex = cursorSpace[prevIndex].next;

        while (currentIndex != -1) {
            if (cursorSpace[currentIndex].element.equals(element)) {
                cursorSpace[prevIndex].next = cursorSpace[currentIndex].next;
                free(currentIndex);
                count--;
                return;
            }
            prevIndex = currentIndex;
            currentIndex = cursorSpace[prevIndex].next;
        }
    }

    private int alloc() {
        int availableNodeIndex = cursorSpace[os].next;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError("Cursor space exhausted");
        }
        cursorSpace[os].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = -1;
        return availableNodeIndex;
    }

    private void free(int index) {
        int osNext = cursorSpace[os].next;
        cursorSpace[os].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = osNext;
    }

    public void append(T element) {
        Objects.requireNonNull(element);

        int newIndex = alloc();
        cursorSpace[newIndex].element = element;
        cursorSpace[newIndex].next = -1;

        if (head == -1) {
            head = newIndex;
        } else {
            int currentIndex = head;
            while (cursorSpace[currentIndex].next != -1) {
                currentIndex = cursorSpace[currentIndex].next;
            }
            cursorSpace[currentIndex].next = newIndex;
        }

        count++;
    }
}
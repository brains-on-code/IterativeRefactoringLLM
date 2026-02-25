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
    private static final int OS_INDEX = 0;
    private static final int NULL_INDEX = -1;
    private static final int NO_FREE_NODE = 0;

    private final Node<T>[] cursorSpace;
    private int head;
    private int count;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];
        initializeCursorSpace();
    }

    public CursorLinkedList() {
        this.head = NULL_INDEX;
        this.count = 0;
    }

    private void initializeCursorSpace() {
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = NO_FREE_NODE;
    }

    public void printList() {
        int currentIndex = head;
        while (currentIndex != NULL_INDEX) {
            System.out.println(cursorSpace[currentIndex].element);
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = head;
        int position = 0;

        while (currentIndex != NULL_INDEX && position < count) {
            if (element.equals(cursorSpace[currentIndex].element)) {
                return position;
            }
            currentIndex = cursorSpace[currentIndex].next;
            position++;
        }

        return -1;
    }

    public T get(int position) {
        if (!isValidPosition(position)) {
            return null;
        }

        int currentIndex = head;
        int currentPosition = 0;

        while (currentIndex != NULL_INDEX && currentPosition < position) {
            currentIndex = cursorSpace[currentIndex].next;
            currentPosition++;
        }

        return currentIndex == NULL_INDEX ? null : cursorSpace[currentIndex].element;
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position < count;
    }

    public void removeByIndex(int index) {
        if (!isValidPosition(index)) {
            return;
        }

        if (index == 0) {
            removeHead();
            return;
        }

        int previousIndex = head;
        int currentIndex = cursorSpace[previousIndex].next;
        int currentPosition = 1;

        while (currentIndex != NULL_INDEX && currentPosition < index) {
            previousIndex = currentIndex;
            currentIndex = cursorSpace[currentIndex].next;
            currentPosition++;
        }

        if (currentIndex != NULL_INDEX) {
            cursorSpace[previousIndex].next = cursorSpace[currentIndex].next;
            free(currentIndex);
            count--;
        }
    }

    public void remove(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        if (isEmpty()) {
            return;
        }

        if (element.equals(cursorSpace[head].element)) {
            removeHead();
            return;
        }

        removeNonHead(element);
    }

    private boolean isEmpty() {
        return head == NULL_INDEX;
    }

    private void removeHead() {
        int nextHead = cursorSpace[head].next;
        free(head);
        head = nextHead;
        count--;
    }

    private void removeNonHead(T element) {
        int previousIndex = head;
        int currentIndex = cursorSpace[previousIndex].next;

        while (currentIndex != NULL_INDEX) {
            if (element.equals(cursorSpace[currentIndex].element)) {
                cursorSpace[previousIndex].next = cursorSpace[currentIndex].next;
                free(currentIndex);
                count--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = cursorSpace[previousIndex].next;
        }
    }

    private int alloc() {
        int availableNodeIndex = cursorSpace[OS_INDEX].next;
        if (availableNodeIndex == NO_FREE_NODE) {
            throw new OutOfMemoryError("Cursor space exhausted");
        }
        cursorSpace[OS_INDEX].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = NULL_INDEX;
        return availableNodeIndex;
    }

    private void free(int index) {
        int osNext = cursorSpace[OS_INDEX].next;
        cursorSpace[OS_INDEX].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = osNext;
    }

    public void append(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int newIndex = alloc();
        cursorSpace[newIndex].element = element;
        cursorSpace[newIndex].next = NULL_INDEX;

        if (isEmpty()) {
            head = newIndex;
        } else {
            int lastIndex = findLastIndex();
            cursorSpace[lastIndex].next = newIndex;
        }

        count++;
    }

    private int findLastIndex() {
        int currentIndex = head;
        while (cursorSpace[currentIndex].next != NULL_INDEX) {
            currentIndex = cursorSpace[currentIndex].next;
        }
        return currentIndex;
    }
}
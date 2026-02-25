package com.thealgorithms.datastructures.lists;

import java.util.Objects;

public class CursorLinkedList<T> {

    private static class Node<T> {
        T value;
        int next;

        Node(T value, int next) {
            this.value = value;
            this.next = next;
        }
    }

    private static final int CURSOR_SPACE_CAPACITY = 100;

    private final int freeListHead;
    private int head;
    private final Node<T>[] cursorSpace;
    private int size;

    {
        cursorSpace = new Node[CURSOR_SPACE_CAPACITY];
        for (int i = 0; i < CURSOR_SPACE_CAPACITY; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_CAPACITY - 1].next = 0;
    }

    public CursorLinkedList() {
        freeListHead = 0;
        size = 0;
        head = -1;
    }

    public void printList() {
        if (head != -1) {
            int currentIndex = head;
            while (currentIndex != -1) {
                T value = cursorSpace[currentIndex].value;
                System.out.println(value.toString());
                currentIndex = cursorSpace[currentIndex].next;
            }
        }
    }

    public int indexOf(T target) {
        Objects.requireNonNull(target, "Element cannot be null");
        try {
            int currentIndex = head;
            for (int position = 0; position < size && currentIndex != -1; position++) {
                if (cursorSpace[currentIndex].value.equals(target)) {
                    return position;
                }
                currentIndex = cursorSpace[currentIndex].next;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            int currentIndex = head;
            int currentPosition = 0;
            while (currentIndex != -1) {
                T value = cursorSpace[currentIndex].value;
                if (currentPosition == index) {
                    return value;
                }
                currentIndex = cursorSpace[currentIndex].next;
                currentPosition++;
            }
        }
        return null;
    }

    public void removeByIndex(int index) {
        if (index >= 0 && index < size) {
            T value = get(index);
            remove(value);
        }
    }

    public void remove(T valueToRemove) {
        Objects.requireNonNull(valueToRemove);
        T headValue = cursorSpace[head].value;
        int headNext = cursorSpace[head].next;

        if (headValue.equals(valueToRemove)) {
            freeNode(head);
            head = headNext;
        } else {
            int previousIndex = head;
            int currentIndex = cursorSpace[previousIndex].next;
            while (currentIndex != -1) {
                T currentValue = cursorSpace[currentIndex].value;
                if (currentValue.equals(valueToRemove)) {
                    cursorSpace[previousIndex].next = cursorSpace[currentIndex].next;
                    freeNode(currentIndex);
                    break;
                }
                previousIndex = currentIndex;
                currentIndex = cursorSpace[previousIndex].next;
            }
        }
        size--;
    }

    private int allocateNode() {
        int availableIndex = cursorSpace[freeListHead].next;
        if (availableIndex == 0) {
            throw new OutOfMemoryError();
        }
        cursorSpace[freeListHead].next = cursorSpace[availableIndex].next;
        cursorSpace[availableIndex].next = -1;
        return availableIndex;
    }

    private void freeNode(int index) {
        Node<T> freeHeadNode = cursorSpace[freeListHead];
        int nextFreeIndex = freeHeadNode.next;
        cursorSpace[freeListHead].next = index;
        cursorSpace[index].value = null;
        cursorSpace[index].next = nextFreeIndex;
    }

    public void append(T value) {
        Objects.requireNonNull(value);
        int newNodeIndex = allocateNode();
        cursorSpace[newNodeIndex].value = value;

        if (head == -1) {
            head = newNodeIndex;
        } else {
            int currentIndex = head;
            while (cursorSpace[currentIndex].next != -1) {
                currentIndex = cursorSpace[currentIndex].next;
            }
            cursorSpace[currentIndex].next = newNodeIndex;
        }
        cursorSpace[newNodeIndex].next = -1;
        size++;
    }
}
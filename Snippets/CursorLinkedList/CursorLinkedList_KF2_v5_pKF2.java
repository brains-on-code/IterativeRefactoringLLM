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

    /** Index of the special node that manages the free list (never stores user data). */
    private final int freeListHeadIndex;

    /** Index of the first element in the list, or -1 if the list is empty. */
    private int headIndex;

    /** Array-backed cursor space used to simulate linked list nodes. */
    private final Node<T>[] cursorSpace;

    /** Number of elements currently stored in the list. */
    private int size;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];

        // Initialize all nodes and link them into a free list:
        // index 0 is the free-list header; indices 1..n-1 are free nodes.
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }

        // Last node points back to 0, which indicates "no free node".
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = 0;
    }

    public CursorLinkedList() {
        this.freeListHeadIndex = 0;
        this.size = 0;
        this.headIndex = -1;
    }

    public void printList() {
        int currentIndex = headIndex;
        while (currentIndex != -1) {
            System.out.println(cursorSpace[currentIndex].element);
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = headIndex;
        for (int i = 0; i < size && currentIndex != -1; i++) {
            if (cursorSpace[currentIndex].element.equals(element)) {
                return i;
            }
            currentIndex = cursorSpace[currentIndex].next;
        }
        return -1;
    }

    public T get(int position) {
        if (position < 0 || position >= size) {
            return null;
        }

        int currentIndex = headIndex;
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
        if (index < 0 || index >= size) {
            return;
        }
        T element = get(index);
        if (element != null) {
            remove(element);
        }
    }

    public void remove(T element) {
        Objects.requireNonNull(element);

        if (headIndex == -1) {
            return;
        }

        // Remove head node if it matches.
        if (cursorSpace[headIndex].element.equals(element)) {
            int nextIndex = cursorSpace[headIndex].next;
            free(headIndex);
            headIndex = nextIndex;
            size--;
            return;
        }

        // Traverse to find the node to remove, keeping track of the previous node.
        int prevIndex = headIndex;
        int currentIndex = cursorSpace[prevIndex].next;

        while (currentIndex != -1) {
            if (cursorSpace[currentIndex].element.equals(element)) {
                cursorSpace[prevIndex].next = cursorSpace[currentIndex].next;
                free(currentIndex);
                size--;
                return;
            }
            prevIndex = currentIndex;
            currentIndex = cursorSpace[prevIndex].next;
        }
    }

    /**
     * Allocates a node from the free list.
     *
     * @return index of the allocated node
     * @throws OutOfMemoryError if no free nodes remain
     */
    private int alloc() {
        int availableNodeIndex = cursorSpace[freeListHeadIndex].next;

        // availableNodeIndex == 0 means the free list is empty.
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError("Cursor space exhausted");
        }

        // Detach the node from the free list.
        cursorSpace[freeListHeadIndex].next = cursorSpace[availableNodeIndex].next;

        // Mark the node as not linked to any list.
        cursorSpace[availableNodeIndex].next = -1;

        return availableNodeIndex;
    }

    /**
     * Returns a node to the free list.
     *
     * @param index index of the node to free
     */
    private void free(int index) {
        int nextFreeIndex = cursorSpace[freeListHeadIndex].next;

        cursorSpace[freeListHeadIndex].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = nextFreeIndex;
    }

    public void append(T element) {
        Objects.requireNonNull(element);

        int newIndex = alloc();
        cursorSpace[newIndex].element = element;
        cursorSpace[newIndex].next = -1;

        if (headIndex == -1) {
            headIndex = newIndex;
        } else {
            int currentIndex = headIndex;
            while (cursorSpace[currentIndex].next != -1) {
                currentIndex = cursorSpace[currentIndex].next;
            }
            cursorSpace[currentIndex].next = newIndex;
        }

        size++;
    }
}
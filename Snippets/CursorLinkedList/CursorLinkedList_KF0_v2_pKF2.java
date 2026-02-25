package com.thealgorithms.datastructures.lists;

import java.util.Objects;

/**
 * Array-based implementation of a singly linked list using cursor indexing.
 *
 * @param <T> the type of elements in this list
 */
public class CursorLinkedList<T> {

    /**
     * Node stored in the cursor space.
     */
    private static class Node<T> {
        T element;
        int next;

        Node(T element, int next) {
            this.element = element;
            this.next = next;
        }
    }

    /** Index of the header node that manages the free list. */
    private final int os;

    /** Index of the first element in the logical list, -1 if empty. */
    private int head;

    /** Backing array that simulates the linked structure. */
    private final Node<T>[] cursorSpace;

    /** Number of elements currently stored in the list. */
    private int count;

    /** Total capacity of the cursor space. */
    private static final int CURSOR_SPACE_SIZE = 100;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = 0;
    }

    /**
     * Constructs an empty list with default capacity.
     */
    public CursorLinkedList() {
        os = 0;
        count = 0;
        head = -1;
    }

    /**
     * Prints all elements in the list in order.
     */
    public void printList() {
        int currentIndex = head;
        while (currentIndex != -1) {
            System.out.println(cursorSpace[currentIndex].element);
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    /**
     * Returns the logical index of the first occurrence of the specified element,
     * or -1 if the element is not present.
     *
     * @param element element to search for
     * @return index of the element, or -1 if not found
     * @throws NullPointerException if element is null
     */
    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = head;
        for (int logicalIndex = 0; logicalIndex < count && currentIndex != -1; logicalIndex++) {
            if (element.equals(cursorSpace[currentIndex].element)) {
                return logicalIndex;
            }
            currentIndex = cursorSpace[currentIndex].next;
        }
        return -1;
    }

    /**
     * Returns the element at the specified logical index, or null if the index
     * is out of bounds.
     *
     * @param position logical index of the element
     * @return element at the specified position, or null if index is invalid
     */
    public T get(int position) {
        if (position < 0 || position >= count) {
            return null;
        }

        int currentIndex = head;
        int logicalIndex = 0;
        while (currentIndex != -1) {
            if (logicalIndex == position) {
                return cursorSpace[currentIndex].element;
            }
            currentIndex = cursorSpace[currentIndex].next;
            logicalIndex++;
        }
        return null;
    }

    /**
     * Removes the element at the specified logical index, if it exists.
     *
     * @param index logical index of the element to remove
     */
    public void removeByIndex(int index) {
        if (index < 0 || index >= count) {
            return;
        }
        T element = get(index);
        if (element != null) {
            remove(element);
        }
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param element element to be removed
     * @throws NullPointerException if element is null
     */
    public void remove(T element) {
        Objects.requireNonNull(element);

        if (head == -1) {
            return;
        }

        if (element.equals(cursorSpace[head].element)) {
            int nextIndex = cursorSpace[head].next;
            free(head);
            head = nextIndex;
            count--;
            return;
        }

        int previousIndex = head;
        int currentIndex = cursorSpace[previousIndex].next;

        while (currentIndex != -1) {
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

    /**
     * Allocates a free node index from the cursor space.
     *
     * @return index of the allocated node
     * @throws OutOfMemoryError if no free node is available
     */
    private int alloc() {
        int availableNodeIndex = cursorSpace[os].next;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError("Cursor space exhausted");
        }
        cursorSpace[os].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = -1;
        return availableNodeIndex;
    }

    /**
     * Returns a node to the free list.
     *
     * @param index index of the node to free
     */
    private void free(int index) {
        int osNext = cursorSpace[os].next;
        cursorSpace[os].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = osNext;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element element to append
     * @throws NullPointerException if element is null
     */
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
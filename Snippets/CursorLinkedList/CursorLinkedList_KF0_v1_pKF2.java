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
        int current = head;
        while (current != -1) {
            System.out.println(cursorSpace[current].element);
            current = cursorSpace[current].next;
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

        int current = head;
        for (int i = 0; i < count && current != -1; i++) {
            if (element.equals(cursorSpace[current].element)) {
                return i;
            }
            current = cursorSpace[current].next;
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

        int current = head;
        int index = 0;
        while (current != -1) {
            if (index == position) {
                return cursorSpace[current].element;
            }
            current = cursorSpace[current].next;
            index++;
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

        // Handle removal of head
        if (element.equals(cursorSpace[head].element)) {
            int next = cursorSpace[head].next;
            free(head);
            head = next;
            count--;
            return;
        }

        int prevIndex = head;
        int currentIndex = cursorSpace[prevIndex].next;

        while (currentIndex != -1) {
            if (element.equals(cursorSpace[currentIndex].element)) {
                cursorSpace[prevIndex].next = cursorSpace[currentIndex].next;
                free(currentIndex);
                count--;
                return;
            }
            prevIndex = currentIndex;
            currentIndex = cursorSpace[prevIndex].next;
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
            int current = head;
            while (cursorSpace[current].next != -1) {
                current = cursorSpace[current].next;
            }
            cursorSpace[current].next = newIndex;
        }
        count++;
    }
}
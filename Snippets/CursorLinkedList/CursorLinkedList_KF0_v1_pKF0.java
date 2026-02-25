package com.thealgorithms.datastructures.lists;

import java.util.Objects;

/**
 * CursorLinkedList is an array-based implementation of a singly linked list.
 * Each node in the array simulates a linked list node, storing an element and
 * the index of the next node. This structure allows for efficient list operations
 * without relying on traditional pointers.
 *
 * @param <T> the type of elements in this list
 */
public class CursorLinkedList<T> {

    /**
     * Node represents an individual element in the list, containing the element
     * itself and a pointer (index) to the next node.
     */
    private static class Node<T> {
        T element;
        int next;

        Node(T element, int next) {
            this.element = element;
            this.next = next;
        }
    }

    private static final int CURSOR_SPACE_SIZE = 100;

    /** Index of the "os" node that manages the free list. */
    private final int os;

    /** Index of the head node of the logical list, -1 if empty. */
    private int head;

    /** Backing array simulating the cursor-based linked list. */
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

    /**
     * Constructs an empty CursorLinkedList with the default capacity.
     */
    public CursorLinkedList() {
        this.os = 0;
        this.count = 0;
        this.head = -1;
    }

    /**
     * Prints all elements in the list in their current order.
     */
    public void printList() {
        int currentIndex = head;
        while (currentIndex != -1) {
            T element = cursorSpace[currentIndex].element;
            System.out.println(element);
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    /**
     * Finds the logical index of a specified element in the list.
     *
     * @param element the element to search for in the list
     * @return the logical index of the element, or -1 if not found
     * @throws NullPointerException if element is null
     */
    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = head;
        int logicalIndex = 0;

        while (currentIndex != -1 && logicalIndex < count) {
            T currentElement = cursorSpace[currentIndex].element;
            if (element.equals(currentElement)) {
                return logicalIndex;
            }
            currentIndex = cursorSpace[currentIndex].next;
            logicalIndex++;
        }

        return -1;
    }

    /**
     * Retrieves an element at a specified logical index in the list.
     *
     * @param position the logical index of the element
     * @return the element at the specified position, or null if index is out of bounds
     */
    public T get(int position) {
        if (position < 0 || position >= count) {
            return null;
        }

        int currentIndex = head;
        int currentPosition = 0;

        while (currentIndex != -1) {
            if (currentPosition == position) {
                return cursorSpace[currentIndex].element;
            }
            currentIndex = cursorSpace[currentIndex].next;
            currentPosition++;
        }

        return null;
    }

    /**
     * Removes the element at a specified logical index from the list.
     *
     * @param index the logical index of the element to remove
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
     * Removes a specified element from the list.
     *
     * @param element the element to be removed
     * @throws NullPointerException if element is null
     */
    public void remove(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        if (head == -1) {
            return;
        }

        int currentIndex = head;
        int previousIndex = -1;

        while (currentIndex != -1) {
            T currentElement = cursorSpace[currentIndex].element;
            if (element.equals(currentElement)) {
                if (previousIndex == -1) {
                    // Removing head
                    head = cursorSpace[currentIndex].next;
                } else {
                    cursorSpace[previousIndex].next = cursorSpace[currentIndex].next;
                }
                free(currentIndex);
                count--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    /**
     * Allocates a new node index for storing an element.
     *
     * @return the index of the newly allocated node
     * @throws OutOfMemoryError if no space is available in cursor space
     */
    private int alloc() {
        int availableNodeIndex = cursorSpace[os].next;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError("No space available in cursor space");
        }
        cursorSpace[os].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = -1;
        return availableNodeIndex;
    }

    /**
     * Releases a node back to the free list.
     *
     * @param index the index of the node to release
     */
    private void free(int index) {
        int osNext = cursorSpace[os].next;
        cursorSpace[os].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = osNext;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param element the element to append
     * @throws NullPointerException if element is null
     */
    public void append(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

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
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
    private static final int NULL_INDEX = -1;
    private static final int OS_INDEX = 0;

    /** Index of the head node of the logical list, -1 if empty. */
    private int head;

    /** Backing array simulating the cursor-based linked list. */
    private final Node<T>[] cursorSpace;

    /** Number of elements currently stored in the list. */
    private int count;

    @SuppressWarnings("unchecked")
    public CursorLinkedList() {
        this.cursorSpace = new Node[CURSOR_SPACE_SIZE];
        initializeCursorSpace();
        this.head = NULL_INDEX;
        this.count = 0;
    }

    private void initializeCursorSpace() {
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = OS_INDEX;
    }

    /**
     * Prints all elements in the list in their current order.
     */
    public void printList() {
        int currentIndex = head;
        while (currentIndex != NULL_INDEX) {
            System.out.println(cursorSpace[currentIndex].element);
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

        while (currentIndex != NULL_INDEX && logicalIndex < count) {
            if (element.equals(cursorSpace[currentIndex].element)) {
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
        if (!isValidIndex(position)) {
            return null;
        }

        int currentIndex = head;
        int currentPosition = 0;

        while (currentIndex != NULL_INDEX) {
            if (currentPosition == position) {
                return cursorSpace[currentIndex].element;
            }
            currentIndex = cursorSpace[currentIndex].next;
            currentPosition++;
        }

        return null;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < count;
    }

    /**
     * Removes the element at a specified logical index from the list.
     *
     * @param index the logical index of the element to remove
     */
    public void removeByIndex(int index) {
        if (!isValidIndex(index)) {
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

        if (head == NULL_INDEX) {
            return;
        }

        int currentIndex = head;
        int previousIndex = NULL_INDEX;

        while (currentIndex != NULL_INDEX) {
            if (element.equals(cursorSpace[currentIndex].element)) {
                unlinkNode(previousIndex, currentIndex);
                free(currentIndex);
                count--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = cursorSpace[currentIndex].next;
        }
    }

    private void unlinkNode(int previousIndex, int currentIndex) {
        if (previousIndex == NULL_INDEX) {
            head = cursorSpace[currentIndex].next;
        } else {
            cursorSpace[previousIndex].next = cursorSpace[currentIndex].next;
        }
    }

    /**
     * Allocates a new node index for storing an element.
     *
     * @return the index of the newly allocated node
     * @throws OutOfMemoryError if no space is available in cursor space
     */
    private int alloc() {
        int availableNodeIndex = cursorSpace[OS_INDEX].next;
        if (availableNodeIndex == OS_INDEX) {
            throw new OutOfMemoryError("No space available in cursor space");
        }
        cursorSpace[OS_INDEX].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = NULL_INDEX;
        return availableNodeIndex;
    }

    /**
     * Releases a node back to the free list.
     *
     * @param index the index of the node to release
     */
    private void free(int index) {
        int osNext = cursorSpace[OS_INDEX].next;
        cursorSpace[OS_INDEX].next = index;
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
        cursorSpace[newIndex].next = NULL_INDEX;

        if (head == NULL_INDEX) {
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
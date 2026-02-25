package com.thealgorithms.datastructures.lists;

import java.util.Objects;

/**
 * A simple fixed-capacity singly linked list backed by an array and a free-list.
 *
 * @param <T> the type of elements stored in the list
 */
public class Class1<T> {

    /**
     * Node structure used internally by the list.
     */
    private static class Node<T> {
        T value;
        int nextIndex;

        Node(T value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    /** Index of the free-list head sentinel. */
    private final int freeListHeadIndex;

    /** Index of the first element in the list, or -1 if the list is empty. */
    private int headIndex;

    /** Backing array of nodes. */
    private final Node<T>[] nodes;

    /** Current number of elements in the list. */
    private int size;

    /** Maximum capacity of the list. */
    private static final int CAPACITY = 100;

    {
        // Initialize free list: nodes[0] is the free-list head sentinel.
        nodes = new Node[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        // Last node in the free list points to 0 (end of free list).
        nodes[CAPACITY - 1].nextIndex = 0;
    }

    /**
     * Constructs an empty list.
     */
    public Class1() {
        freeListHeadIndex = 0;
        size = 0;
        headIndex = -1;
    }

    /**
     * Prints all elements in the list to standard output.
     */
    public void printAll() {
        if (headIndex == -1) {
            return;
        }
        int currentIndex = headIndex;
        while (currentIndex != -1) {
            T value = nodes[currentIndex].value;
            System.out.println(value.toString());
            currentIndex = nodes[currentIndex].nextIndex;
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element,
     * or -1 if the element is not found.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if not found
     */
    public int indexOf(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        try {
            Objects.requireNonNull(element);
            Node<T> current = nodes[headIndex];
            for (int i = 0; i < size; i++) {
                if (current.value.equals(element)) {
                    return i;
                }
                current = nodes[current.nextIndex];
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    /**
     * Returns the element at the specified logical index in the list,
     * or null if the index is out of bounds.
     *
     * @param index the logical index (0-based)
     * @return the element at the given index, or null if out of bounds
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = headIndex;
        int currentPosition = 0;
        while (currentIndex != -1) {
            T value = nodes[currentIndex].value;
            if (currentPosition == index) {
                return value;
            }
            currentIndex = nodes[currentIndex].nextIndex;
            currentPosition++;
        }
        return null;
    }

    /**
     * Removes the element at the specified logical index, if it exists.
     *
     * @param index the logical index (0-based)
     */
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        T value = get(index);
        remove(value);
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param element the element to remove
     */
    public void remove(T element) {
        Objects.requireNonNull(element);

        T headValue = nodes[headIndex].value;
        int headNext = nodes[headIndex].nextIndex;

        if (headValue.equals(element)) {
            releaseNode(headIndex);
            headIndex = headNext;
        } else {
            int previousIndex = headIndex;
            int currentIndex = nodes[previousIndex].nextIndex;

            while (currentIndex != -1) {
                T currentValue = nodes[currentIndex].value;
                if (currentValue.equals(element)) {
                    nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
                    releaseNode(currentIndex);
                    break;
                }
                previousIndex = currentIndex;
                currentIndex = nodes[previousIndex].nextIndex;
            }
        }
        size--;
    }

    /**
     * Allocates a node from the free list and returns its index.
     *
     * @return index of the allocated node
     * @throws OutOfMemoryError if no free nodes are available
     */
    private int allocateNode() {
        int freeIndex = nodes[freeListHeadIndex].nextIndex;
        if (freeIndex == 0) {
            throw new OutOfMemoryError();
        }
        nodes[freeListHeadIndex].nextIndex = nodes[freeIndex].nextIndex;
        nodes[freeIndex].nextIndex = -1;
        return freeIndex;
    }

    /**
     * Releases a node back to the free list.
     *
     * @param index index of the node to release
     */
    private void releaseNode(int index) {
        Node<T> freeHead = nodes[freeListHeadIndex];
        int oldFirstFree = freeHead.nextIndex;
        nodes[freeListHeadIndex].nextIndex = index;
        nodes[index].value = null;
        nodes[index].nextIndex = oldFirstFree;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element the element to add
     */
    public void add(T element) {
        Objects.requireNonNull(element);

        int newIndex = allocateNode();
        nodes[newIndex].value = element;

        if (headIndex == -1) {
            headIndex = newIndex;
        } else {
            int currentIndex = headIndex;
            while (nodes[currentIndex].nextIndex != -1) {
                currentIndex = nodes[currentIndex].nextIndex;
            }
            nodes[currentIndex].nextIndex = newIndex;
        }
        nodes[newIndex].nextIndex = -1;
        size++;
    }
}
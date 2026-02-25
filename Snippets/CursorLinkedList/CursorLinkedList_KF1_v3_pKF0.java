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
        nodes = new Node[CAPACITY];
        initializeFreeList();
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
        int currentIndex = headIndex;
        while (currentIndex != -1) {
            System.out.println(nodes[currentIndex].value);
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
        Objects.requireNonNull(element, "Element cannot be null");

        int currentIndex = headIndex;
        int logicalIndex = 0;

        while (currentIndex != -1 && logicalIndex < size) {
            if (Objects.equals(nodes[currentIndex].value, element)) {
                return logicalIndex;
            }
            currentIndex = nodes[currentIndex].nextIndex;
            logicalIndex++;
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
        if (!isValidIndex(index)) {
            return null;
        }

        int nodeIndex = getNodeIndexAt(index);
        return nodeIndex == -1 ? null : nodes[nodeIndex].value;
    }

    /**
     * Removes the element at the specified logical index, if it exists.
     *
     * @param index the logical index (0-based)
     */
    public void removeAt(int index) {
        if (!isValidIndex(index)) {
            return;
        }

        if (index == 0) {
            removeHead();
            return;
        }

        int previousIndex = getNodeIndexAt(index - 1);
        if (previousIndex == -1) {
            return;
        }

        int currentIndex = nodes[previousIndex].nextIndex;
        if (currentIndex == -1) {
            return;
        }

        nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
        releaseNode(currentIndex);
        size--;
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param element the element to remove
     */
    public void remove(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        if (headIndex == -1) {
            return;
        }

        if (Objects.equals(nodes[headIndex].value, element)) {
            removeHead();
            return;
        }

        int previousIndex = headIndex;
        int currentIndex = nodes[previousIndex].nextIndex;

        while (currentIndex != -1) {
            if (Objects.equals(nodes[currentIndex].value, element)) {
                nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
                releaseNode(currentIndex);
                size--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = nodes[previousIndex].nextIndex;
        }
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element the element to add
     */
    public void add(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        int newIndex = allocateNode();
        nodes[newIndex].value = element;
        nodes[newIndex].nextIndex = -1;

        if (headIndex == -1) {
            headIndex = newIndex;
        } else {
            int tailIndex = getTailIndex();
            nodes[tailIndex].nextIndex = newIndex;
        }

        size++;
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
            throw new OutOfMemoryError("No free nodes available");
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

        nodes[index].value = null;
        nodes[index].nextIndex = oldFirstFree;
        freeHead.nextIndex = index;
    }

    private void initializeFreeList() {
        for (int i = 0; i < CAPACITY; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        nodes[CAPACITY - 1].nextIndex = 0;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size;
    }

    private void removeHead() {
        int oldHeadIndex = headIndex;
        headIndex = nodes[headIndex].nextIndex;
        releaseNode(oldHeadIndex);
        size--;
    }

    private int getNodeIndexAt(int index) {
        if (!isValidIndex(index)) {
            return -1;
        }

        int currentIndex = headIndex;
        int currentPosition = 0;

        while (currentIndex != -1 && currentPosition < index) {
            currentIndex = nodes[currentIndex].nextIndex;
            currentPosition++;
        }

        return currentIndex;
    }

    private int getTailIndex() {
        int currentIndex = headIndex;
        while (nodes[currentIndex].nextIndex != -1) {
            currentIndex = nodes[currentIndex].nextIndex;
        }
        return currentIndex;
    }
}
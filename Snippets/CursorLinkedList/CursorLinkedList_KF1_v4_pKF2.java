package com.thealgorithms.datastructures.lists;

import java.util.Objects;

/**
 * A singly linked list backed by a fixed-size array and a free-list allocator.
 *
 * @param <T> the type of elements stored in the list
 */
public class Class1<T> {

    private static final int CAPACITY = 100;
    private static final int NULL_INDEX = -1;
    private static final int FREE_LIST_HEAD_INDEX = 0;

    /**
     * Internal node representation.
     */
    private static class Node<T> {
        T value;
        int nextIndex;

        Node(T value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    /** Index of the first element in the list, or -1 if the list is empty. */
    private int headIndex;

    /** Backing array of nodes. */
    private final Node<T>[] nodes;

    /** Current number of elements in the list. */
    private int size;

    {
        nodes = new Node[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        nodes[CAPACITY - 1].nextIndex = FREE_LIST_HEAD_INDEX;
    }

    /** Constructs an empty list. */
    public Class1() {
        size = 0;
        headIndex = NULL_INDEX;
    }

    /** Prints all elements in the list in order, one per line. */
    public void printAll() {
        int currentIndex = headIndex;
        while (currentIndex != NULL_INDEX) {
            System.out.println(nodes[currentIndex].value.toString());
            currentIndex = nodes[currentIndex].nextIndex;
        }
    }

    /**
     * Returns the index (0-based) of the first occurrence of the given element,
     * or -1 if the element is not found.
     *
     * @param element the element to search for (must not be null)
     * @return index of the element, or -1 if not found
     */
    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");

        if (headIndex == NULL_INDEX) {
            return -1;
        }

        int currentIndex = headIndex;
        for (int i = 0; i < size && currentIndex != NULL_INDEX; i++) {
            if (Objects.equals(nodes[currentIndex].value, element)) {
                return i;
            }
            currentIndex = nodes[currentIndex].nextIndex;
        }
        return -1;
    }

    /**
     * Returns the element at the specified position in the list, or {@code null}
     * if the index is out of range.
     *
     * @param index position of the element to return (0-based)
     * @return the element at the specified position, or {@code null} if invalid
     */
    public T get(int index) {
        if (index < 0 || index >= size || headIndex == NULL_INDEX) {
            return null;
        }

        int currentIndex = headIndex;
        int currentPosition = 0;

        while (currentIndex != NULL_INDEX) {
            if (currentPosition == index) {
                return nodes[currentIndex].value;
            }
            currentIndex = nodes[currentIndex].nextIndex;
            currentPosition++;
        }

        return null;
    }

    /**
     * Removes the element at the specified position in the list, if the index is valid.
     *
     * @param index position of the element to remove (0-based)
     */
    public void removeAt(int index) {
        if (index < 0 || index >= size || headIndex == NULL_INDEX) {
            return;
        }

        if (index == 0) {
            int oldHead = headIndex;
            headIndex = nodes[headIndex].nextIndex;
            freeNode(oldHead);
            size--;
            return;
        }

        int previousIndex = headIndex;
        int currentIndex = nodes[previousIndex].nextIndex;
        int currentPosition = 1;

        while (currentIndex != NULL_INDEX) {
            if (currentPosition == index) {
                nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
                freeNode(currentIndex);
                size--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = nodes[currentIndex].nextIndex;
            currentPosition++;
        }
    }

    /**
     * Removes the first occurrence of the specified element from the list, if present.
     *
     * @param element element to be removed from the list (must not be null)
     */
    public void remove(T element) {
        Objects.requireNonNull(element);

        if (headIndex == NULL_INDEX) {
            return;
        }

        if (Objects.equals(nodes[headIndex].value, element)) {
            int oldHead = headIndex;
            headIndex = nodes[headIndex].nextIndex;
            freeNode(oldHead);
            size--;
            return;
        }

        int previousIndex = headIndex;
        int currentIndex = nodes[previousIndex].nextIndex;

        while (currentIndex != NULL_INDEX) {
            if (Objects.equals(nodes[currentIndex].value, element)) {
                nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
                freeNode(currentIndex);
                size--;
                return;
            }
            previousIndex = currentIndex;
            currentIndex = nodes[currentIndex].nextIndex;
        }
    }

    /**
     * Allocates a node from the free list and returns its index.
     *
     * @return index of the allocated node
     * @throws OutOfMemoryError if no free nodes are available
     */
    private int allocateNode() {
        int freeIndex = nodes[FREE_LIST_HEAD_INDEX].nextIndex;
        if (freeIndex == FREE_LIST_HEAD_INDEX) {
            throw new OutOfMemoryError("No free nodes available");
        }
        nodes[FREE_LIST_HEAD_INDEX].nextIndex = nodes[freeIndex].nextIndex;
        nodes[freeIndex].nextIndex = NULL_INDEX;
        return freeIndex;
    }

    /**
     * Returns a node to the free list.
     *
     * @param index index of the node to free
     */
    private void freeNode(int index) {
        int oldFirstFree = nodes[FREE_LIST_HEAD_INDEX].nextIndex;
        nodes[FREE_LIST_HEAD_INDEX].nextIndex = index;
        nodes[index].value = null;
        nodes[index].nextIndex = oldFirstFree;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param element element to be appended to this list (must not be null)
     */
    public void add(T element) {
        Objects.requireNonNull(element);
        int newIndex = allocateNode();
        nodes[newIndex].value = element;
        nodes[newIndex].nextIndex = NULL_INDEX;

        if (headIndex == NULL_INDEX) {
            headIndex = newIndex;
        } else {
            int currentIndex = headIndex;
            while (nodes[currentIndex].nextIndex != NULL_INDEX) {
                currentIndex = nodes[currentIndex].nextIndex;
            }
            nodes[currentIndex].nextIndex = newIndex;
        }
        size++;
    }
}
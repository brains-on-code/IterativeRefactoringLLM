package com.thealgorithms.datastructures.lists;

import java.util.Objects;

/**
 * A simple singly linked list implementation backed by a fixed-size array and a
 * free-list allocator.
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

    /** Index of the head of the free list (always 0). */
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
        // Initialize free list: nodes[0] is the free-list head; nodes[1..CAPACITY-1] are free.
        nodes = new Node[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        // Last free node points to 0 (end of free list).
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
     * Prints all elements in the list in order, one per line.
     */
    public void printAll() {
        if (headIndex != -1) {
            int currentIndex = headIndex;
            while (currentIndex != -1) {
                T value = nodes[currentIndex].value;
                System.out.println(value.toString());
                currentIndex = nodes[currentIndex].nextIndex;
            }
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
     * Returns the element at the specified position in the list, or {@code null}
     * if the index is out of range.
     *
     * @param index position of the element to return (0-based)
     * @return the element at the specified position, or {@code null} if invalid
     */
    public T get(int index) {
        if (index >= 0 && index < size) {
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
        }
        return null;
    }

    /**
     * Removes the element at the specified position in the list, if the index is valid.
     *
     * @param index position of the element to remove (0-based)
     */
    public void removeAt(int index) {
        if (index >= 0 && index < size) {
            T value = get(index);
            remove(value);
        }
    }

    /**
     * Removes the first occurrence of the specified element from the list, if present.
     *
     * @param element element to be removed from the list (must not be null)
     */
    public void remove(T element) {
        Objects.requireNonNull(element);
        T headValue = nodes[headIndex].value;
        int headNext = nodes[headIndex].nextIndex;

        // If the head node contains the element to remove.
        if (headValue.equals(element)) {
            freeNode(headIndex);
            headIndex = headNext;
        } else {
            int previousIndex = headIndex;
            int currentIndex = nodes[previousIndex].nextIndex;
            while (currentIndex != -1) {
                T currentValue = nodes[currentIndex].value;
                if (currentValue.equals(element)) {
                    nodes[previousIndex].nextIndex = nodes[currentIndex].nextIndex;
                    freeNode(currentIndex);
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
     * Returns a node to the free list.
     *
     * @param index index of the node to free
     */
    private void freeNode(int index) {
        Node<T> freeHead = nodes[freeListHeadIndex];
        int oldFirstFree = freeHead.nextIndex;
        nodes[freeListHeadIndex].nextIndex = index;
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
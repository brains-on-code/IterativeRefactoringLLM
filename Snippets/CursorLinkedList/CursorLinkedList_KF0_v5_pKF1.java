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
        T value;
        int nextIndex;

        Node(T value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    private static final int DEFAULT_CAPACITY = 100;

    /** Index of the header node that manages the free list. */
    private final int freeListHeadIndex;

    /** Index of the first element node in the list; -1 if the list is empty. */
    private int headIndex;

    /** Underlying array simulating the linked list nodes. */
    private final Node<T>[] nodes;

    /** Number of elements currently stored in the list. */
    private int size;

    {
        nodes = new Node[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        nodes[DEFAULT_CAPACITY - 1].nextIndex = 0;
    }

    /**
     * Constructs an empty CursorLinkedList with the default capacity.
     */
    public CursorLinkedList() {
        freeListHeadIndex = 0;
        size = 0;
        headIndex = -1;
    }

    /**
     * Prints all elements in the list in their current order.
     */
    public void printList() {
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
     * Finds the logical index of a specified element in the list.
     *
     * @param element the element to search for in the list
     * @return the logical index of the element, or -1 if not found
     * @throws NullPointerException if element is null
     */
    public int indexOf(T element) {
        Objects.requireNonNull(element, "Element cannot be null");
        int currentIndex = headIndex;
        for (int logicalIndex = 0; logicalIndex < size && currentIndex != -1; logicalIndex++) {
            if (nodes[currentIndex].value.equals(element)) {
                return logicalIndex;
            }
            currentIndex = nodes[currentIndex].nextIndex;
        }
        return -1;
    }

    /**
     * Retrieves an element at a specified logical index in the list.
     *
     * @param index the logical index of the element
     * @return the element at the specified index, or null if index is out of bounds
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = headIndex;
        int currentPosition = 0;
        while (currentIndex != -1) {
            if (currentPosition == index) {
                return nodes[currentIndex].value;
            }
            currentIndex = nodes[currentIndex].nextIndex;
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
        if (index < 0 || index >= size) {
            return;
        }
        T element = get(index);
        remove(element);
    }

    /**
     * Removes a specified element from the list.
     *
     * @param element the element to be removed
     * @throws NullPointerException if element is null
     */
    public void remove(T element) {
        Objects.requireNonNull(element);
        T headValue = nodes[headIndex].value;
        int headNextIndex = nodes[headIndex].nextIndex;

        if (headValue.equals(element)) {
            freeNode(headIndex);
            headIndex = headNextIndex;
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
     * Allocates a new node index for storing an element.
     *
     * @return the index of the newly allocated node
     * @throws OutOfMemoryError if no space is available in cursor space
     */
    private int allocateNode() {
        int availableIndex = nodes[freeListHeadIndex].nextIndex;
        if (availableIndex == 0) {
            throw new OutOfMemoryError();
        }
        nodes[freeListHeadIndex].nextIndex = nodes[availableIndex].nextIndex;
        nodes[availableIndex].nextIndex = -1;
        return availableIndex;
    }

    /**
     * Releases a node back to the free list.
     *
     * @param index the index of the node to release
     */
    private void freeNode(int index) {
        Node<T> freeListHeadNode = nodes[freeListHeadIndex];
        int nextFreeIndex = freeListHeadNode.nextIndex;
        nodes[freeListHeadIndex].nextIndex = index;
        nodes[index].value = null;
        nodes[index].nextIndex = nextFreeIndex;
    }

    /**
     * Appends an element to the end of the list.
     *
     * @param element the element to append
     * @throws NullPointerException if element is null
     */
    public void append(T element) {
        Objects.requireNonNull(element);
        int newNodeIndex = allocateNode();
        nodes[newNodeIndex].value = element;

        if (headIndex == -1) {
            headIndex = newNodeIndex;
        } else {
            int currentIndex = headIndex;
            while (nodes[currentIndex].nextIndex != -1) {
                currentIndex = nodes[currentIndex].nextIndex;
            }
            nodes[currentIndex].nextIndex = newNodeIndex;
        }
        nodes[newNodeIndex].nextIndex = -1;
        size++;
    }
}
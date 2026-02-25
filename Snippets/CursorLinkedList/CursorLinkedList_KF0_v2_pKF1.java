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
        int nextIndex;

        Node(T element, int nextIndex) {
            this.element = element;
            this.nextIndex = nextIndex;
        }
    }

    private static final int CURSOR_SPACE_SIZE = 100;

    /** Index of the header node that manages the free list. */
    private final int freeListHeadIndex;

    /** Index of the first element node in the list; -1 if the list is empty. */
    private int listHeadIndex;

    /** Underlying array simulating the linked list nodes. */
    private final Node<T>[] nodes;

    /** Number of elements currently stored in the list. */
    private int size;

    {
        nodes = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            nodes[i] = new Node<>(null, i + 1);
        }
        nodes[CURSOR_SPACE_SIZE - 1].nextIndex = 0;
    }

    /**
     * Constructs an empty CursorLinkedList with the default capacity.
     */
    public CursorLinkedList() {
        freeListHeadIndex = 0;
        size = 0;
        listHeadIndex = -1;
    }

    /**
     * Prints all elements in the list in their current order.
     */
    public void printList() {
        if (listHeadIndex == -1) {
            return;
        }
        int currentNodeIndex = listHeadIndex;
        while (currentNodeIndex != -1) {
            T element = nodes[currentNodeIndex].element;
            System.out.println(element.toString());
            currentNodeIndex = nodes[currentNodeIndex].nextIndex;
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
        try {
            int currentNodeIndex = listHeadIndex;
            for (int logicalIndex = 0; logicalIndex < size && currentNodeIndex != -1; logicalIndex++) {
                if (nodes[currentNodeIndex].element.equals(element)) {
                    return logicalIndex;
                }
                currentNodeIndex = nodes[currentNodeIndex].nextIndex;
            }
        } catch (Exception e) {
            return -1;
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
        if (position < 0 || position >= size) {
            return null;
        }
        int currentNodeIndex = listHeadIndex;
        int currentPosition = 0;
        while (currentNodeIndex != -1) {
            if (currentPosition == position) {
                return nodes[currentNodeIndex].element;
            }
            currentNodeIndex = nodes[currentNodeIndex].nextIndex;
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
        T headElement = nodes[listHeadIndex].element;
        int headNextIndex = nodes[listHeadIndex].nextIndex;

        if (headElement.equals(element)) {
            freeNode(listHeadIndex);
            listHeadIndex = headNextIndex;
        } else {
            int previousNodeIndex = listHeadIndex;
            int currentNodeIndex = nodes[previousNodeIndex].nextIndex;
            while (currentNodeIndex != -1) {
                T currentElement = nodes[currentNodeIndex].element;
                if (currentElement.equals(element)) {
                    nodes[previousNodeIndex].nextIndex = nodes[currentNodeIndex].nextIndex;
                    freeNode(currentNodeIndex);
                    break;
                }
                previousNodeIndex = currentNodeIndex;
                currentNodeIndex = nodes[previousNodeIndex].nextIndex;
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
        int availableNodeIndex = nodes[freeListHeadIndex].nextIndex;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError();
        }
        nodes[freeListHeadIndex].nextIndex = nodes[availableNodeIndex].nextIndex;
        nodes[availableNodeIndex].nextIndex = -1;
        return availableNodeIndex;
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
        nodes[index].element = null;
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
        nodes[newNodeIndex].element = element;

        if (listHeadIndex == -1) {
            listHeadIndex = newNodeIndex;
        } else {
            int currentNodeIndex = listHeadIndex;
            while (nodes[currentNodeIndex].nextIndex != -1) {
                currentNodeIndex = nodes[currentNodeIndex].nextIndex;
            }
            nodes[currentNodeIndex].nextIndex = newNodeIndex;
        }
        nodes[newNodeIndex].nextIndex = -1;
        size++;
    }
}
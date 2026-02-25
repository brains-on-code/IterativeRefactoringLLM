package com.thealgorithms.datastructures.lists;

import java.util.Objects;

public class CursorLinkedList<T> {

    private static class Node<T> {
        T element;
        int nextIndex;

        Node(T element, int nextIndex) {
            this.element = element;
            this.nextIndex = nextIndex;
        }
    }

    private static final int CURSOR_SPACE_CAPACITY = 100;

    private final int freeListHeadIndex;
    private int listHeadIndex;
    private final Node<T>[] cursorSpace;
    private int size;

    {
        cursorSpace = new Node[CURSOR_SPACE_CAPACITY];
        for (int i = 0; i < CURSOR_SPACE_CAPACITY; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_CAPACITY - 1].nextIndex = 0;
    }

    public CursorLinkedList() {
        freeListHeadIndex = 0;
        size = 0;
        listHeadIndex = -1;
    }

    public void printList() {
        if (listHeadIndex != -1) {
            int currentIndex = listHeadIndex;
            while (currentIndex != -1) {
                T element = cursorSpace[currentIndex].element;
                System.out.println(element.toString());
                currentIndex = cursorSpace[currentIndex].nextIndex;
            }
        }
    }

    public int indexOf(T targetElement) {
        Objects.requireNonNull(targetElement, "Element cannot be null");
        try {
            int currentIndex = listHeadIndex;
            for (int position = 0; position < size && currentIndex != -1; position++) {
                if (cursorSpace[currentIndex].element.equals(targetElement)) {
                    return position;
                }
                currentIndex = cursorSpace[currentIndex].nextIndex;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public T get(int position) {
        if (position >= 0 && position < size) {
            int currentIndex = listHeadIndex;
            int currentPosition = 0;
            while (currentIndex != -1) {
                T element = cursorSpace[currentIndex].element;
                if (currentPosition == position) {
                    return element;
                }
                currentIndex = cursorSpace[currentIndex].nextIndex;
                currentPosition++;
            }
        }
        return null;
    }

    public void removeByIndex(int index) {
        if (index >= 0 && index < size) {
            T element = get(index);
            remove(element);
        }
    }

    public void remove(T elementToRemove) {
        Objects.requireNonNull(elementToRemove);
        T headElement = cursorSpace[listHeadIndex].element;
        int headNextIndex = cursorSpace[listHeadIndex].nextIndex;

        if (headElement.equals(elementToRemove)) {
            freeNode(listHeadIndex);
            listHeadIndex = headNextIndex;
        } else {
            int previousIndex = listHeadIndex;
            int currentIndex = cursorSpace[previousIndex].nextIndex;
            while (currentIndex != -1) {
                T currentElement = cursorSpace[currentIndex].element;
                if (currentElement.equals(elementToRemove)) {
                    cursorSpace[previousIndex].nextIndex = cursorSpace[currentIndex].nextIndex;
                    freeNode(currentIndex);
                    break;
                }
                previousIndex = currentIndex;
                currentIndex = cursorSpace[previousIndex].nextIndex;
            }
        }
        size--;
    }

    private int allocateNode() {
        int availableNodeIndex = cursorSpace[freeListHeadIndex].nextIndex;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError();
        }
        cursorSpace[freeListHeadIndex].nextIndex = cursorSpace[availableNodeIndex].nextIndex;
        cursorSpace[availableNodeIndex].nextIndex = -1;
        return availableNodeIndex;
    }

    private void freeNode(int index) {
        Node<T> freeListHeadNode = cursorSpace[freeListHeadIndex];
        int nextFreeIndex = freeListHeadNode.nextIndex;
        cursorSpace[freeListHeadIndex].nextIndex = index;
        cursorSpace[index].element = null;
        cursorSpace[index].nextIndex = nextFreeIndex;
    }

    public void append(T element) {
        Objects.requireNonNull(element);
        int newNodeIndex = allocateNode();
        cursorSpace[newNodeIndex].element = element;

        if (listHeadIndex == -1) {
            listHeadIndex = newNodeIndex;
        } else {
            int currentIndex = listHeadIndex;
            while (cursorSpace[currentIndex].nextIndex != -1) {
                currentIndex = cursorSpace[currentIndex].nextIndex;
            }
            cursorSpace[currentIndex].nextIndex = newNodeIndex;
        }
        cursorSpace[newNodeIndex].nextIndex = -1;
        size++;
    }
}
package com.thealgorithms.datastructures.lists;

import java.util.Objects;

public class CursorLinkedList<T> {

    private static class Node<T> {
        T value;
        int nextIndex;

        Node(T value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    private static final int CURSOR_SPACE_SIZE = 100;

    private final int freeListHeadIndex;
    private int headIndex;
    private final Node<T>[] cursorSpace;
    private int size;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].nextIndex = 0;
    }

    public CursorLinkedList() {
        freeListHeadIndex = 0;
        size = 0;
        headIndex = -1;
    }

    public void printList() {
        if (headIndex != -1) {
            int currentIndex = headIndex;
            while (currentIndex != -1) {
                T value = cursorSpace[currentIndex].value;
                System.out.println(value.toString());
                currentIndex = cursorSpace[currentIndex].nextIndex;
            }
        }
    }

    public int indexOf(T targetValue) {
        if (targetValue == null) {
            throw new NullPointerException("Element cannot be null");
        }
        try {
            Objects.requireNonNull(targetValue);
            int currentIndex = headIndex;
            for (int i = 0; i < size && currentIndex != -1; i++) {
                if (cursorSpace[currentIndex].value.equals(targetValue)) {
                    return i;
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
            int currentIndex = headIndex;
            int currentPosition = 0;
            while (currentIndex != -1) {
                T value = cursorSpace[currentIndex].value;
                if (currentPosition == position) {
                    return value;
                }
                currentIndex = cursorSpace[currentIndex].nextIndex;
                currentPosition++;
            }
        }
        return null;
    }

    public void removeByIndex(int index) {
        if (index >= 0 && index < size) {
            T value = get(index);
            remove(value);
        }
    }

    public void remove(T valueToRemove) {
        Objects.requireNonNull(valueToRemove);
        T headValue = cursorSpace[headIndex].value;
        int headNextIndex = cursorSpace[headIndex].nextIndex;

        if (headValue.equals(valueToRemove)) {
            free(headIndex);
            headIndex = headNextIndex;
        } else {
            int previousIndex = headIndex;
            int currentIndex = cursorSpace[previousIndex].nextIndex;
            while (currentIndex != -1) {
                T currentValue = cursorSpace[currentIndex].value;
                if (currentValue.equals(valueToRemove)) {
                    cursorSpace[previousIndex].nextIndex = cursorSpace[currentIndex].nextIndex;
                    free(currentIndex);
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

    private void free(int index) {
        Node<T> freeListHeadNode = cursorSpace[freeListHeadIndex];
        int nextFreeIndex = freeListHeadNode.nextIndex;
        cursorSpace[freeListHeadIndex].nextIndex = index;
        cursorSpace[index].value = null;
        cursorSpace[index].nextIndex = nextFreeIndex;
    }

    public void append(T value) {
        Objects.requireNonNull(value);
        int newNodeIndex = allocateNode();
        cursorSpace[newNodeIndex].value = value;

        if (headIndex == -1) {
            headIndex = newNodeIndex;
        } else {
            int currentIndex = headIndex;
            while (cursorSpace[currentIndex].nextIndex != -1) {
                currentIndex = cursorSpace[currentIndex].nextIndex;
            }
            cursorSpace[currentIndex].nextIndex = newNodeIndex;
        }
        cursorSpace[newNodeIndex].nextIndex = -1;
        size++;
    }
}
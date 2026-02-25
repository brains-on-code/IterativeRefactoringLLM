package com.thealgorithms.datastructures.lists;

import java.util.Objects;


public class CursorLinkedList<T> {


    private static class Node<T> {
        T element;
        int next;

        Node(T element, int next) {
            this.element = element;
            this.next = next;
        }
    }

    private final int os;
    private int head;
    private final Node<T>[] cursorSpace;
    private int count;
    private static final int CURSOR_SPACE_SIZE = 100;

    {
        cursorSpace = new Node[CURSOR_SPACE_SIZE];
        for (int i = 0; i < CURSOR_SPACE_SIZE; i++) {
            cursorSpace[i] = new Node<>(null, i + 1);
        }
        cursorSpace[CURSOR_SPACE_SIZE - 1].next = 0;
    }


    public CursorLinkedList() {
        os = 0;
        count = 0;
        head = -1;
    }


    public void printList() {
        if (head != -1) {
            int start = head;
            while (start != -1) {
                T element = cursorSpace[start].element;
                System.out.println(element.toString());
                start = cursorSpace[start].next;
            }
        }
    }


    public int indexOf(T element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        try {
            Objects.requireNonNull(element);
            Node<T> iterator = cursorSpace[head];
            for (int i = 0; i < count; i++) {
                if (iterator.element.equals(element)) {
                    return i;
                }
                iterator = cursorSpace[iterator.next];
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }


    public T get(int position) {
        if (position >= 0 && position < count) {
            int start = head;
            int counter = 0;
            while (start != -1) {
                T element = cursorSpace[start].element;
                if (counter == position) {
                    return element;
                }
                start = cursorSpace[start].next;
                counter++;
            }
        }
        return null;
    }


    public void removeByIndex(int index) {
        if (index >= 0 && index < count) {
            T element = get(index);
            remove(element);
        }
    }


    public void remove(T element) {
        Objects.requireNonNull(element);
        T tempElement = cursorSpace[head].element;
        int tempNext = cursorSpace[head].next;
        if (tempElement.equals(element)) {
            free(head);
            head = tempNext;
        } else {
            int prevIndex = head;
            int currentIndex = cursorSpace[prevIndex].next;
            while (currentIndex != -1) {
                T currentElement = cursorSpace[currentIndex].element;
                if (currentElement.equals(element)) {
                    cursorSpace[prevIndex].next = cursorSpace[currentIndex].next;
                    free(currentIndex);
                    break;
                }
                prevIndex = currentIndex;
                currentIndex = cursorSpace[prevIndex].next;
            }
        }
        count--;
    }


    private int alloc() {
        int availableNodeIndex = cursorSpace[os].next;
        if (availableNodeIndex == 0) {
            throw new OutOfMemoryError();
        }
        cursorSpace[os].next = cursorSpace[availableNodeIndex].next;
        cursorSpace[availableNodeIndex].next = -1;
        return availableNodeIndex;
    }


    private void free(int index) {
        Node<T> osNode = cursorSpace[os];
        int osNext = osNode.next;
        cursorSpace[os].next = index;
        cursorSpace[index].element = null;
        cursorSpace[index].next = osNext;
    }


    public void append(T element) {
        Objects.requireNonNull(element);
        int availableIndex = alloc();
        cursorSpace[availableIndex].element = element;
        if (head == -1) {
            head = availableIndex;
        } else {
            int iterator = head;
            while (cursorSpace[iterator].next != -1) {
                iterator = cursorSpace[iterator].next;
            }
            cursorSpace[iterator].next = availableIndex;
        }
        cursorSpace[availableIndex].next = -1;
        count++;
    }
}

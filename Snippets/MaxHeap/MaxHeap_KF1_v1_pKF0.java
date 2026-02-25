package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Max-heap implementation backed by a List of HeapElement.
 */
public class Class1 implements Heap {

    /** Internal storage for heap elements (1-based logical indexing, 0-based list). */
    private final List<HeapElement> elements;

    /**
     * Builds a heap from the given list, ignoring null elements.
     *
     * @param initialElements list of elements to build the heap from
     * @throws IllegalArgumentException if initialElements is null
     */
    public Class1(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.elements = new ArrayList<>();

        // Copy non-null elements
        for (HeapElement element : initialElements) {
            if (element != null) {
                elements.add(element);
            }
        }

        // Heapify (bottom-up)
        for (int i = size() / 2; i >= 0; i--) {
            heapifyDown(i + 1); // +1 because heap is logically 1-based
        }
    }

    /**
     * Restores max-heap property by sifting down from the given index.
     *
     * @param index 1-based index
     */
    private void heapifyDown(int index) {
        int currentIndex = index - 1;
        int leftChildIndex = 2 * index - 1;
        int rightChildIndex = 2 * index;

        if (leftChildIndex < size() && elements.get(leftChildIndex).getKey() > elements.get(currentIndex).getKey()) {
            currentIndex = leftChildIndex;
        }

        if (rightChildIndex < size() && elements.get(rightChildIndex).getKey() > elements.get(currentIndex).getKey()) {
            currentIndex = rightChildIndex;
        }

        if (currentIndex != index - 1) {
            HeapElement temp = elements.get(index - 1);
            elements.set(index - 1, elements.get(currentIndex));
            elements.set(currentIndex, temp);

            heapifyDown(currentIndex + 1);
        }
    }

    /**
     * Returns the element at the given 1-based index.
     *
     * @param index 1-based index
     * @return element at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public HeapElement getElement(int index) {
        validateIndex(index);
        return elements.get(index - 1);
    }

    /**
     * Returns the key of the element at the given 1-based index.
     *
     * @param index 1-based index
     * @return key at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    private double getKey(int index) {
        validateIndex(index);
        return elements.get(index - 1).getKey();
    }

    /**
     * Swaps elements at the given 1-based indices.
     *
     * @param firstIndex  1-based index of first element
     * @param secondIndex 1-based index of second element
     */
    private void swap(int firstIndex, int secondIndex) {
        HeapElement temp = elements.get(firstIndex - 1);
        elements.set(firstIndex - 1, elements.get(secondIndex - 1));
        elements.set(secondIndex - 1, temp);
    }

    /**
     * Restores max-heap property by sifting up from the given index.
     *
     * @param index 1-based index
     */
    private void heapifyUp(int index) {
        double key = elements.get(index - 1).getKey();
        while (index > 1 && getKey(parentIndex(index)) < key) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    /**
     * Restores max-heap property by sifting down from the given index,
     * assuming the element at index may be smaller than its children.
     *
     * @param index 1-based index
     */
    private void heapifyDownFrom(int index) {
        double key = elements.get(index - 1).getKey();
        boolean shouldContinue =
                (leftChildIndex(index) <= size() && key < getKey(leftChildIndex(index))) ||
                (rightChildIndex(index) <= size() && key < getKey(rightChildIndex(index)));

        while (leftChildIndex(index) <= size() && shouldContinue) {
            int largerChildIndex;
            if (rightChildIndex(index) <= size() &&
                getKey(rightChildIndex(index)) > getKey(leftChildIndex(index))) {
                largerChildIndex = rightChildIndex(index);
            } else {
                largerChildIndex = leftChildIndex(index);
            }

            swap(index, largerChildIndex);
            index = largerChildIndex;

            shouldContinue =
                    (leftChildIndex(index) <= size() && key < getKey(leftChildIndex(index))) ||
                    (rightChildIndex(index) <= size() && key < getKey(rightChildIndex(index)));
        }
    }

    /**
     * Extracts and returns the maximum element from the heap.
     *
     * @return maximum element
     * @throws EmptyHeapException if the heap is empty
     */
    private HeapElement extractMaxInternal() throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement max = elements.getFirst();
        delete(1);
        return max;
    }

    /**
     * Inserts a new element into the heap.
     *
     * @param element element to insert
     * @throws IllegalArgumentException if element is null
     */
    @Override
    public void method8(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        elements.add(element);
        heapifyUp(size());
    }

    /**
     * Deletes the element at the given 1-based index.
     *
     * @param index 1-based index
     * @throws EmptyHeapException       if the heap is empty
     * @throws IndexOutOfBoundsException if index is out of range
     */
    @Override
    public void method9(int index) throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(index);

        elements.set(index - 1, elements.getLast());
        elements.removeLast();

        if (!isEmpty() && index <= size()) {
            if (index > 1 && getKey(index) > getKey(parentIndex(index))) {
                heapifyUp(index);
            } else {
                heapifyDownFrom(index);
            }
        }
    }

    /**
     * Extracts and returns the maximum element from the heap.
     *
     * @return maximum element
     * @throws EmptyHeapException if the heap is empty
     */
    @Override
    public HeapElement method10() throws EmptyHeapException {
        return extractMaxInternal();
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return heap size
     */
    public int method11() {
        return size();
    }

    /**
     * Returns true if the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean method12() {
        return isEmpty();
    }

    // Helper methods

    private int size() {
        return elements.size();
    }

    private boolean isEmpty() {
        return elements.isEmpty();
    }

    private void validateIndex(int index) {
        if (index <= 0 || index > size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of heap range [1, " + size() + "]");
        }
    }

    private int parentIndex(int index) {
        return (int) Math.floor(index / 2.0);
    }

    private int leftChildIndex(int index) {
        return 2 * index;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 1;
    }
}
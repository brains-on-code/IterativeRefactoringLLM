package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Max-heap implementation backed by a List of HeapElement.
 */
public class MaxHeap implements Heap {

    /** Internal storage for heap elements (1-based logical indexing, 0-based list). */
    private final List<HeapElement> elements;

    /**
     * Builds a heap from the given list, ignoring null elements.
     *
     * @param initialElements list of elements to build the heap from
     * @throws IllegalArgumentException if initialElements is null
     */
    public MaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.elements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                elements.add(element);
            }
        }

        buildHeap();
    }

    /** Builds the heap from the current elements list. */
    private void buildHeap() {
        for (int i = size() / 2; i >= 1; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Restores max-heap property by sifting down from the given 1-based index.
     *
     * @param index 1-based index
     */
    private void heapifyDown(int index) {
        while (true) {
            int leftIndex = leftChildIndex(index);
            int rightIndex = rightChildIndex(index);
            int largestIndex = index;

            if (leftIndex <= size() && getKey(leftIndex) > getKey(largestIndex)) {
                largestIndex = leftIndex;
            }

            if (rightIndex <= size() && getKey(rightIndex) > getKey(largestIndex)) {
                largestIndex = rightIndex;
            }

            if (largestIndex == index) {
                return;
            }

            swap(index, largestIndex);
            index = largestIndex;
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
        return elements.get(toZeroBased(index));
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
        return elements.get(toZeroBased(index)).getKey();
    }

    /**
     * Swaps elements at the given 1-based indices.
     *
     * @param firstIndex  1-based index of first element
     * @param secondIndex 1-based index of second element
     */
    private void swap(int firstIndex, int secondIndex) {
        int first = toZeroBased(firstIndex);
        int second = toZeroBased(secondIndex);

        HeapElement temp = elements.get(first);
        elements.set(first, elements.get(second));
        elements.set(second, temp);
    }

    /**
     * Restores max-heap property by sifting up from the given index.
     *
     * @param index 1-based index
     */
    private void heapifyUp(int index) {
        while (index > 1) {
            int parent = parentIndex(index);
            if (getKey(parent) >= getKey(index)) {
                return;
            }
            swap(index, parent);
            index = parent;
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
    public void insert(HeapElement element) {
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
     * @throws EmptyHeapException        if the heap is empty
     * @throws IndexOutOfBoundsException if index is out of range
     */
    @Override
    public void delete(int index) throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(index);

        int lastIndex = size();
        if (index == lastIndex) {
            elements.removeLast();
            return;
        }

        elements.set(toZeroBased(index), elements.getLast());
        elements.removeLast();

        if (isEmpty() || index > size()) {
            return;
        }

        if (index > 1 && getKey(index) > getKey(parentIndex(index))) {
            heapifyUp(index);
        } else {
            heapifyDown(index);
        }
    }

    /**
     * Extracts and returns the maximum element from the heap.
     *
     * @return maximum element
     * @throws EmptyHeapException if the heap is empty
     */
    @Override
    public HeapElement extractMax() throws EmptyHeapException {
        return extractMaxInternal();
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return heap size
     */
    public int sizePublic() {
        return size();
    }

    /**
     * Returns true if the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmptyPublic() {
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
                "Index " + index + " is out of heap range [1, " + size() + "]"
            );
        }
    }

    private int parentIndex(int index) {
        return index / 2;
    }

    private int leftChildIndex(int index) {
        return 2 * index;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 1;
    }

    private int toZeroBased(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }
}
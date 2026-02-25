package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap implements Heap {

    /**
     * Internal storage for heap elements.
     * Uses 0-based indexing internally; public methods use 1-based indexing.
     */
    private final List<HeapElement> maxHeap;

    /**
     * Constructs a max heap from the given list of elements.
     * Null elements in the input list are ignored.
     *
     * @param listElements list of elements to build the heap from
     * @throws IllegalArgumentException if the input list is null
     */
    public MaxHeap(List<HeapElement> listElements) {
        if (listElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.maxHeap = new ArrayList<>();

        for (HeapElement heapElement : listElements) {
            if (heapElement != null) {
                maxHeap.add(heapElement);
            }
        }

        buildHeap();
    }

    /**
     * Builds the heap in-place from the current list of elements.
     * Time complexity: O(n).
     */
    private void buildHeap() {
        for (int i = parentIndex(maxHeap.size()); i >= 1; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Restores the max-heap property by moving the element at the given index
     * down the tree until it is larger than its children.
     *
     * @param index 1-based index of the element to heapify down
     */
    private void heapifyDown(int index) {
        int largest = index;
        int left = leftChildIndex(index);
        int right = rightChildIndex(index);

        if (left <= maxHeap.size() && getElementKey(left) > getElementKey(largest)) {
            largest = left;
        }

        if (right <= maxHeap.size() && getElementKey(right) > getElementKey(largest)) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    /**
     * Returns the 1-based index of the parent of the given node.
     */
    private int parentIndex(int index) {
        return index / 2;
    }

    /**
     * Returns the 1-based index of the left child of the given node.
     */
    private int leftChildIndex(int index) {
        return 2 * index;
    }

    /**
     * Returns the 1-based index of the right child of the given node.
     */
    private int rightChildIndex(int index) {
        return 2 * index + 1;
    }

    /**
     * Ensures the given 1-based index is within the valid heap range.
     *
     * @param index 1-based index to validate
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void validateIndex(int index) {
        if (index <= 0 || index > maxHeap.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is out of heap range [1, " + maxHeap.size() + "]"
            );
        }
    }

    /**
     * Returns the element at the given 1-based index.
     *
     * @param index 1-based index of the element
     * @return the heap element at the specified index
     */
    public HeapElement getElement(int index) {
        validateIndex(index);
        return maxHeap.get(index - 1);
    }

    /**
     * Returns the key of the element at the given 1-based index.
     */
    private double getElementKey(int index) {
        validateIndex(index);
        return maxHeap.get(index - 1).getKey();
    }

    /**
     * Swaps the elements at the given 1-based indices.
     */
    private void swap(int index1, int index2) {
        HeapElement temp = maxHeap.get(index1 - 1);
        maxHeap.set(index1 - 1, maxHeap.get(index2 - 1));
        maxHeap.set(index2 - 1, temp);
    }

    /**
     * Restores the max-heap property by moving the element at the given index
     * up the tree until it is no longer larger than its parent.
     *
     * @param index 1-based index of the element to move up
     */
    private void toggleUp(int index) {
        double key = getElementKey(index);
        int parent = parentIndex(index);

        while (index > 1 && getElementKey(parent) < key) {
            swap(index, parent);
            index = parent;
            parent = parentIndex(index);
        }
    }

    /**
     * Restores the max-heap property by moving the element at the given index
     * down the tree until it is no longer smaller than its largest child.
     *
     * @param index 1-based index of the element to move down
     */
    private void toggleDown(int index) {
        double key = getElementKey(index);

        while (leftChildIndex(index) <= maxHeap.size()) {
            int left = leftChildIndex(index);
            int right = rightChildIndex(index);
            int largerChild = left;

            if (right <= maxHeap.size() && getElementKey(right) > getElementKey(left)) {
                largerChild = right;
            }

            if (getElementKey(largerChild) <= key) {
                break;
            }

            swap(index, largerChild);
            index = largerChild;
        }
    }

    /**
     * Removes and returns the maximum element (root) of the heap.
     *
     * @return the maximum element in the heap
     * @throws EmptyHeapException if the heap is empty
     */
    private HeapElement extractMax() throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement result = maxHeap.getFirst();
        deleteElement(1);
        return result;
    }

    /**
     * Inserts a new element into the heap and restores the heap property.
     *
     * @param element element to insert
     * @throws IllegalArgumentException if the element is null
     */
    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        maxHeap.add(element);
        toggleUp(maxHeap.size());
    }

    /**
     * Deletes the element at the given 1-based index and restores the heap property.
     *
     * @param index 1-based index of the element to delete
     * @throws EmptyHeapException if the heap is empty
     */
    @Override
    public void deleteElement(int index) throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(index);

        maxHeap.set(index - 1, maxHeap.getLast());
        maxHeap.removeLast();

        if (!maxHeap.isEmpty() && index <= maxHeap.size()) {
            int parent = parentIndex(index);
            if (index > 1 && getElementKey(index) > getElementKey(parent)) {
                toggleUp(index);
            } else {
                toggleDown(index);
            }
        }
    }

    /**
     * Removes and returns the maximum element (root) of the heap.
     *
     * @return the maximum element in the heap
     * @throws EmptyHeapException if the heap is empty
     */
    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    /**
     * Returns the number of elements currently in the heap.
     */
    public int size() {
        return maxHeap.size();
    }

    /**
     * Returns true if the heap contains no elements.
     */
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }
}
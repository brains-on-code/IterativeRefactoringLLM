package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Max-heap implementation where each node's key is greater than or equal to its children's keys.
 *
 * <p>Time complexity:
 * <ul>
 *   <li>Insertion: O(log n)</li>
 *   <li>Deletion: O(log n)</li>
 *   <li>Get max: O(1)</li>
 * </ul>
 */
public class MaxHeap implements Heap {

    /** Internal storage for heap elements (0-based index, heap operations use 1-based indices). */
    private final List<HeapElement> maxHeap;

    /**
     * Constructs a new MaxHeap from a list of elements.
     * Null elements in the input list are ignored.
     *
     * @param listElements list of HeapElement objects to initialize the heap
     * @throws IllegalArgumentException if the input list is null
     */
    public MaxHeap(List<HeapElement> listElements) {
        if (listElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        maxHeap = new ArrayList<>();

        for (HeapElement heapElement : listElements) {
            if (heapElement != null) {
                maxHeap.add(heapElement);
            }
        }

        // Build heap bottom-up
        for (int i = maxHeap.size() / 2; i >= 0; i--) {
            heapifyDown(i + 1); // heapifyDown uses 1-based index
        }
    }

    /**
     * Restores the heap property by moving an element down the heap.
     * Used during heap construction.
     *
     * @param elementIndex 1-based index of the element to heapify
     */
    private void heapifyDown(int elementIndex) {
        int largest = elementIndex - 1;
        int leftChild = 2 * elementIndex - 1;
        int rightChild = 2 * elementIndex;

        if (leftChild < maxHeap.size()
                && maxHeap.get(leftChild).getKey() > maxHeap.get(largest).getKey()) {
            largest = leftChild;
        }

        if (rightChild < maxHeap.size()
                && maxHeap.get(rightChild).getKey() > maxHeap.get(largest).getKey()) {
            largest = rightChild;
        }

        if (largest != elementIndex - 1) {
            HeapElement swap = maxHeap.get(elementIndex - 1);
            maxHeap.set(elementIndex - 1, maxHeap.get(largest));
            maxHeap.set(largest, swap);

            heapifyDown(largest + 1);
        }
    }

    /**
     * Returns the element at the specified index without removing it.
     * Index is 1-based.
     *
     * @param elementIndex 1-based index of the element to retrieve
     * @return HeapElement at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public HeapElement getElement(int elementIndex) {
        validateIndex(elementIndex);
        return maxHeap.get(elementIndex - 1);
    }

    /**
     * Returns the key of the element at the specified index.
     * Index is 1-based.
     *
     * @param elementIndex 1-based index of the element
     * @return key of the element
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private double getElementKey(int elementIndex) {
        validateIndex(elementIndex);
        return maxHeap.get(elementIndex - 1).getKey();
    }

    /**
     * Swaps two elements in the heap.
     * Indices are 1-based.
     *
     * @param index1 1-based index of first element
     * @param index2 1-based index of second element
     */
    private void swap(int index1, int index2) {
        HeapElement temporaryElement = maxHeap.get(index1 - 1);
        maxHeap.set(index1 - 1, maxHeap.get(index2 - 1));
        maxHeap.set(index2 - 1, temporaryElement);
    }

    /**
     * Moves an element up the heap until the heap property is restored.
     * Called after insertion.
     *
     * @param elementIndex 1-based index of the element to move up
     */
    private void toggleUp(int elementIndex) {
        double key = maxHeap.get(elementIndex - 1).getKey();
        int parentIndex = elementIndex / 2;

        while (elementIndex > 1 && getElementKey(parentIndex) < key) {
            swap(elementIndex, parentIndex);
            elementIndex = parentIndex;
            parentIndex = elementIndex / 2;
        }
    }

    /**
     * Moves an element down the heap until the heap property is restored.
     * Called after deletion.
     *
     * @param elementIndex 1-based index of the element to move down
     */
    private void toggleDown(int elementIndex) {
        double key = maxHeap.get(elementIndex - 1).getKey();

        boolean wrongOrder =
                (2 * elementIndex <= maxHeap.size() && key < getElementKey(elementIndex * 2))
                        || (2 * elementIndex + 1 <= maxHeap.size()
                                && key < getElementKey(elementIndex * 2 + 1));

        while (2 * elementIndex <= maxHeap.size() && wrongOrder) {
            int leftChildIndex = 2 * elementIndex;
            int rightChildIndex = 2 * elementIndex + 1;

            int largerChildIndex;
            if (rightChildIndex <= maxHeap.size()
                    && getElementKey(rightChildIndex) > getElementKey(leftChildIndex)) {
                largerChildIndex = rightChildIndex;
            } else {
                largerChildIndex = leftChildIndex;
            }

            swap(elementIndex, largerChildIndex);
            elementIndex = largerChildIndex;

            wrongOrder =
                    (2 * elementIndex <= maxHeap.size()
                                    && key < getElementKey(elementIndex * 2))
                            || (2 * elementIndex + 1 <= maxHeap.size()
                                    && key < getElementKey(elementIndex * 2 + 1));
        }
    }

    /**
     * Removes and returns the maximum element from the heap.
     *
     * @return element with the highest key
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

    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        maxHeap.add(element);
        toggleUp(maxHeap.size());
    }

    @Override
    public void deleteElement(int elementIndex) throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(elementIndex);

        maxHeap.set(elementIndex - 1, maxHeap.getLast());
        maxHeap.removeLast();

        if (!maxHeap.isEmpty() && elementIndex <= maxHeap.size()) {
            int parentIndex = elementIndex / 2;
            if (elementIndex > 1 && getElementKey(elementIndex) > getElementKey(parentIndex)) {
                toggleUp(elementIndex);
            } else {
                toggleDown(elementIndex);
            }
        }
    }

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return heap size
     */
    public int size() {
        return maxHeap.size();
    }

    /**
     * Returns whether the heap is empty.
     *
     * @return true if the heap contains no elements
     */
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    /**
     * Validates that a 1-based index is within the current heap bounds.
     *
     * @param elementIndex 1-based index to validate
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void validateIndex(int elementIndex) {
        if (elementIndex <= 0 || elementIndex > maxHeap.size()) {
            throw new IndexOutOfBoundsException(
                    "Index "
                            + elementIndex
                            + " is out of heap range [1, "
                            + maxHeap.size()
                            + "]");
        }
    }
}
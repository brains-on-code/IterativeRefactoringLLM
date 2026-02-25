package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * A Max Heap implementation where each node's key is higher than or equal to its children's keys.
 * This data structure provides O(log n) time complexity for insertion and deletion operations,
 * and O(1) for retrieving the maximum element.
 *
 * Properties:
 * 1. Complete Binary Tree
 * 2. Parent node's key ≥ Children nodes' keys
 * 3. Root contains the maximum element
 *
 * Example usage:
 * <pre>
 * List<HeapElement> elements = Arrays.asList(
 *     new HeapElement(5, "Five"),
 *     new HeapElement(2, "Two")
 * );
 * MaxHeap heap = new MaxHeap(elements);
 * heap.insertElement(new HeapElement(7, "Seven"));
 * HeapElement max = heap.getElement(); // Returns and removes the maximum element
 * </pre>
 *
 * @author Nicolas Renard
 */
public class MaxHeap implements Heap {

    /** The internal list that stores heap elements */
    private final List<HeapElement> heapElements;

    /**
     * Constructs a new MaxHeap from a list of elements.
     * Null elements in the input list are ignored.
     *
     * @param initialElements List of HeapElement objects to initialize the heap
     * @throws IllegalArgumentException if the input list is null
     */
    public MaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        heapElements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                heapElements.add(element);
            }
        }

        for (int arrayIndex = heapElements.size() / 2; arrayIndex >= 0; arrayIndex--) {
            heapifyDown(arrayIndex + 1); // +1 because heapifyDown expects 1-based index
        }
    }

    /**
     * Maintains heap properties by moving an element down the heap.
     * Similar to siftDown but used specifically during initialization.
     *
     * @param heapIndex 1-based index of the element to heapify
     */
    private void heapifyDown(int heapIndex) {
        int largestArrayIndex = heapIndex - 1;
        int leftChildArrayIndex = 2 * heapIndex - 1;
        int rightChildArrayIndex = 2 * heapIndex;

        if (leftChildArrayIndex < heapElements.size()
                && heapElements.get(leftChildArrayIndex).getKey()
                    > heapElements.get(largestArrayIndex).getKey()) {
            largestArrayIndex = leftChildArrayIndex;
        }

        if (rightChildArrayIndex < heapElements.size()
                && heapElements.get(rightChildArrayIndex).getKey()
                    > heapElements.get(largestArrayIndex).getKey()) {
            largestArrayIndex = rightChildArrayIndex;
        }

        if (largestArrayIndex != heapIndex - 1) {
            HeapElement temporaryElement = heapElements.get(heapIndex - 1);
            heapElements.set(heapIndex - 1, heapElements.get(largestArrayIndex));
            heapElements.set(largestArrayIndex, temporaryElement);

            heapifyDown(largestArrayIndex + 1);
        }
    }

    /**
     * Retrieves the element at the specified index without removing it.
     * Note: The index is 1-based for consistency with heap operations.
     *
     * @param heapIndex 1-based index of the element to retrieve
     * @return HeapElement at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public HeapElement getElement(int heapIndex) {
        validateHeapIndex(heapIndex);
        return heapElements.get(heapIndex - 1);
    }

    /**
     * Retrieves the key value of an element at the specified index.
     *
     * @param heapIndex 1-based index of the element
     * @return double value representing the key
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private double getElementKey(int heapIndex) {
        validateHeapIndex(heapIndex);
        return heapElements.get(heapIndex - 1).getKey();
    }

    /**
     * Swaps two elements in the heap.
     *
     * @param firstHeapIndex  1-based index of first element
     * @param secondHeapIndex 1-based index of second element
     */
    private void swap(int firstHeapIndex, int secondHeapIndex) {
        HeapElement temporaryElement = heapElements.get(firstHeapIndex - 1);
        heapElements.set(firstHeapIndex - 1, heapElements.get(secondHeapIndex - 1));
        heapElements.set(secondHeapIndex - 1, temporaryElement);
    }

    /**
     * Moves an element up the heap until heap properties are satisfied.
     * This operation is called after insertion to maintain heap properties.
     *
     * @param heapIndex 1-based index of the element to move up
     */
    private void siftUp(int heapIndex) {
        double currentKey = heapElements.get(heapIndex - 1).getKey();
        int parentHeapIndex = heapIndex / 2;

        while (heapIndex > 1 && getElementKey(parentHeapIndex) < currentKey) {
            swap(heapIndex, parentHeapIndex);
            heapIndex = parentHeapIndex;
            parentHeapIndex = heapIndex / 2;
        }
    }

    /**
     * Moves an element down the heap until heap properties are satisfied.
     * This operation is called after deletion to maintain heap properties.
     *
     * @param heapIndex 1-based index of the element to move down
     */
    private void siftDown(int heapIndex) {
        double currentKey = heapElements.get(heapIndex - 1).getKey();

        boolean isInWrongOrder =
            (2 * heapIndex <= heapElements.size() && currentKey < getElementKey(heapIndex * 2))
                || (2 * heapIndex + 1 <= heapElements.size()
                    && currentKey < getElementKey(heapIndex * 2 + 1));

        while (2 * heapIndex <= heapElements.size() && isInWrongOrder) {
            int largerChildHeapIndex;
            int leftChildHeapIndex = heapIndex * 2;
            int rightChildHeapIndex = heapIndex * 2 + 1;

            if (rightChildHeapIndex <= heapElements.size()
                    && getElementKey(rightChildHeapIndex) > getElementKey(leftChildHeapIndex)) {
                largerChildHeapIndex = rightChildHeapIndex;
            } else {
                largerChildHeapIndex = leftChildHeapIndex;
            }

            swap(heapIndex, largerChildHeapIndex);
            heapIndex = largerChildHeapIndex;

            isInWrongOrder =
                (2 * heapIndex <= heapElements.size() && currentKey < getElementKey(heapIndex * 2))
                    || (2 * heapIndex + 1 <= heapElements.size()
                        && currentKey < getElementKey(heapIndex * 2 + 1));
        }
    }

    /**
     * Extracts and returns the maximum element from the heap.
     *
     * @return HeapElement with the highest key
     * @throws EmptyHeapException if the heap is empty
     */
    private HeapElement extractMax() throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = heapElements.getFirst();
        deleteElement(1);
        return maxElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        heapElements.add(element);
        siftUp(heapElements.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteElement(int heapIndex) throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateHeapIndex(heapIndex);

        heapElements.set(heapIndex - 1, heapElements.getLast());
        heapElements.removeLast();

        if (!heapElements.isEmpty() && heapIndex <= heapElements.size()) {
            int parentHeapIndex = heapIndex / 2;
            if (heapIndex > 1 && getElementKey(heapIndex) > getElementKey(parentHeapIndex)) {
                siftUp(heapIndex);
            } else {
                siftDown(heapIndex);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    /**
     * Returns the current size of the heap.
     *
     * @return number of elements in the heap
     */
    public int size() {
        return heapElements.size();
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap contains no elements
     */
    public boolean isEmpty() {
        return heapElements.isEmpty();
    }

    /**
     * Validates that the given heap index is within the valid 1-based range.
     *
     * @param heapIndex 1-based index to validate
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private void validateHeapIndex(int heapIndex) {
        if (heapIndex <= 0 || heapIndex > heapElements.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + heapElements.size() + "]"
            );
        }
    }
}
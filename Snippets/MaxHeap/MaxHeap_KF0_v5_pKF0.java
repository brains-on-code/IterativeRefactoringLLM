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
 * author Nicolas Renard
 */
public class MaxHeap implements Heap {

    /** The internal list that stores heap elements (0-based index). */
    private final List<HeapElement> maxHeap;

    /**
     * Constructs a new MaxHeap from a list of elements.
     * Null elements in the input list are ignored.
     *
     * @param listElements List of HeapElement objects to initialize the heap
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

    /** Builds the heap in-place from the current list contents. */
    private void buildHeap() {
        for (int i = parentIndex(maxHeap.size() - 1); i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /** Returns the parent index of a given index (0-based). */
    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    /** Returns the left child index of a given index (0-based). */
    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    /** Returns the right child index of a given index (0-based). */
    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    /**
     * Maintains heap properties by moving an element down the heap.
     * Used during initialization and after deletions.
     *
     * @param index 0-based index of the element to heapify
     */
    private void heapifyDown(int index) {
        int size = maxHeap.size();

        while (true) {
            int largest = index;
            int left = leftChildIndex(index);
            int right = rightChildIndex(index);

            if (left < size && maxHeap.get(left).getKey() > maxHeap.get(largest).getKey()) {
                largest = left;
            }

            if (right < size && maxHeap.get(right).getKey() > maxHeap.get(largest).getKey()) {
                largest = right;
            }

            if (largest == index) {
                break;
            }

            swapInternal(index, largest);
            index = largest;
        }
    }

    /**
     * Retrieves the element at the specified index without removing it.
     * Note: The index is 1-based for consistency with heap operations.
     *
     * @param elementIndex 1-based index of the element to retrieve
     * @return HeapElement at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public HeapElement getElement(int elementIndex) {
        return maxHeap.get(toInternalIndex(elementIndex));
    }

    /**
     * Converts a 1-based external index to a 0-based internal index and validates it.
     */
    private int toInternalIndex(int elementIndex) {
        if (elementIndex <= 0 || elementIndex > maxHeap.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + elementIndex + " is out of heap range [1, " + maxHeap.size() + "]"
            );
        }
        return elementIndex - 1;
    }

    /**
     * Swaps two elements in the heap using 0-based indices.
     *
     * @param i 0-based index of first element
     * @param j 0-based index of second element
     */
    private void swapInternal(int i, int j) {
        HeapElement temp = maxHeap.get(i);
        maxHeap.set(i, maxHeap.get(j));
        maxHeap.set(j, temp);
    }

    /**
     * Swaps two elements in the heap using 1-based indices.
     *
     * @param index1 1-based index of first element
     * @param index2 1-based index of second element
     */
    private void swap(int index1, int index2) {
        swapInternal(toInternalIndex(index1), toInternalIndex(index2));
    }

    /**
     * Moves an element up the heap until heap properties are satisfied.
     * This operation is called after insertion to maintain heap properties.
     *
     * @param elementIndex 1-based index of the element to move up
     */
    private void toggleUp(int elementIndex) {
        int index = toInternalIndex(elementIndex);
        toggleUpInternal(index);
    }

    /**
     * Internal version of toggleUp using 0-based index.
     *
     * @param index 0-based index of the element to move up
     */
    private void toggleUpInternal(int index) {
        HeapElement element = maxHeap.get(index);
        double key = element.getKey();

        while (index > 0) {
            int parent = parentIndex(index);
            if (maxHeap.get(parent).getKey() >= key) {
                break;
            }
            maxHeap.set(index, maxHeap.get(parent));
            index = parent;
        }

        maxHeap.set(index, element);
    }

    /**
     * Moves an element down the heap until heap properties are satisfied.
     * This operation is called after deletion to maintain heap properties.
     *
     * @param elementIndex 1-based index of the element to move down
     */
    private void toggleDown(int elementIndex) {
        heapifyDown(toInternalIndex(elementIndex));
    }

    /**
     * Extracts and returns the maximum element from the heap.
     *
     * @return HeapElement with the highest key
     * @throws EmptyHeapException if the heap is empty
     */
    private HeapElement extractMax() throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement result = maxHeap.get(0);
        deleteElement(1);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        maxHeap.add(element);
        toggleUpInternal(maxHeap.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteElement(int elementIndex) throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }

        int internalIndex = toInternalIndex(elementIndex);
        int lastIndex = maxHeap.size() - 1;

        HeapElement lastElement = maxHeap.get(lastIndex);
        maxHeap.set(internalIndex, lastElement);
        maxHeap.remove(lastIndex);

        if (internalIndex < maxHeap.size()) {
            boolean hasParent = internalIndex > 0;
            if (hasParent) {
                int parent = parentIndex(internalIndex);
                if (maxHeap.get(internalIndex).getKey() > maxHeap.get(parent).getKey()) {
                    toggleUpInternal(internalIndex);
                    return;
                }
            }
            heapifyDown(internalIndex);
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
        return maxHeap.size();
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap contains no elements
     */
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }
}
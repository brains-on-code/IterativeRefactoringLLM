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
    private final List<HeapElement> elements;

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

        elements = new ArrayList<>();

        // Safe initialization: directly add non-null elements first
        for (HeapElement heapElement : initialElements) {
            if (heapElement != null) {
                elements.add(heapElement);
            }
        }

        // Heapify the array bottom-up
        for (int i = elements.size() / 2; i >= 0; i--) {
            heapifyDown(i + 1); // +1 because heapifyDown expects 1-based index
        }
    }

    /**
     * Maintains heap properties by moving an element down the heap.
     * Similar to siftDown but used specifically during initialization.
     *
     * @param heapIndex 1-based index of the element to heapify
     */
    private void heapifyDown(int heapIndex) {
        int largestIndex = heapIndex - 1;
        int leftChildIndex = 2 * heapIndex - 1;
        int rightChildIndex = 2 * heapIndex;

        if (leftChildIndex < elements.size()
                && elements.get(leftChildIndex).getKey() > elements.get(largestIndex).getKey()) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < elements.size()
                && elements.get(rightChildIndex).getKey() > elements.get(largestIndex).getKey()) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != heapIndex - 1) {
            HeapElement temp = elements.get(heapIndex - 1);
            elements.set(heapIndex - 1, elements.get(largestIndex));
            elements.set(largestIndex, temp);

            heapifyDown(largestIndex + 1);
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
        if (heapIndex <= 0 || heapIndex > elements.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }
        return elements.get(heapIndex - 1);
    }

    /**
     * Retrieves the key value of an element at the specified index.
     *
     * @param heapIndex 1-based index of the element
     * @return double value representing the key
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    private double getElementKey(int heapIndex) {
        if (heapIndex <= 0 || heapIndex > elements.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }
        return elements.get(heapIndex - 1).getKey();
    }

    /**
     * Swaps two elements in the heap.
     *
     * @param firstIndex  1-based index of first element
     * @param secondIndex 1-based index of second element
     */
    private void swap(int firstIndex, int secondIndex) {
        HeapElement temporaryElement = elements.get(firstIndex - 1);
        elements.set(firstIndex - 1, elements.get(secondIndex - 1));
        elements.set(secondIndex - 1, temporaryElement);
    }

    /**
     * Moves an element up the heap until heap properties are satisfied.
     * This operation is called after insertion to maintain heap properties.
     *
     * @param heapIndex 1-based index of the element to move up
     */
    private void siftUp(int heapIndex) {
        double currentKey = elements.get(heapIndex - 1).getKey();
        int parentIndex = heapIndex / 2;

        while (heapIndex > 1 && getElementKey(parentIndex) < currentKey) {
            swap(heapIndex, parentIndex);
            heapIndex = parentIndex;
            parentIndex = heapIndex / 2;
        }
    }

    /**
     * Moves an element down the heap until heap properties are satisfied.
     * This operation is called after deletion to maintain heap properties.
     *
     * @param heapIndex 1-based index of the element to move down
     */
    private void siftDown(int heapIndex) {
        double currentKey = elements.get(heapIndex - 1).getKey();

        boolean isInWrongOrder =
            (2 * heapIndex <= elements.size() && currentKey < getElementKey(heapIndex * 2))
                || (2 * heapIndex + 1 <= elements.size()
                    && currentKey < getElementKey(heapIndex * 2 + 1));

        while (2 * heapIndex <= elements.size() && isInWrongOrder) {
            int largerChildIndex;
            int leftChildIndex = heapIndex * 2;
            int rightChildIndex = heapIndex * 2 + 1;

            if (rightChildIndex <= elements.size()
                    && getElementKey(rightChildIndex) > getElementKey(leftChildIndex)) {
                largerChildIndex = rightChildIndex;
            } else {
                largerChildIndex = leftChildIndex;
            }

            swap(heapIndex, largerChildIndex);
            heapIndex = largerChildIndex;

            isInWrongOrder =
                (2 * heapIndex <= elements.size() && currentKey < getElementKey(heapIndex * 2))
                    || (2 * heapIndex + 1 <= elements.size()
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
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = elements.getFirst();
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
        elements.add(element);
        siftUp(elements.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteElement(int heapIndex) throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        if (heapIndex > elements.size() || heapIndex <= 0) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }

        // Replace with last element and remove last position
        elements.set(heapIndex - 1, elements.getLast());
        elements.removeLast();

        // No need to sift if we just removed the last element
        if (!elements.isEmpty() && heapIndex <= elements.size()) {
            int parentIndex = heapIndex / 2;
            if (heapIndex > 1 && getElementKey(heapIndex) > getElementKey(parentIndex)) {
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
        return elements.size();
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap contains no elements
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
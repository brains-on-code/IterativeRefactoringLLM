package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * A max-heap implementation backed by a {@link List} of {@link HeapElement}.
 *
 * <p>This heap uses 1-based indexing for its public API (i.e., the "first"
 * element is at index 1), while internally it stores elements in a 0-based
 * {@link List}. All index validations and conversions are handled inside
 * the methods.</p>
 */
public class Class1 implements Heap {

    /** Internal storage for heap elements (0-based index). */
    private final List<HeapElement> heap;

    /**
     * Constructs a max-heap from the given list of elements.
     * Null elements in the input list are ignored.
     *
     * @param elements the initial elements to build the heap from
     * @throws IllegalArgumentException if {@code elements} is {@code null}
     */
    public Class1(List<HeapElement> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.heap = new ArrayList<>();

        for (HeapElement element : elements) {
            if (element != null) {
                heap.add(element);
            }
        }

        for (int i = size() / 2; i >= 0; i--) {
            heapifyDown(i + 1);
        }
    }

    /**
     * Restores the max-heap property by sifting the element at the given
     * 1-based index down the tree.
     *
     * @param index1Based the index (1-based) of the element to heapify down
     */
    private void heapifyDown(int index1Based) {
        int currentIndex0Based = index1Based - 1;
        int leftChild0Based = 2 * index1Based - 1;
        int rightChild0Based = 2 * index1Based;

        if (leftChild0Based < size()
            && heap.get(leftChild0Based).getKey() > heap.get(currentIndex0Based).getKey()) {
            currentIndex0Based = leftChild0Based;
        }

        if (rightChild0Based < size()
            && heap.get(rightChild0Based).getKey() > heap.get(currentIndex0Based).getKey()) {
            currentIndex0Based = rightChild0Based;
        }

        if (currentIndex0Based != index1Based - 1) {
            swap(index1Based, currentIndex0Based + 1);
            heapifyDown(currentIndex0Based + 1);
        }
    }

    /**
     * Returns the element at the given 1-based index without removing it.
     *
     * @param index1Based the index (1-based) of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of heap range
     */
    public HeapElement get(int index1Based) {
        validateIndex(index1Based);
        return heap.get(toZeroBased(index1Based));
    }

    /**
     * Returns the key of the element at the given 1-based index.
     *
     * @param index1Based the index (1-based) of the element
     * @return the key of the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of heap range
     */
    private double getKey(int index1Based) {
        validateIndex(index1Based);
        return heap.get(toZeroBased(index1Based)).getKey();
    }

    /**
     * Swaps the elements at the given 1-based indices.
     *
     * @param indexA1Based first index (1-based)
     * @param indexB1Based second index (1-based)
     */
    private void swap(int indexA1Based, int indexB1Based) {
        int a = toZeroBased(indexA1Based);
        int b = toZeroBased(indexB1Based);
        HeapElement temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
    }

    /**
     * Restores the max-heap property by sifting the element at the given
     * 1-based index up the tree.
     *
     * @param index1Based the index (1-based) of the element to heapify up
     */
    private void heapifyUp(int index1Based) {
        double key = heap.get(toZeroBased(index1Based)).getKey();
        while (index1Based > 1 && getKey(parentIndex(index1Based)) < key) {
            int parent = parentIndex(index1Based);
            swap(index1Based, parent);
            index1Based = parent;
        }
    }

    /**
     * Restores the max-heap property starting from the given 1-based index,
     * moving the element down as needed.
     *
     * @param index1Based the index (1-based) of the element to heapify down
     */
    private void heapifyDownFrom(int index1Based) {
        double key = heap.get(toZeroBased(index1Based)).getKey();

        boolean shouldContinue =
            (leftChildIndex(index1Based) <= size() && key < getKey(leftChildIndex(index1Based))) ||
            (rightChildIndex(index1Based) <= size() && key < getKey(rightChildIndex(index1Based)));

        while (leftChildIndex(index1Based) <= size() && shouldContinue) {
            int left = leftChildIndex(index1Based);
            int right = rightChildIndex(index1Based);

            int largerChildIndex;
            if (right <= size() && getKey(right) > getKey(left)) {
                largerChildIndex = right;
            } else {
                largerChildIndex = left;
            }

            swap(index1Based, largerChildIndex);
            index1Based = largerChildIndex;

            shouldContinue =
                (leftChildIndex(index1Based) <= size() && key < getKey(leftChildIndex(index1Based))) ||
                (rightChildIndex(index1Based) <= size() && key < getKey(rightChildIndex(index1Based)));
        }
    }

    /**
     * Extracts and returns the maximum element (root) of the heap.
     *
     * @return the maximum element in the heap
     * @throws EmptyHeapException if the heap is empty
     */
    private HeapElement extractMaxInternal() throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement max = heap.getFirst();
        delete(1);
        return max;
    }

    /**
     * Inserts a new element into the heap.
     *
     * @param element the element to insert
     * @throws IllegalArgumentException if {@code element} is {@code null}
     */
    @Override
    public void method8(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        heap.add(element);
        heapifyUp(size());
    }

    /**
     * Deletes the element at the given 1-based index from the heap.
     *
     * @param index1Based the index (1-based) of the element to delete
     * @throws EmptyHeapException if the heap is empty
     * @throws IndexOutOfBoundsException if the index is out of heap range
     */
    @Override
    public void method9(int index1Based) throws EmptyHeapException {
        if (isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(index1Based);

        heap.set(toZeroBased(index1Based), heap.getLast());
        heap.removeLast();

        if (!isEmpty() && index1Based <= size()) {
            int parent = parentIndex(index1Based);
            if (index1Based > 1 && getKey(index1Based) > getKey(parent)) {
                heapifyUp(index1Based);
            } else {
                heapifyDownFrom(index1Based);
            }
        }
    }

    /**
     * Extracts and returns the maximum element (root) of the heap.
     *
     * @return the maximum element in the heap
     * @throws EmptyHeapException if the heap is empty
     */
    @Override
    public HeapElement method10() throws EmptyHeapException {
        return extractMaxInternal();
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return the heap size
     */
    public int method11() {
        return size();
    }

    /**
     * Returns {@code true} if the heap contains no elements.
     *
     * @return {@code true} if the heap is empty; {@code false} otherwise
     */
    public boolean method12() {
        return isEmpty();
    }

    private int size() {
        return heap.size();
    }

    private boolean isEmpty() {
        return heap.isEmpty();
    }

    private int toZeroBased(int index1Based) {
        return index1Based - 1;
    }

    private void validateIndex(int index1Based) {
        if (index1Based <= 0 || index1Based > size()) {
            throw new IndexOutOfBoundsException(
                "Index " + index1Based + " is out of heap range [1, " + size() + "]"
            );
        }
    }

    private int parentIndex(int index1Based) {
        return index1Based / 2;
    }

    private int leftChildIndex(int index1Based) {
        return 2 * index1Based;
    }

    private int rightChildIndex(int index1Based) {
        return 2 * index1Based + 1;
    }

    // Keeping original method names used in the codebase for delete
    private void delete(int index1Based) throws EmptyHeapException {
        method9(index1Based);
    }
}
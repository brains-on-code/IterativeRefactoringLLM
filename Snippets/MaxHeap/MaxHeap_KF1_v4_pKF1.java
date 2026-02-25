package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class BinaryMaxHeap implements Heap {

    private final List<HeapElement> heapElements;

    public BinaryMaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        heapElements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                heapElements.add(element);
            }
        }

        for (int heapIndex = heapElements.size() / 2; heapIndex >= 0; heapIndex--) {
            heapifyDown(heapIndex + 1);
        }
    }

    private void heapifyDown(int heapIndex) {
        int largestIndex = heapIndex - 1;
        int leftChildIndex = 2 * heapIndex - 1;
        int rightChildIndex = 2 * heapIndex;

        if (leftChildIndex < heapElements.size()
            && heapElements.get(leftChildIndex).getKey() > heapElements.get(largestIndex).getKey()) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heapElements.size()
            && heapElements.get(rightChildIndex).getKey() > heapElements.get(largestIndex).getKey()) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != heapIndex - 1) {
            HeapElement temp = heapElements.get(heapIndex - 1);
            heapElements.set(heapIndex - 1, heapElements.get(largestIndex));
            heapElements.set(largestIndex, temp);

            heapifyDown(largestIndex + 1);
        }
    }

    public HeapElement getElementAt(int heapIndex) {
        validateHeapIndex(heapIndex);
        return heapElements.get(heapIndex - 1);
    }

    private double getKeyAt(int heapIndex) {
        validateHeapIndex(heapIndex);
        return heapElements.get(heapIndex - 1).getKey();
    }

    private void swap(int firstHeapIndex, int secondHeapIndex) {
        HeapElement temp = heapElements.get(firstHeapIndex - 1);
        heapElements.set(firstHeapIndex - 1, heapElements.get(secondHeapIndex - 1));
        heapElements.set(secondHeapIndex - 1, temp);
    }

    private void heapifyUp(int heapIndex) {
        double currentKey = heapElements.get(heapIndex - 1).getKey();
        int parentIndex = heapIndex / 2;

        while (heapIndex > 1 && getKeyAt(parentIndex) < currentKey) {
            swap(heapIndex, parentIndex);
            heapIndex = parentIndex;
            parentIndex = heapIndex / 2;
        }
    }

    private void heapifyDownFrom(int heapIndex) {
        double currentKey = heapElements.get(heapIndex - 1).getKey();
        boolean shouldContinue =
            (2 * heapIndex <= heapElements.size() && currentKey < getKeyAt(heapIndex * 2)) ||
            (2 * heapIndex + 1 <= heapElements.size() && currentKey < getKeyAt(heapIndex * 2 + 1));

        while (2 * heapIndex <= heapElements.size() && shouldContinue) {
            int largerChildIndex;
            int leftChildIndex = heapIndex * 2;
            int rightChildIndex = heapIndex * 2 + 1;

            if (rightChildIndex <= heapElements.size() && getKeyAt(rightChildIndex) > getKeyAt(leftChildIndex)) {
                largerChildIndex = rightChildIndex;
            } else {
                largerChildIndex = leftChildIndex;
            }

            swap(heapIndex, largerChildIndex);
            heapIndex = largerChildIndex;

            shouldContinue =
                (2 * heapIndex <= heapElements.size() && currentKey < getKeyAt(heapIndex * 2)) ||
                (2 * heapIndex + 1 <= heapElements.size() && currentKey < getKeyAt(heapIndex * 2 + 1));
        }
    }

    private HeapElement extractMaxInternal() throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = heapElements.getFirst();
        deleteAt(1);
        return maxElement;
    }

    @Override
    public void insert(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        heapElements.add(element);
        heapifyUp(heapElements.size());
    }

    @Override
    public void deleteAt(int heapIndex) throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateHeapIndex(heapIndex);

        heapElements.set(heapIndex - 1, heapElements.getLast());
        heapElements.removeLast();

        if (!heapElements.isEmpty() && heapIndex <= heapElements.size()) {
            int parentIndex = heapIndex / 2;
            if (heapIndex > 1 && getKeyAt(heapIndex) > getKeyAt(parentIndex)) {
                heapifyUp(heapIndex);
            } else {
                heapifyDownFrom(heapIndex);
            }
        }
    }

    @Override
    public HeapElement extractMax() throws EmptyHeapException {
        return extractMaxInternal();
    }

    public int size() {
        return heapElements.size();
    }

    public boolean isEmpty() {
        return heapElements.isEmpty();
    }

    private void validateHeapIndex(int heapIndex) {
        if (heapIndex <= 0 || heapIndex > heapElements.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + heapElements.size() + "]"
            );
        }
    }
}
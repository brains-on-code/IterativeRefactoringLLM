package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class BinaryMaxHeap implements Heap {

    private final List<HeapElement> elements;

    public BinaryMaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        elements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                elements.add(element);
            }
        }

        for (int heapIndex = elements.size() / 2; heapIndex >= 0; heapIndex--) {
            heapifyDown(heapIndex + 1);
        }
    }

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

    public HeapElement getElementAt(int heapIndex) {
        if ((heapIndex <= 0) || (heapIndex > elements.size())) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }
        return elements.get(heapIndex - 1);
    }

    private double getKeyAt(int heapIndex) {
        if ((heapIndex <= 0) || (heapIndex > elements.size())) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }
        return elements.get(heapIndex - 1).getKey();
    }

    private void swap(int firstHeapIndex, int secondHeapIndex) {
        HeapElement temp = elements.get(firstHeapIndex - 1);
        elements.set(firstHeapIndex - 1, elements.get(secondHeapIndex - 1));
        elements.set(secondHeapIndex - 1, temp);
    }

    private void heapifyUp(int heapIndex) {
        double currentKey = elements.get(heapIndex - 1).getKey();
        int parentIndex = (int) Math.floor(heapIndex / 2.0);

        while (heapIndex > 1 && getKeyAt(parentIndex) < currentKey) {
            swap(heapIndex, parentIndex);
            heapIndex = parentIndex;
            parentIndex = (int) Math.floor(heapIndex / 2.0);
        }
    }

    private void heapifyDownFrom(int heapIndex) {
        double currentKey = elements.get(heapIndex - 1).getKey();
        boolean shouldContinue =
            (2 * heapIndex <= elements.size() && currentKey < getKeyAt(heapIndex * 2)) ||
            (2 * heapIndex + 1 <= elements.size() && currentKey < getKeyAt(heapIndex * 2 + 1));

        while (2 * heapIndex <= elements.size() && shouldContinue) {
            int largerChildIndex;
            int leftChildIndex = heapIndex * 2;
            int rightChildIndex = heapIndex * 2 + 1;

            if (rightChildIndex <= elements.size() && getKeyAt(rightChildIndex) > getKeyAt(leftChildIndex)) {
                largerChildIndex = rightChildIndex;
            } else {
                largerChildIndex = leftChildIndex;
            }

            swap(heapIndex, largerChildIndex);
            heapIndex = largerChildIndex;

            shouldContinue =
                (2 * heapIndex <= elements.size() && currentKey < getKeyAt(heapIndex * 2)) ||
                (2 * heapIndex + 1 <= elements.size() && currentKey < getKeyAt(heapIndex * 2 + 1));
        }
    }

    private HeapElement extractMaxInternal() throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = elements.getFirst();
        deleteAt(1);
        return maxElement;
    }

    @Override
    public void insert(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        elements.add(element);
        heapifyUp(elements.size());
    }

    @Override
    public void deleteAt(int heapIndex) throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        if ((heapIndex > elements.size()) || (heapIndex <= 0)) {
            throw new IndexOutOfBoundsException(
                "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]"
            );
        }

        elements.set(heapIndex - 1, elements.getLast());
        elements.removeLast();

        if (!elements.isEmpty() && heapIndex <= elements.size()) {
            int parentIndex = (int) Math.floor(heapIndex / 2.0);
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
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
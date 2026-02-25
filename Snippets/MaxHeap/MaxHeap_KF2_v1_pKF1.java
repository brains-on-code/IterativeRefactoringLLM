package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap implements Heap {

    private final List<HeapElement> elements;

    public MaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        elements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                elements.add(element);
            }
        }

        for (int i = elements.size() / 2; i >= 0; i--) {
            heapifyDown(i + 1);
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

    public HeapElement getElement(int heapIndex) {
        validateIndex(heapIndex);
        return elements.get(heapIndex - 1);
    }

    private double getElementKey(int heapIndex) {
        validateIndex(heapIndex);
        return elements.get(heapIndex - 1).getKey();
    }

    private void swap(int firstIndex, int secondIndex) {
        HeapElement temp = elements.get(firstIndex - 1);
        elements.set(firstIndex - 1, elements.get(secondIndex - 1));
        elements.set(secondIndex - 1, temp);
    }

    private void bubbleUp(int heapIndex) {
        double key = elements.get(heapIndex - 1).getKey();
        int parentIndex = heapIndex / 2;

        while (heapIndex > 1 && getElementKey(parentIndex) < key) {
            swap(heapIndex, parentIndex);
            heapIndex = parentIndex;
            parentIndex = heapIndex / 2;
        }
    }

    private void bubbleDown(int heapIndex) {
        double key = elements.get(heapIndex - 1).getKey();
        boolean isInWrongOrder =
                (2 * heapIndex <= elements.size() && key < getElementKey(heapIndex * 2))
                        || (2 * heapIndex + 1 <= elements.size() && key < getElementKey(heapIndex * 2 + 1));

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
                    (2 * heapIndex <= elements.size() && key < getElementKey(heapIndex * 2))
                            || (2 * heapIndex + 1 <= elements.size()
                                    && key < getElementKey(heapIndex * 2 + 1));
        }
    }

    private HeapElement extractMax() throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = elements.getFirst();
        deleteElement(1);
        return maxElement;
    }

    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        elements.add(element);
        bubbleUp(elements.size());
    }

    @Override
    public void deleteElement(int heapIndex) throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateIndex(heapIndex);

        elements.set(heapIndex - 1, elements.getLast());
        elements.removeLast();

        if (!elements.isEmpty() && heapIndex <= elements.size()) {
            int parentIndex = heapIndex / 2;
            if (heapIndex > 1 && getElementKey(heapIndex) > getElementKey(parentIndex)) {
                bubbleUp(heapIndex);
            } else {
                bubbleDown(heapIndex);
            }
        }
    }

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private void validateIndex(int heapIndex) {
        if (heapIndex <= 0 || heapIndex > elements.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + heapIndex + " is out of heap range [1, " + elements.size() + "]");
        }
    }
}
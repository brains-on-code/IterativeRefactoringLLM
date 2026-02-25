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

        for (int index = elements.size() / 2; index >= 0; index--) {
            heapifyDown(index + 1);
        }
    }

    private void heapifyDown(int index) {
        int largestIndex = index - 1;
        int leftChildIndex = 2 * index - 1;
        int rightChildIndex = 2 * index;

        if (leftChildIndex < elements.size() && elements.get(leftChildIndex).getKey() > elements.get(largestIndex).getKey()) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < elements.size() && elements.get(rightChildIndex).getKey() > elements.get(largestIndex).getKey()) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != index - 1) {
            HeapElement temp = elements.get(index - 1);
            elements.set(index - 1, elements.get(largestIndex));
            elements.set(largestIndex, temp);

            heapifyDown(largestIndex + 1);
        }
    }

    public HeapElement getElementAt(int index) {
        if ((index <= 0) || (index > elements.size())) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of heap range [1, " + elements.size() + "]");
        }
        return elements.get(index - 1);
    }

    private double getKeyAt(int index) {
        if ((index <= 0) || (index > elements.size())) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of heap range [1, " + elements.size() + "]");
        }
        return elements.get(index - 1).getKey();
    }

    private void swap(int firstIndex, int secondIndex) {
        HeapElement temp = elements.get(firstIndex - 1);
        elements.set(firstIndex - 1, elements.get(secondIndex - 1));
        elements.set(secondIndex - 1, temp);
    }

    private void heapifyUp(int index) {
        double key = elements.get(index - 1).getKey();
        while (index > 1 && getKeyAt((int) Math.floor(index / 2.0)) < key) {
            swap(index, (int) Math.floor(index / 2.0));
            index = (int) Math.floor(index / 2.0);
        }
    }

    private void heapifyDownFrom(int index) {
        double key = elements.get(index - 1).getKey();
        boolean shouldContinue =
            (2 * index <= elements.size() && key < getKeyAt(index * 2)) ||
            (2 * index + 1 <= elements.size() && key < getKeyAt(index * 2 + 1));

        while (2 * index <= elements.size() && shouldContinue) {
            int largerChildIndex;
            if (2 * index + 1 <= elements.size() && getKeyAt(index * 2 + 1) > getKeyAt(index * 2)) {
                largerChildIndex = 2 * index + 1;
            } else {
                largerChildIndex = 2 * index;
            }

            swap(index, largerChildIndex);
            index = largerChildIndex;

            shouldContinue =
                (2 * index <= elements.size() && key < getKeyAt(index * 2)) ||
                (2 * index + 1 <= elements.size() && key < getKeyAt(index * 2 + 1));
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
    public void deleteAt(int index) throws EmptyHeapException {
        if (elements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        if ((index > elements.size()) || (index <= 0)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of heap range [1, " + elements.size() + "]");
        }

        elements.set(index - 1, elements.getLast());
        elements.removeLast();

        if (!elements.isEmpty() && index <= elements.size()) {
            if (index > 1 && getKeyAt(index) > getKeyAt((int) Math.floor(index / 2.0))) {
                heapifyUp(index);
            } else {
                heapifyDownFrom(index);
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
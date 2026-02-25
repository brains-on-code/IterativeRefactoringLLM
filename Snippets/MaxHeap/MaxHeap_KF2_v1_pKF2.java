package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap implements Heap {

    private final List<HeapElement> maxHeap;

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

    private void buildHeap() {
        for (int i = parentIndex(maxHeap.size()); i >= 1; i--) {
            heapifyDown(i);
        }
    }

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

    private int parentIndex(int index) {
        return index / 2;
    }

    private int leftChildIndex(int index) {
        return 2 * index;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 1;
    }

    private void validateIndex(int index) {
        if (index <= 0 || index > maxHeap.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is out of heap range [1, " + maxHeap.size() + "]"
            );
        }
    }

    public HeapElement getElement(int index) {
        validateIndex(index);
        return maxHeap.get(index - 1);
    }

    private double getElementKey(int index) {
        validateIndex(index);
        return maxHeap.get(index - 1).getKey();
    }

    private void swap(int index1, int index2) {
        HeapElement temp = maxHeap.get(index1 - 1);
        maxHeap.set(index1 - 1, maxHeap.get(index2 - 1));
        maxHeap.set(index2 - 1, temp);
    }

    private void toggleUp(int index) {
        double key = getElementKey(index);
        int parent = parentIndex(index);

        while (index > 1 && getElementKey(parent) < key) {
            swap(index, parent);
            index = parent;
            parent = parentIndex(index);
        }
    }

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

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    public int size() {
        return maxHeap.size();
    }

    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }
}
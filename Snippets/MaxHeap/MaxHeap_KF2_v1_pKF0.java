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
        for (HeapElement element : listElements) {
            if (element != null) {
                maxHeap.add(element);
            }
        }

        buildHeap();
    }

    private void buildHeap() {
        for (int i = parentIndex(maxHeap.size() - 1); i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void heapifyDown(int index) {
        int largest = index;
        int left = leftChildIndex(index);
        int right = rightChildIndex(index);

        if (left < maxHeap.size() && maxHeap.get(left).getKey() > maxHeap.get(largest).getKey()) {
            largest = left;
        }

        if (right < maxHeap.size() && maxHeap.get(right).getKey() > maxHeap.get(largest).getKey()) {
            largest = right;
        }

        if (largest != index) {
            swapByZeroBasedIndex(index, largest);
            heapifyDown(largest);
        }
    }

    public HeapElement getElement(int elementIndex) {
        validateOneBasedIndex(elementIndex);
        return maxHeap.get(elementIndex - 1);
    }

    private double getElementKey(int elementIndex) {
        validateOneBasedIndex(elementIndex);
        return maxHeap.get(elementIndex - 1).getKey();
    }

    private void validateOneBasedIndex(int elementIndex) {
        if (elementIndex <= 0 || elementIndex > maxHeap.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + elementIndex + " is out of heap range [1, " + maxHeap.size() + "]"
            );
        }
    }

    private void swap(int index1, int index2) {
        swapByZeroBasedIndex(index1 - 1, index2 - 1);
    }

    private void swapByZeroBasedIndex(int index1, int index2) {
        HeapElement temp = maxHeap.get(index1);
        maxHeap.set(index1, maxHeap.get(index2));
        maxHeap.set(index2, temp);
    }

    private void toggleUp(int elementIndex) {
        int currentIndex = elementIndex;
        double key = maxHeap.get(currentIndex - 1).getKey();

        while (currentIndex > 1) {
            int parent = parentIndex(currentIndex - 1) + 1;
            if (getElementKey(parent) >= key) {
                break;
            }
            swap(currentIndex, parent);
            currentIndex = parent;
        }
    }

    private void toggleDown(int elementIndex) {
        int currentIndex = elementIndex;
        double key = maxHeap.get(currentIndex - 1).getKey();

        while (true) {
            int left = 2 * currentIndex;
            int right = 2 * currentIndex + 1;

            boolean hasLeft = left <= maxHeap.size();
            boolean hasRight = right <= maxHeap.size();

            boolean wrongOrder =
                (hasLeft && key < getElementKey(left)) ||
                (hasRight && key < getElementKey(right));

            if (!hasLeft || !wrongOrder) {
                break;
            }

            int largerChildIndex;
            if (hasRight && getElementKey(right) > getElementKey(left)) {
                largerChildIndex = right;
            } else {
                largerChildIndex = left;
            }

            swap(currentIndex, largerChildIndex);
            currentIndex = largerChildIndex;
        }
    }

    private HeapElement extractMax() throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement result = maxHeap.get(0);
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
        validateOneBasedIndex(elementIndex);

        int lastIndex = maxHeap.size() - 1;
        maxHeap.set(elementIndex - 1, maxHeap.get(lastIndex));
        maxHeap.remove(lastIndex);

        if (!maxHeap.isEmpty() && elementIndex <= maxHeap.size()) {
            int parent = parentIndex(elementIndex - 1) + 1;
            if (elementIndex > 1 && getElementKey(elementIndex) > getElementKey(parent)) {
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

    public int size() {
        return maxHeap.size();
    }

    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }
}
package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;


public class MaxHeap implements Heap {


    private final List<HeapElement> maxHeap;


    public MaxHeap(List<HeapElement> listElements) {
        if (listElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        maxHeap = new ArrayList<>();

        for (HeapElement heapElement : listElements) {
            if (heapElement != null) {
                maxHeap.add(heapElement);
            }
        }

        for (int i = maxHeap.size() / 2; i >= 0; i--) {
            heapifyDown(i + 1);
        }
    }


    private void heapifyDown(int elementIndex) {
        int largest = elementIndex - 1;
        int leftChild = 2 * elementIndex - 1;
        int rightChild = 2 * elementIndex;

        if (leftChild < maxHeap.size() && maxHeap.get(leftChild).getKey() > maxHeap.get(largest).getKey()) {
            largest = leftChild;
        }

        if (rightChild < maxHeap.size() && maxHeap.get(rightChild).getKey() > maxHeap.get(largest).getKey()) {
            largest = rightChild;
        }

        if (largest != elementIndex - 1) {
            HeapElement swap = maxHeap.get(elementIndex - 1);
            maxHeap.set(elementIndex - 1, maxHeap.get(largest));
            maxHeap.set(largest, swap);

            heapifyDown(largest + 1);
        }
    }


    public HeapElement getElement(int elementIndex) {
        if ((elementIndex <= 0) || (elementIndex > maxHeap.size())) {
            throw new IndexOutOfBoundsException("Index " + elementIndex + " is out of heap range [1, " + maxHeap.size() + "]");
        }
        return maxHeap.get(elementIndex - 1);
    }


    private double getElementKey(int elementIndex) {
        if ((elementIndex <= 0) || (elementIndex > maxHeap.size())) {
            throw new IndexOutOfBoundsException("Index " + elementIndex + " is out of heap range [1, " + maxHeap.size() + "]");
        }
        return maxHeap.get(elementIndex - 1).getKey();
    }


    private void swap(int index1, int index2) {
        HeapElement temporaryElement = maxHeap.get(index1 - 1);
        maxHeap.set(index1 - 1, maxHeap.get(index2 - 1));
        maxHeap.set(index2 - 1, temporaryElement);
    }


    private void toggleUp(int elementIndex) {
        double key = maxHeap.get(elementIndex - 1).getKey();
        while (elementIndex > 1 && getElementKey((int) Math.floor(elementIndex / 2.0)) < key) {
            swap(elementIndex, (int) Math.floor(elementIndex / 2.0));
            elementIndex = (int) Math.floor(elementIndex / 2.0);
        }
    }


    private void toggleDown(int elementIndex) {
        double key = maxHeap.get(elementIndex - 1).getKey();
        boolean wrongOrder = (2 * elementIndex <= maxHeap.size() && key < getElementKey(elementIndex * 2)) || (2 * elementIndex + 1 <= maxHeap.size() && key < getElementKey(elementIndex * 2 + 1));

        while (2 * elementIndex <= maxHeap.size() && wrongOrder) {
            int largerChildIndex;
            if (2 * elementIndex + 1 <= maxHeap.size() && getElementKey(elementIndex * 2 + 1) > getElementKey(elementIndex * 2)) {
                largerChildIndex = 2 * elementIndex + 1;
            } else {
                largerChildIndex = 2 * elementIndex;
            }

            swap(elementIndex, largerChildIndex);
            elementIndex = largerChildIndex;

            wrongOrder = (2 * elementIndex <= maxHeap.size() && key < getElementKey(elementIndex * 2)) || (2 * elementIndex + 1 <= maxHeap.size() && key < getElementKey(elementIndex * 2 + 1));
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
    public void deleteElement(int elementIndex) throws EmptyHeapException {
        if (maxHeap.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        if ((elementIndex > maxHeap.size()) || (elementIndex <= 0)) {
            throw new IndexOutOfBoundsException("Index " + elementIndex + " is out of heap range [1, " + maxHeap.size() + "]");
        }

        maxHeap.set(elementIndex - 1, maxHeap.getLast());
        maxHeap.removeLast();

        if (!maxHeap.isEmpty() && elementIndex <= maxHeap.size()) {
            if (elementIndex > 1 && getElementKey(elementIndex) > getElementKey((int) Math.floor(elementIndex / 2.0))) {
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

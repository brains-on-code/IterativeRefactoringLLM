package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap implements Heap {

    private final List<HeapElement> heapElements;

    public MaxHeap(List<HeapElement> initialElements) {
        if (initialElements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.heapElements = new ArrayList<>();

        for (HeapElement element : initialElements) {
            if (element != null) {
                heapElements.add(element);
            }
        }

        for (int currentIndex = heapElements.size() / 2; currentIndex >= 0; currentIndex--) {
            heapifyDown(currentIndex + 1);
        }
    }

    private void heapifyDown(int heapPosition) {
        int largestPosition = heapPosition - 1;
        int leftChildPosition = 2 * heapPosition - 1;
        int rightChildPosition = 2 * heapPosition;

        if (leftChildPosition < heapElements.size()
                && heapElements.get(leftChildPosition).getKey() > heapElements.get(largestPosition).getKey()) {
            largestPosition = leftChildPosition;
        }

        if (rightChildPosition < heapElements.size()
                && heapElements.get(rightChildPosition).getKey() > heapElements.get(largestPosition).getKey()) {
            largestPosition = rightChildPosition;
        }

        if (largestPosition != heapPosition - 1) {
            HeapElement temporaryElement = heapElements.get(heapPosition - 1);
            heapElements.set(heapPosition - 1, heapElements.get(largestPosition));
            heapElements.set(largestPosition, temporaryElement);

            heapifyDown(largestPosition + 1);
        }
    }

    public HeapElement getElement(int heapPosition) {
        validateHeapPosition(heapPosition);
        return heapElements.get(heapPosition - 1);
    }

    private double getElementKey(int heapPosition) {
        validateHeapPosition(heapPosition);
        return heapElements.get(heapPosition - 1).getKey();
    }

    private void swap(int firstHeapPosition, int secondHeapPosition) {
        HeapElement temporaryElement = heapElements.get(firstHeapPosition - 1);
        heapElements.set(firstHeapPosition - 1, heapElements.get(secondHeapPosition - 1));
        heapElements.set(secondHeapPosition - 1, temporaryElement);
    }

    private void bubbleUp(int heapPosition) {
        double currentKey = heapElements.get(heapPosition - 1).getKey();
        int parentPosition = heapPosition / 2;

        while (heapPosition > 1 && getElementKey(parentPosition) < currentKey) {
            swap(heapPosition, parentPosition);
            heapPosition = parentPosition;
            parentPosition = heapPosition / 2;
        }
    }

    private void bubbleDown(int heapPosition) {
        double currentKey = heapElements.get(heapPosition - 1).getKey();
        boolean isInWrongOrder =
                (2 * heapPosition <= heapElements.size() && currentKey < getElementKey(heapPosition * 2))
                        || (2 * heapPosition + 1 <= heapElements.size()
                                && currentKey < getElementKey(heapPosition * 2 + 1));

        while (2 * heapPosition <= heapElements.size() && isInWrongOrder) {
            int largerChildPosition;
            int leftChildPosition = heapPosition * 2;
            int rightChildPosition = heapPosition * 2 + 1;

            if (rightChildPosition <= heapElements.size()
                    && getElementKey(rightChildPosition) > getElementKey(leftChildPosition)) {
                largerChildPosition = rightChildPosition;
            } else {
                largerChildPosition = leftChildPosition;
            }

            swap(heapPosition, largerChildPosition);
            heapPosition = largerChildPosition;

            isInWrongOrder =
                    (2 * heapPosition <= heapElements.size() && currentKey < getElementKey(heapPosition * 2))
                            || (2 * heapPosition + 1 <= heapElements.size()
                                    && currentKey < getElementKey(heapPosition * 2 + 1));
        }
    }

    private HeapElement extractMax() throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement maxElement = heapElements.getFirst();
        deleteElement(1);
        return maxElement;
    }

    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        heapElements.add(element);
        bubbleUp(heapElements.size());
    }

    @Override
    public void deleteElement(int heapPosition) throws EmptyHeapException {
        if (heapElements.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateHeapPosition(heapPosition);

        heapElements.set(heapPosition - 1, heapElements.getLast());
        heapElements.removeLast();

        if (!heapElements.isEmpty() && heapPosition <= heapElements.size()) {
            int parentPosition = heapPosition / 2;
            if (heapPosition > 1 && getElementKey(heapPosition) > getElementKey(parentPosition)) {
                bubbleUp(heapPosition);
            } else {
                bubbleDown(heapPosition);
            }
        }
    }

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    public int size() {
        return heapElements.size();
    }

    public boolean isEmpty() {
        return heapElements.isEmpty();
    }

    private void validateHeapPosition(int heapPosition) {
        if (heapPosition <= 0 || heapPosition > heapElements.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + heapPosition + " is out of heap range [1, " + heapElements.size() + "]");
        }
    }
}
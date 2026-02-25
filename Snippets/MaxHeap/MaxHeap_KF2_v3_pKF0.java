package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap implements Heap {

    private final List<HeapElement> heap;

    public MaxHeap(List<HeapElement> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Input list cannot be null");
        }

        this.heap = new ArrayList<>();
        for (HeapElement element : elements) {
            if (element != null) {
                heap.add(element);
            }
        }

        buildHeap();
    }

    private void buildHeap() {
        for (int i = parentIndex(heap.size() - 1); i >= 0; i--) {
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

        if (left < heap.size() && heap.get(left).getKey() > heap.get(largest).getKey()) {
            largest = left;
        }

        if (right < heap.size() && heap.get(right).getKey() > heap.get(largest).getKey()) {
            largest = right;
        }

        if (largest != index) {
            swapZeroBased(index, largest);
            heapifyDown(largest);
        }
    }

    public HeapElement getElement(int oneBasedIndex) {
        validateOneBasedIndex(oneBasedIndex);
        return heap.get(toZeroBasedIndex(oneBasedIndex));
    }

    private double getElementKey(int oneBasedIndex) {
        validateOneBasedIndex(oneBasedIndex);
        return heap.get(toZeroBasedIndex(oneBasedIndex)).getKey();
    }

    private void validateOneBasedIndex(int oneBasedIndex) {
        if (oneBasedIndex <= 0 || oneBasedIndex > heap.size()) {
            throw new IndexOutOfBoundsException(
                "Index " + oneBasedIndex + " is out of heap range [1, " + heap.size() + "]"
            );
        }
    }

    private int toZeroBasedIndex(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }

    private void swap(int firstOneBasedIndex, int secondOneBasedIndex) {
        swapZeroBased(toZeroBasedIndex(firstOneBasedIndex), toZeroBasedIndex(secondOneBasedIndex));
    }

    private void swapZeroBased(int firstZeroBasedIndex, int secondZeroBasedIndex) {
        HeapElement temp = heap.get(firstZeroBasedIndex);
        heap.set(firstZeroBasedIndex, heap.get(secondZeroBasedIndex));
        heap.set(secondZeroBasedIndex, temp);
    }

    private void toggleUp(int oneBasedIndex) {
        int currentIndex = oneBasedIndex;
        double currentKey = getElementKey(currentIndex);

        while (currentIndex > 1) {
            int parentOneBasedIndex = parentIndex(toZeroBasedIndex(currentIndex)) + 1;
            if (getElementKey(parentOneBasedIndex) >= currentKey) {
                break;
            }
            swap(currentIndex, parentOneBasedIndex);
            currentIndex = parentOneBasedIndex;
        }
    }

    private void toggleDown(int oneBasedIndex) {
        int currentIndex = oneBasedIndex;
        double currentKey = getElementKey(currentIndex);

        while (true) {
            int leftChild = 2 * currentIndex;
            int rightChild = 2 * currentIndex + 1;

            boolean hasLeft = leftChild <= heap.size();
            boolean hasRight = rightChild <= heap.size();

            if (!hasLeft) {
                break;
            }

            boolean violatesHeap =
                (hasLeft && currentKey < getElementKey(leftChild)) ||
                (hasRight && currentKey < getElementKey(rightChild));

            if (!violatesHeap) {
                break;
            }

            int largerChildIndex =
                hasRight && getElementKey(rightChild) > getElementKey(leftChild)
                    ? rightChild
                    : leftChild;

            swap(currentIndex, largerChildIndex);
            currentIndex = largerChildIndex;
        }
    }

    private HeapElement extractMax() throws EmptyHeapException {
        if (heap.isEmpty()) {
            throw new EmptyHeapException("Cannot extract from an empty heap");
        }
        HeapElement max = heap.get(0);
        deleteElement(1);
        return max;
    }

    @Override
    public void insertElement(HeapElement element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        heap.add(element);
        toggleUp(heap.size());
    }

    @Override
    public void deleteElement(int oneBasedIndex) throws EmptyHeapException {
        if (heap.isEmpty()) {
            throw new EmptyHeapException("Cannot delete from an empty heap");
        }
        validateOneBasedIndex(oneBasedIndex);

        int lastZeroBasedIndex = heap.size() - 1;
        int targetZeroBasedIndex = toZeroBasedIndex(oneBasedIndex);

        heap.set(targetZeroBasedIndex, heap.get(lastZeroBasedIndex));
        heap.remove(lastZeroBasedIndex);

        if (!heap.isEmpty() && oneBasedIndex <= heap.size()) {
            int parentOneBasedIndex = parentIndex(targetZeroBasedIndex) + 1;
            if (oneBasedIndex > 1 && getElementKey(oneBasedIndex) > getElementKey(parentOneBasedIndex)) {
                toggleUp(oneBasedIndex);
            } else {
                toggleDown(oneBasedIndex);
            }
        }
    }

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        return extractMax();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
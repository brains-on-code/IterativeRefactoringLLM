package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private final List<Integer> inputList;

    public MergeSort(List<Integer> inputList) {
        this.inputList = inputList;
    }

    public List<Integer> sort() {
        return mergeSort(inputList);
    }

    private static List<Integer> mergeSort(List<Integer> listToSort) {
        if (listToSort.size() <= 1) {
            return listToSort;
        }

        int listSize = listToSort.size();
        int middleIndex = listSize / 2;

        List<Integer> leftHalf = new ArrayList<>(listToSort.subList(0, middleIndex));
        List<Integer> rightHalf = new ArrayList<>(listToSort.subList(middleIndex, listSize));

        List<Integer> sortedLeftHalf = mergeSort(leftHalf);
        List<Integer> sortedRightHalf = mergeSort(rightHalf);

        return merge(sortedLeftHalf, sortedRightHalf);
    }

    private static List<Integer> merge(List<Integer> leftList, List<Integer> rightList) {
        List<Integer> mergedResult = new ArrayList<>();

        int leftPointer = 0;
        int rightPointer = 0;

        while (leftPointer < leftList.size() && rightPointer < rightList.size()) {
            if (leftList.get(leftPointer) <= rightList.get(rightPointer)) {
                mergedResult.add(leftList.get(leftPointer));
                leftPointer++;
            } else {
                mergedResult.add(rightList.get(rightPointer));
                rightPointer++;
            }
        }

        while (leftPointer < leftList.size()) {
            mergedResult.add(leftList.get(leftPointer));
            leftPointer++;
        }

        while (rightPointer < rightList.size()) {
            mergedResult.add(rightList.get(rightPointer));
            rightPointer++;
        }

        return mergedResult;
    }
}
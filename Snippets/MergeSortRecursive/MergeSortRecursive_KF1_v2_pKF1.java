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

    private static List<Integer> mergeSort(List<Integer> values) {
        if (values.size() <= 1) {
            return values;
        }

        int listSize = values.size();
        int middleIndex = listSize / 2;

        List<Integer> leftHalf = new ArrayList<>(values.subList(0, middleIndex));
        List<Integer> rightHalf = new ArrayList<>(values.subList(middleIndex, listSize));

        List<Integer> sortedLeft = mergeSort(leftHalf);
        List<Integer> sortedRight = mergeSort(rightHalf);

        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> leftValues, List<Integer> rightValues) {
        List<Integer> mergedValues = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftValues.size() && rightIndex < rightValues.size()) {
            if (leftValues.get(leftIndex) <= rightValues.get(rightIndex)) {
                mergedValues.add(leftValues.get(leftIndex));
                leftIndex++;
            } else {
                mergedValues.add(rightValues.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < leftValues.size()) {
            mergedValues.add(leftValues.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < rightValues.size()) {
            mergedValues.add(rightValues.get(rightIndex));
            rightIndex++;
        }

        return mergedValues;
    }
}
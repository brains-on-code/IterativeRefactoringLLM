package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private final List<Integer> numbers;

    public MergeSort(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> sort() {
        return mergeSort(numbers);
    }

    private static List<Integer> mergeSort(List<Integer> values) {
        if (values.size() <= 1) {
            return values;
        }

        int size = values.size();
        int middle = size / 2;

        List<Integer> leftSublist = new ArrayList<>(values.subList(0, middle));
        List<Integer> rightSublist = new ArrayList<>(values.subList(middle, size));

        List<Integer> sortedLeft = mergeSort(leftSublist);
        List<Integer> sortedRight = mergeSort(rightSublist);

        return merge(sortedLeft, sortedRight);
    }

    private static List<Integer> merge(List<Integer> leftList, List<Integer> rightList) {
        List<Integer> mergedList = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            if (leftList.get(leftIndex) <= rightList.get(rightIndex)) {
                mergedList.add(leftList.get(leftIndex));
                leftIndex++;
            } else {
                mergedList.add(rightList.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < leftList.size()) {
            mergedList.add(leftList.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < rightList.size()) {
            mergedList.add(rightList.get(rightIndex));
            rightIndex++;
        }

        return mergedList;
    }
}
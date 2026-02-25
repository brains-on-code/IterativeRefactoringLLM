package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> inputList;

    public MergeSortRecursive(List<Integer> inputList) {
        this.inputList = inputList;
    }

    public List<Integer> mergeSort() {
        return mergeSortRecursive(inputList);
    }

    private static List<Integer> mergeSortRecursive(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }

        int length = list.size();
        int midIndex = length / 2;

        List<Integer> leftSubList = list.subList(0, midIndex);
        List<Integer> rightSubList = list.subList(midIndex, length);

        List<Integer> sortedLeft = mergeSortRecursive(leftSubList);
        List<Integer> sortedRight = mergeSortRecursive(rightSubList);

        return mergeSortedLists(sortedLeft, sortedRight);
    }

    private static List<Integer> mergeSortedLists(List<Integer> leftList, List<Integer> rightList) {
        if (leftList.isEmpty() && rightList.isEmpty()) {
            return new ArrayList<>();
        }
        if (leftList.isEmpty()) {
            return rightList;
        }
        if (rightList.isEmpty()) {
            return leftList;
        }

        if (leftList.get(0) <= rightList.get(0)) {
            List<Integer> mergedList = new ArrayList<>();
            mergedList.add(leftList.get(0));
            mergedList.addAll(mergeSortedLists(leftList.subList(1, leftList.size()), rightList));
            return mergedList;
        } else {
            List<Integer> mergedList = new ArrayList<>();
            mergedList.add(rightList.get(0));
            mergedList.addAll(mergeSortedLists(leftList, rightList.subList(1, rightList.size())));
            return mergedList;
        }
    }
}
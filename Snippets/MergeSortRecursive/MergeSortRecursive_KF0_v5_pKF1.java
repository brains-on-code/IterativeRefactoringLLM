package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class MergeSortRecursive {

    private final List<Integer> inputList;

    public MergeSortRecursive(List<Integer> inputList) {
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

        List<Integer> leftSubList = listToSort.subList(0, middleIndex);
        List<Integer> rightSubList = listToSort.subList(middleIndex, listSize);

        List<Integer> sortedLeftSubList = mergeSort(leftSubList);
        List<Integer> sortedRightSubList = mergeSort(rightSubList);

        return merge(sortedLeftSubList, sortedRightSubList);
    }

    private static List<Integer> merge(List<Integer> leftList, List<Integer> rightList) {
        if (leftList.isEmpty() && rightList.isEmpty()) {
            return new ArrayList<>();
        }
        if (leftList.isEmpty()) {
            return rightList;
        }
        if (rightList.isEmpty()) {
            return leftList;
        }

        List<Integer> mergedList = new ArrayList<>();
        if (leftList.get(0) <= rightList.get(0)) {
            mergedList.add(leftList.get(0));
            mergedList.addAll(merge(leftList.subList(1, leftList.size()), rightList));
        } else {
            mergedList.add(rightList.get(0));
            mergedList.addAll(merge(leftList, rightList.subList(1, rightList.size())));
        }
        return mergedList;
    }
}
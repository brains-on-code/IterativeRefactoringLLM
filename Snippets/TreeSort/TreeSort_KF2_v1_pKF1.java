package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;

public class TreeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        return sortArrayUsingTree(unsortedArray);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> unsortedList) {
        return sortIterableUsingTree(unsortedList);
    }

    private <T extends Comparable<T>> T[] sortArrayUsingTree(T[] array) {
        BSTRecursiveGeneric<T> binarySearchTree = new BSTRecursiveGeneric<>();

        for (T element : array) {
            binarySearchTree.add(element);
        }

        List<T> sortedElements = binarySearchTree.inorderSort();

        int index = 0;
        for (T element : sortedElements) {
            array[index++] = element;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterableUsingTree(Iterable<T> elements) {
        BSTRecursiveGeneric<T> binarySearchTree = new BSTRecursiveGeneric<>();

        for (T element : elements) {
            binarySearchTree.add(element);
        }

        return binarySearchTree.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        System.out.println("Testing for Integer Array....");
        Integer[] integerArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(integerArray);
        integerArray = treeSort.sort(integerArray);
        System.out.printf("%-10s", "sorted: ");
        print(integerArray);
        System.out.println();

        System.out.println("Testing for Integer List....");
        List<Integer> integerList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(integerList);
        List<Integer> sortedIntegerList = treeSort.sort(integerList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedIntegerList);
        System.out.println();

        System.out.println("Testing for String Array....");
        String[] stringArray = {
            "banana",
            "berry",
            "orange",
            "grape",
            "peach",
            "cherry",
            "apple",
            "pineapple",
        };
        System.out.printf("%-10s", "unsorted: ");
        print(stringArray);
        stringArray = treeSort.sort(stringArray);
        System.out.printf("%-10s", "sorted: ");
        print(stringArray);
        System.out.println();

        System.out.println("Testing for String List....");
        List<String> stringList = List.of(
            "banana",
            "berry",
            "orange",
            "grape",
            "peach",
            "cherry",
            "apple",
            "pineapple"
        );
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        List<String> sortedStringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedStringList);
    }
}
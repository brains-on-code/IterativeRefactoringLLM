package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;

/**
 * Tree sort implementation using a recursive generic BST.
 */
public class TreeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sortArray(array);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> list) {
        return sortIterable(list);
    }

    private <T extends Comparable<T>> T[] sortArray(T[] array) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T element : array) {
            bst.add(element);
        }

        List<T> sortedList = bst.inorderSort();

        int index = 0;
        for (T element : sortedList) {
            array[index++] = element;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterable(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T element : iterable) {
            bst.add(element);
        }

        return bst.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        System.out.println("Testing for Integer Array....");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(intArray);
        intArray = treeSort.sort(intArray);
        System.out.printf("%-10s", "sorted: ");
        print(intArray);
        System.out.println();

        System.out.println("Testing for Integer List....");
        List<Integer> intList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        intList = treeSort.sort(intList);
        System.out.printf("%-10s", "sorted: ");
        print(intList);
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
        List<String> stringList =
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        stringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(stringList);
    }
}
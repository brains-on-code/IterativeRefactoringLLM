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

        for (T value : array) {
            bst.add(value);
        }

        List<T> sortedValues = bst.inorderSort();

        int index = 0;
        for (T value : sortedValues) {
            array[index++] = value;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterable(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T value : iterable) {
            bst.add(value);
        }

        return bst.inorderSort();
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
        integerList = treeSort.sort(integerList);
        System.out.printf("%-10s", "sorted: ");
        print(integerList);
        System.out.println();

        System.out.println("Testing for String Array....");
        String[] fruitArray = {
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
        print(fruitArray);
        fruitArray = treeSort.sort(fruitArray);
        System.out.printf("%-10s", "sorted: ");
        print(fruitArray);
        System.out.println();

        System.out.println("Testing for String List....");
        List<String> fruitList =
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(fruitList);
        fruitList = treeSort.sort(fruitList);
        System.out.printf("%-10s", "sorted: ");
        print(fruitList);
    }
}
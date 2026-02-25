package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;

public class TreeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        return sortArrayWithTree(unsortedArray);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> unsortedList) {
        return sortIterableWithTree(unsortedList);
    }

    private <T extends Comparable<T>> T[] sortArrayWithTree(T[] array) {
        BSTRecursiveGeneric<T> tree = buildTreeFromIterable(array);
        List<T> sortedList = tree.inorderSort();

        int index = 0;
        for (T element : sortedList) {
            array[index++] = element;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterableWithTree(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> tree = buildTreeFromIterable(iterable);
        return tree.inorderSort();
    }

    private <T extends Comparable<T>> BSTRecursiveGeneric<T> buildTreeFromIterable(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<>();
        for (T element : iterable) {
            tree.add(element);
        }
        return tree;
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
        List<Integer> sortedIntList = treeSort.sort(intList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedIntList);
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
        List<String> sortedStringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedStringList);
    }
}
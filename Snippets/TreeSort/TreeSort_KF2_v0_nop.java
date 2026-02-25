package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;


public class TreeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        return doTreeSortArray(unsortedArray);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> unsortedList) {
        return doTreeSortList(unsortedList);
    }

    private <T extends Comparable<T>> T[] doTreeSortArray(T[] unsortedArray) {
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<T>();

        for (T element : unsortedArray) {
            tree.add(element);
        }

        List<T> sortedList = tree.inorderSort();

        int i = 0;
        for (T element : sortedList) {
            unsortedArray[i++] = element;
        }

        return unsortedArray;
    }

    private <T extends Comparable<T>> List<T> doTreeSortList(Iterable<T> unsortedList) {
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<T>();

        for (T element : unsortedList) {
            tree.add(element);
        }

        return tree.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        System.out.println("Testing for Integer Array....");
        Integer[] a = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(a);
        a = treeSort.sort(a);
        System.out.printf("%-10s", "sorted: ");
        print(a);
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
        String[] b = {
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
        print(b);
        b = treeSort.sort(b);
        System.out.printf("%-10s", "sorted: ");
        print(b);
        System.out.println();

        System.out.println("Testing for String List....");
        List<String> stringList = List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        stringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(stringList);
    }
}

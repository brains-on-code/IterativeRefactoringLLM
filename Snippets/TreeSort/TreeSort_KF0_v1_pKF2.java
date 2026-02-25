package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;

/**
 * Tree Sort implementation.
 *
 * <p>Builds a Binary Search Tree (BST) from the input and returns the elements
 * in sorted order via an inorder traversal.</p>
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Tree_sort</p>
 */
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
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<>();

        for (T element : unsortedArray) {
            tree.add(element);
        }

        List<T> sortedList = tree.inorderSort();

        int index = 0;
        for (T element : sortedList) {
            unsortedArray[index++] = element;
        }

        return unsortedArray;
    }

    private <T extends Comparable<T>> List<T> doTreeSortList(Iterable<T> unsortedList) {
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<>();

        for (T element : unsortedList) {
            tree.add(element);
        }

        return tree.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        // Integer array
        System.out.println("Testing for Integer Array....");
        Integer[] a = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(a);
        a = treeSort.sort(a);
        System.out.printf("%-10s", "sorted: ");
        print(a);
        System.out.println();

        // Integer list
        System.out.println("Testing for Integer List....");
        List<Integer> intList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        intList = treeSort.sort(intList);
        System.out.printf("%-10s", "sorted: ");
        print(intList);
        System.out.println();

        // String array
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

        // String list
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
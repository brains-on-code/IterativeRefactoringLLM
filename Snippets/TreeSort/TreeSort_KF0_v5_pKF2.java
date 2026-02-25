package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree Sort implementation using a Binary Search Tree (BST).
 *
 * <p>Algorithm:
 * <ol>
 *   <li>Insert all elements into a BST.</li>
 *   <li>Return the inorder traversal of the BST (which is sorted).</li>
 * </ol>
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Tree_sort</p>
 */
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
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<>();

        for (T element : array) {
            tree.add(element);
        }

        List<T> sortedElements = tree.inorderSort();

        int index = 0;
        for (T element : sortedElements) {
            array[index++] = element;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterableUsingTree(Iterable<T> elements) {
        BSTRecursiveGeneric<T> tree = new BSTRecursiveGeneric<>();

        for (T element : elements) {
            tree.add(element);
        }

        return tree.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        System.out.println("Testing Integer array:");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(intArray);
        Integer[] sortedIntArray = treeSort.sort(intArray);
        System.out.printf("%-10s", "sorted: ");
        print(sortedIntArray);
        System.out.println();

        System.out.println("Testing Integer list:");
        List<Integer> intList = new ArrayList<>(List.of(3, -7, 45, 1, 343, -5, 2, 9));
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        List<Integer> sortedIntList = treeSort.sort(intList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedIntList);
        System.out.println();

        System.out.println("Testing String array:");
        String[] stringArray = {
            "banana",
            "berry",
            "orange",
            "grape",
            "peach",
            "cherry",
            "apple",
            "pineapple"
        };
        System.out.printf("%-10s", "unsorted: ");
        print(stringArray);
        String[] sortedStringArray = treeSort.sort(stringArray);
        System.out.printf("%-10s", "sorted: ");
        print(sortedStringArray);
        System.out.println();

        System.out.println("Testing String list:");
        List<String> stringList = new ArrayList<>(
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple")
        );
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        List<String> sortedStringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(sortedStringList);
    }
}
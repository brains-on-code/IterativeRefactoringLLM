package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Implementation of the Tree Sort algorithm</h1>
 *
 * <p>
 * Tree Sort: A sorting algorithm which constructs a Binary Search Tree using
 * the unsorted data and then outputs the data by inorder traversal of the tree.
 *
 * Reference: https://en.wikipedia.org/wiki/Tree_sort
 * </p>
 *
 * @author Madhur Panwar (https://github.com/mdrpanwar)
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
        List<T> sortedList = sortIterableUsingTree(List.of(array));
        return sortedList.toArray(array);
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

        // ==== Integer Array =======
        System.out.println("Testing for Integer Array....");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        printLabeled("unsorted:", intArray);
        intArray = treeSort.sort(intArray);
        printLabeled("sorted:", intArray);
        System.out.println();

        // ==== Integer List =======
        System.out.println("Testing for Integer List....");
        List<Integer> intList = new ArrayList<>(List.of(3, -7, 45, 1, 343, -5, 2, 9));
        printLabeled("unsorted:", intList);
        intList = treeSort.sort(intList);
        printLabeled("sorted:", intList);
        System.out.println();

        // ==== String Array =======
        System.out.println("Testing for String Array....");
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
        printLabeled("unsorted:", stringArray);
        stringArray = treeSort.sort(stringArray);
        printLabeled("sorted:", stringArray);
        System.out.println();

        // ==== String List =======
        System.out.println("Testing for String List....");
        List<String> stringList = new ArrayList<>(
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple")
        );
        printLabeled("unsorted:", stringList);
        stringList = treeSort.sort(stringList);
        printLabeled("sorted:", stringList);
    }

    private static void printLabeled(String label, Object data) {
        System.out.printf("%-10s ", label);
        print(data);
    }
}
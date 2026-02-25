package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.List;

/**
 * <h1> Implementation of the Tree Sort algorithm</h1>
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
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sortArrayUsingTree(array);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> list) {
        return sortIterableUsingTree(list);
    }

    private <T extends Comparable<T>> T[] sortArrayUsingTree(T[] array) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T value : array) {
            bst.add(value);
        }

        List<T> sortedValues = bst.inorderSort();

        int i = 0;
        for (T value : sortedValues) {
            array[i++] = value;
        }

        return array;
    }

    private <T extends Comparable<T>> List<T> sortIterableUsingTree(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T value : iterable) {
            bst.add(value);
        }

        return bst.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        // ==== Integer Array =======
        System.out.println("Testing for Integer Array....");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(intArray);
        intArray = treeSort.sort(intArray);
        System.out.printf("%-10s", "sorted: ");
        print(intArray);
        System.out.println();

        // ==== Integer List =======
        System.out.println("Testing for Integer List....");
        List<Integer> intList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        intList = treeSort.sort(intList);
        System.out.printf("%-10s", "sorted: ");
        print(intList);
        System.out.println();

        // ==== String Array =======
        System.out.println("Testing for String Array....");
        String[] strArray = {
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
        print(strArray);
        strArray = treeSort.sort(strArray);
        System.out.printf("%-10s", "sorted: ");
        print(strArray);
        System.out.println();

        // ==== String List =======
        System.out.println("Testing for String List....");
        List<String> strList = List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(strList);
        strList = treeSort.sort(strList);
        System.out.printf("%-10s", "sorted: ");
        print(strList);
    }
}
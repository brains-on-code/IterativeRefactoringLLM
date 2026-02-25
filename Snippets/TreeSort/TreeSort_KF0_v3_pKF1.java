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
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        return sortArrayUsingTree(unsortedArray);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> unsortedList) {
        return sortIterableUsingTree(unsortedList);
    }

    private <T extends Comparable<T>> T[] sortArrayUsingTree(T[] unsortedArray) {
        BSTRecursiveGeneric<T> binarySearchTree = new BSTRecursiveGeneric<>();

        for (T element : unsortedArray) {
            binarySearchTree.add(element);
        }

        List<T> sortedElements = binarySearchTree.inorderSort();

        int index = 0;
        for (T element : sortedElements) {
            unsortedArray[index++] = element;
        }

        return unsortedArray;
    }

    private <T extends Comparable<T>> List<T> sortIterableUsingTree(Iterable<T> unsortedIterable) {
        BSTRecursiveGeneric<T> binarySearchTree = new BSTRecursiveGeneric<>();

        for (T element : unsortedIterable) {
            binarySearchTree.add(element);
        }

        return binarySearchTree.inorderSort();
    }

    public static void main(String[] args) {
        TreeSort treeSort = new TreeSort();

        // ==== Integer Array =======
        System.out.println("Testing for Integer Array....");
        Integer[] integerArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(integerArray);
        integerArray = treeSort.sort(integerArray);
        System.out.printf("%-10s", "sorted: ");
        print(integerArray);
        System.out.println();

        // ==== Integer List =======
        System.out.println("Testing for Integer List....");
        List<Integer> integerList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(integerList);
        integerList = treeSort.sort(integerList);
        System.out.printf("%-10s", "sorted: ");
        print(integerList);
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
            "pineapple",
        };
        System.out.printf("%-10s", "unsorted: ");
        print(stringArray);
        stringArray = treeSort.sort(stringArray);
        System.out.printf("%-10s", "sorted: ");
        print(stringArray);
        System.out.println();

        // ==== String List =======
        System.out.println("Testing for String List....");
        List<String> stringList = List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        stringList = treeSort.sort(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(stringList);
    }
}
package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree sort implementation using a recursive binary search tree.
 *
 * <p>Algorithm steps:
 * <ol>
 *   <li>Insert all elements into a binary search tree (BST).</li>
 *   <li>Traverse the BST in-order to obtain the elements in sorted order.</li>
 * </ol>
 */
public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method2(T[] array) {
        return sortArrayWithTree(array);
    }

    @Override
    public <T extends Comparable<T>> List<T> method2(List<T> list) {
        return sortIterableWithTree(list);
    }

    /**
     * Sorts an array using tree sort.
     *
     * @param array the array to sort
     * @param <T>   the element type
     * @return the same array instance, sorted in-place
     */
    private <T extends Comparable<T>> T[] sortArrayWithTree(T[] array) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T element : array) {
            bst.add(element);
        }

        List<T> sorted = bst.inorderSort();

        int index = 0;
        for (T element : sorted) {
            array[index++] = element;
        }

        return array;
    }

    /**
     * Sorts an {@link Iterable} using tree sort and returns the result as a list.
     *
     * @param iterable the elements to sort
     * @param <T>      the element type
     * @return a new list containing the sorted elements
     */
    private <T extends Comparable<T>> List<T> sortIterableWithTree(Iterable<T> iterable) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();

        for (T element : iterable) {
            bst.add(element);
        }

        return bst.inorderSort();
    }

    public static void method5(String[] args) {
        Class1 sorter = new Class1();

        System.out.println("Testing Integer array...");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        System.out.printf("%-10s", "unsorted: ");
        print(intArray);
        intArray = sorter.method2(intArray);
        System.out.printf("%-10s", "sorted: ");
        print(intArray);
        System.out.println();

        System.out.println("Testing Integer list...");
        List<Integer> intList = new ArrayList<>(List.of(3, -7, 45, 1, 343, -5, 2, 9));
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        intList = sorter.method2(intList);
        System.out.printf("%-10s", "sorted: ");
        print(intList);
        System.out.println();

        System.out.println("Testing String array...");
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
        stringArray = sorter.method2(stringArray);
        System.out.printf("%-10s", "sorted: ");
        print(stringArray);
        System.out.println();

        System.out.println("Testing String list...");
        List<String> stringList =
            new ArrayList<>(List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple"));
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        stringList = sorter.method2(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(stringList);
    }
}
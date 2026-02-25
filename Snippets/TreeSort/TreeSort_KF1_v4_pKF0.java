package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree sort implementation using {@link BSTRecursiveGeneric}.
 */
public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method2(T[] array) {
        return sortArray(array);
    }

    @Override
    public <T extends Comparable<T>> List<T> method2(List<T> list) {
        return sortIterable(list);
    }

    private <T extends Comparable<T>> T[] sortArray(T[] array) {
        BSTRecursiveGeneric<T> bst = new BSTRecursiveGeneric<>();
        for (T element : array) {
            bst.add(element);
        }

        List<T> sorted = bst.inorderSort();
        for (int i = 0; i < sorted.size(); i++) {
            array[i] = sorted.get(i);
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

    public static void method5(String[] args) {
        Class1 treeSort = new Class1();

        System.out.println("Testing for Integer Array....");
        Integer[] intArray = {3, -7, 45, 1, 343, -5, 2, 9};
        printLabeled("unsorted: ", intArray);
        intArray = treeSort.method2(intArray);
        printLabeled("sorted: ", intArray);
        System.out.println();

        System.out.println("Testing for Integer List....");
        List<Integer> intList = new ArrayList<>(List.of(3, -7, 45, 1, 343, -5, 2, 9));
        printLabeled("unsorted: ", intList);
        intList = treeSort.method2(intList);
        printLabeled("sorted: ", intList);
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
            "pineapple"
        };
        printLabeled("unsorted: ", stringArray);
        stringArray = treeSort.method2(stringArray);
        printLabeled("sorted: ", stringArray);
        System.out.println();

        System.out.println("Testing for String List....");
        List<String> stringList = new ArrayList<>(
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple")
        );
        printLabeled("unsorted: ", stringList);
        stringList = treeSort.method2(stringList);
        printLabeled("sorted: ", stringList);
    }

    private static void printLabeled(String label, Object arrayOrList) {
        System.out.printf("%-10s", label);
        if (arrayOrList instanceof Object[] array) {
            print(array);
        } else if (arrayOrList instanceof List<?> list) {
            print(list);
        }
    }
}
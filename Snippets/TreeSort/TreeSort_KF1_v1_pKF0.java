package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.print;

import com.thealgorithms.datastructures.trees.BSTRecursiveGeneric;
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

        int index = 0;
        for (T element : sorted) {
            array[index++] = element;
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
        System.out.printf("%-10s", "unsorted: ");
        print(intArray);
        intArray = treeSort.method2(intArray);
        System.out.printf("%-10s", "sorted: ");
        print(intArray);
        System.out.println();

        System.out.println("Testing for Integer List....");
        List<Integer> intList = List.of(3, -7, 45, 1, 343, -5, 2, 9);
        System.out.printf("%-10s", "unsorted: ");
        print(intList);
        intList = treeSort.method2(intList);
        System.out.printf("%-10s", "sorted: ");
        print(intList);
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
        stringArray = treeSort.method2(stringArray);
        System.out.printf("%-10s", "sorted: ");
        print(stringArray);
        System.out.println();

        System.out.println("Testing for String List....");
        List<String> stringList =
            List.of("banana", "berry", "orange", "grape", "peach", "cherry", "apple", "pineapple");
        System.out.printf("%-10s", "unsorted: ");
        print(stringList);
        stringList = treeSort.method2(stringList);
        System.out.printf("%-10s", "sorted: ");
        print(stringList);
    }
}
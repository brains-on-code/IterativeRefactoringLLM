package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int TREE_SIZE = 1000;

    private NearestRightKey() {
        // Utility class
    }

    public static void main(String[] args) {
        NRKTree root = buildRandomTree();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            int target = scanner.nextInt();
            Integer nearestKey = findNearestRightKey(root, target);
            System.out.println("Key: " + (nearestKey != null ? nearestKey : "None"));
        }
    }

    public static NRKTree buildRandomTree() {
        NRKTree root = new NRKTree(randomInRange(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE));

        for (int i = 0; i < TREE_SIZE; i++) {
            int randomValue = randomInRange(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
            root.insertKey(randomValue);
        }

        return root;
    }

    private static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Integer findNearestRightKey(NRKTree root, int target) {
        if (root == null) {
            return null;
        }

        Integer nearestGreater = null;
        NRKTree current = root;

        while (current != null) {
            int currentValue = current.getData();
            if (currentValue > target) {
                nearestGreater = currentValue;
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        return nearestGreater;
    }
}

class NRKTree {

    private NRKTree left;
    private NRKTree right;
    private final int data;

    NRKTree(int data) {
        this.data = data;
    }

    NRKTree(NRKTree left, NRKTree right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public NRKTree getLeft() {
        return left;
    }

    public NRKTree getRight() {
        return right;
    }

    public int getData() {
        return data;
    }

    public void insertKey(int value) {
        insertKeyRecursive(this, value);
    }

    private NRKTree insertKeyRecursive(NRKTree current, int value) {
        if (current == null) {
            return new NRKTree(value);
        }

        if (value < current.data) {
            current.left = insertKeyRecursive(current.left, value);
        } else if (value > current.data) {
            current.right = insertKeyRecursive(current.right, value);
        }

        return current;
    }
}
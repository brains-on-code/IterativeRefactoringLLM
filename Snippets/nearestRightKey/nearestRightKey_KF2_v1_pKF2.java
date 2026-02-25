package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int TREE_SIZE = 1000;

    private NearestRightKey() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        NRKTree root = buildRandomTree();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            int target = scanner.nextInt();
            int nearestKey = findNearestRightKey(root, target);
            System.out.println("Key: " + nearestKey);
        }
    }

    /**
     * Builds a binary search tree with random integer values.
     */
    public static NRKTree buildRandomTree() {
        int initialValue = randomValue();
        NRKTree root = new NRKTree(initialValue);

        for (int i = 0; i < TREE_SIZE; i++) {
            root.insertKey(root, randomValue());
        }

        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    /**
     * Returns the smallest key in the tree that is strictly greater than {@code target}.
     * If no such key exists, returns 0.
     */
    public static int findNearestRightKey(NRKTree root, int target) {
        if (root == null) {
            return 0;
        }

        if (root.data > target) {
            int candidate = findNearestRightKey(root.left, target);
            return candidate == 0 ? root.data : candidate;
        }

        return findNearestRightKey(root.right, target);
    }
}

class NRKTree {

    public NRKTree left;
    public NRKTree right;
    public int data;

    NRKTree(int value) {
        this.data = value;
    }

    NRKTree(NRKTree right, NRKTree left, int value) {
        this.left = left;
        this.right = right;
        this.data = value;
    }

    /**
     * Inserts a value into the binary search tree rooted at {@code current}.
     * Returns the (possibly new) root of the subtree.
     */
    public NRKTree insertKey(NRKTree current, int value) {
        if (current == null) {
            return new NRKTree(value);
        }

        if (value < current.data) {
            current.left = insertKey(current.left, value);
        } else if (value > current.data) {
            current.right = insertKey(current.right, value);
        }

        return current;
    }
}
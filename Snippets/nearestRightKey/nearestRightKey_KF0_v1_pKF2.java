package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

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
     * Builds a binary search tree with 1001 random integers in the range [0, 100].
     */
    public static NRKTree buildRandomTree() {
        int randomValue = ThreadLocalRandom.current().nextInt(0, 101);
        NRKTree root = new NRKTree(null, null, randomValue);

        for (int i = 0; i < 1000; i++) {
            randomValue = ThreadLocalRandom.current().nextInt(0, 101);
            root = root.insertKey(root, randomValue);
        }

        return root;
    }

    /**
     * Returns the smallest key in the tree that is strictly greater than {@code target}.
     * If no such key exists or the tree is empty, returns 0.
     */
    public static int findNearestRightKey(NRKTree root, int target) {
        if (root == null) {
            return 0;
        }

        if (root.data > target) {
            int candidate = findNearestRightKey(root.left, target);
            return (candidate == 0) ? root.data : candidate;
        }

        return findNearestRightKey(root.right, target);
    }
}

class NRKTree {

    public NRKTree left;
    public NRKTree right;
    public int data;

    NRKTree(int value) {
        this(null, null, value);
    }

    NRKTree(NRKTree right, NRKTree left, int value) {
        this.left = left;
        this.right = right;
        this.data = value;
    }

    /**
     * Inserts {@code value} into the BST rooted at {@code current} and
     * returns the (possibly new) root of the subtree.
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
package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private static final int TREE_SIZE = 1001;
    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;

    private NearestRightKey() {}

    public static void main(String[] args) {
        NRKTree root = buildRandomTree();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter target number: ");
            int target = scanner.nextInt();
            int nearestKey = findNearestRightKey(root, target);
            System.out.println("Nearest right key: " + nearestKey);
        }
    }

    /**
     * Builds a binary search tree with {@value TREE_SIZE} random integers
     * in the range [{@value MIN_RANDOM_VALUE}, {@value MAX_RANDOM_VALUE}].
     */
    public static NRKTree buildRandomTree() {
        NRKTree root = new NRKTree(randomValue());
        for (int i = 1; i < TREE_SIZE; i++) {
            root = root.insertKey(root, randomValue());
        }
        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    /**
     * Returns the smallest key in the tree that is strictly greater than {@code target}.
     * Returns 0 if no such key exists or if the tree is empty.
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

    NRKTree(NRKTree left, NRKTree right, int value) {
        this.left = left;
        this.right = right;
        this.data = value;
    }

    /**
     * Inserts {@code value} into the BST rooted at {@code current} and
     * returns the (possibly new) root of that subtree.
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
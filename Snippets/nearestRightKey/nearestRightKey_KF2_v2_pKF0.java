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
        NRKTreeNode root = buildRandomTree();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            int target = scanner.nextInt();
            int nearestKey = findNearestRightKey(root, target);
            System.out.println("Key: " + nearestKey);
        }
    }

    public static NRKTreeNode buildRandomTree() {
        NRKTreeNode root = new NRKTreeNode(randomValue());

        for (int i = 0; i < TREE_SIZE; i++) {
            root.insert(randomValue());
        }

        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    /**
     * Finds the smallest key in the tree that is strictly greater than the target.
     * Returns 0 if no such key exists.
     */
    public static int findNearestRightKey(NRKTreeNode root, int target) {
        if (root == null) {
            return 0;
        }

        if (root.data > target) {
            int leftCandidate = findNearestRightKey(root.left, target);
            return leftCandidate == 0 ? root.data : leftCandidate;
        }

        return findNearestRightKey(root.right, target);
    }
}

class NRKTreeNode {

    NRKTreeNode left;
    NRKTreeNode right;
    int data;

    NRKTreeNode(int data) {
        this.data = data;
    }

    NRKTreeNode(NRKTreeNode left, NRKTreeNode right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    /**
     * Inserts a value into the BST rooted at this node.
     */
    public void insert(int value) {
        insert(this, value);
    }

    private NRKTreeNode insert(NRKTreeNode current, int value) {
        if (current == null) {
            return new NRKTreeNode(value);
        }

        if (value < current.data) {
            current.left = insert(current.left, value);
        } else if (value > current.data) {
            current.right = insert(current.right, value);
        }

        return current;
    }
}
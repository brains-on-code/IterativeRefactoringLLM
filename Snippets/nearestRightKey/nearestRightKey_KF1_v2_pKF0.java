package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int NODE_COUNT = 1000;
    private static final int NOT_FOUND = 0;

    private BinarySearchTreeDemo() {
        // Utility class
    }

    public static void main(String[] args) {
        BinarySearchTreeNode root = createRandomTree();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            int input = scanner.nextInt();
            int result = findNextGreaterOrEqual(root, input);
            System.out.println("Key: " + result);
        }
    }

    private static BinarySearchTreeNode createRandomTree() {
        BinarySearchTreeNode root =
                new BinarySearchTreeNode(randomValue());

        for (int i = 0; i < NODE_COUNT; i++) {
            root.insert(randomValue());
        }

        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current()
                .nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    private static int findNextGreaterOrEqual(BinarySearchTreeNode node, int target) {
        if (node == null) {
            return NOT_FOUND;
        }

        if (node.value >= target) {
            int candidate = findNextGreaterOrEqual(node.left, target);
            return candidate == NOT_FOUND ? node.value : candidate;
        }

        return findNextGreaterOrEqual(node.right, target);
    }
}

class BinarySearchTreeNode {

    BinarySearchTreeNode left;
    BinarySearchTreeNode right;
    int value;

    BinarySearchTreeNode(int value) {
        this(null, null, value);
    }

    BinarySearchTreeNode(BinarySearchTreeNode left, BinarySearchTreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public void insert(int value) {
        insert(this, value);
    }

    private BinarySearchTreeNode insert(BinarySearchTreeNode node, int value) {
        if (node == null) {
            return new BinarySearchTreeNode(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }

        return node;
    }
}
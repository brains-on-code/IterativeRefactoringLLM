package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int NODE_COUNT = 1000;

    private BinarySearchTreeDemo() {
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
        int value = ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
        BinarySearchTreeNode root = new BinarySearchTreeNode(null, null, value);

        for (int i = 0; i < NODE_COUNT; i++) {
            value = ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
            root = root.insert(root, value);
        }

        return root;
    }

    private static int findNextGreaterOrEqual(BinarySearchTreeNode node, int target) {
        if (node == null) {
            return 0;
        }

        if (node.value - target > 0) {
            int candidate = findNextGreaterOrEqual(node.left, target);
            return candidate == 0 ? node.value : candidate;
        } else {
            return findNextGreaterOrEqual(node.right, target);
        }
    }
}

class BinarySearchTreeNode {

    public BinarySearchTreeNode left;
    public BinarySearchTreeNode right;
    public int value;

    BinarySearchTreeNode(int value) {
        this.left = null;
        this.right = null;
        this.value = value;
    }

    BinarySearchTreeNode(BinarySearchTreeNode left, BinarySearchTreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public BinarySearchTreeNode insert(BinarySearchTreeNode node, int value) {
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
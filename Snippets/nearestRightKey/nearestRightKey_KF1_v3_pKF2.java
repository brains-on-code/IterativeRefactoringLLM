package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int TREE_SIZE = 1000;
    private static final int NO_CEILING_FOUND = 0;

    private BinarySearchTreeDemo() {}

    public static void main(String[] args) {
        BinarySearchTree root = buildRandomTree();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            int input = scanner.nextInt();
            int key = findCeilingKey(root, input);
            System.out.println("Key: " + key);
        }
    }

    private static BinarySearchTree buildRandomTree() {
        BinarySearchTree root = new BinarySearchTree(randomValue());
        for (int i = 0; i < TREE_SIZE; i++) {
            root = root.insert(root, randomValue());
        }
        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    private static int findCeilingKey(BinarySearchTree node, int target) {
        if (node == null) {
            return NO_CEILING_FOUND;
        }

        if (node.value > target) {
            int leftResult = findCeilingKey(node.left, target);
            return (leftResult == NO_CEILING_FOUND) ? node.value : leftResult;
        }

        return findCeilingKey(node.right, target);
    }
}

class BinarySearchTree {

    public BinarySearchTree left;
    public BinarySearchTree right;
    public int value;

    BinarySearchTree(int value) {
        this(null, null, value);
    }

    BinarySearchTree(BinarySearchTree left, BinarySearchTree right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public BinarySearchTree insert(BinarySearchTree node, int value) {
        if (node == null) {
            return new BinarySearchTree(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }

        return node;
    }
}
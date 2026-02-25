package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int NODE_COUNT = 1000;
    private static final int NOT_FOUND = 0;

    private BinarySearchTreeDemo() {
        // Prevent instantiation
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
        BinarySearchTreeNode root = new BinarySearchTreeNode(generateRandomValue());

        for (int i = 0; i < NODE_COUNT; i++) {
            root.insert(generateRandomValue());
        }

        return root;
    }

    private static int generateRandomValue() {
        return ThreadLocalRandom.current()
            .nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    private static int findNextGreaterOrEqual(BinarySearchTreeNode node, int target) {
        if (node == null) {
            return NOT_FOUND;
        }

        int nodeValue = node.getValue();

        if (nodeValue >= target) {
            int leftCandidate = findNextGreaterOrEqual(node.getLeft(), target);
            return leftCandidate == NOT_FOUND ? nodeValue : leftCandidate;
        }

        return findNextGreaterOrEqual(node.getRight(), target);
    }
}

class BinarySearchTreeNode {

    private BinarySearchTreeNode left;
    private BinarySearchTreeNode right;
    private final int value;

    BinarySearchTreeNode(int value) {
        this(null, null, value);
    }

    BinarySearchTreeNode(BinarySearchTreeNode left, BinarySearchTreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public BinarySearchTreeNode getLeft() {
        return left;
    }

    public BinarySearchTreeNode getRight() {
        return right;
    }

    public int getValue() {
        return value;
    }

    public void insert(int value) {
        insertNode(this, value);
    }

    private BinarySearchTreeNode insertNode(BinarySearchTreeNode current, int valueToInsert) {
        if (current == null) {
            return new BinarySearchTreeNode(valueToInsert);
        }

        if (valueToInsert < current.value) {
            current.left = insertNode(current.left, valueToInsert);
        } else if (valueToInsert > current.value) {
            current.right = insertNode(current.right, valueToInsert);
        }

        return current;
    }
}
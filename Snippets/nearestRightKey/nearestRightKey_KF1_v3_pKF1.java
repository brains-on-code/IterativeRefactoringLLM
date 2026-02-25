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
        BinarySearchTree root = createRandomTree();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int targetValue = scanner.nextInt();

        int nextGreaterOrEqualValue = findNextGreaterOrEqual(root, targetValue);
        System.out.println("Key: " + nextGreaterOrEqualValue);

        scanner.close();
    }

    public static BinarySearchTree createRandomTree() {
        int randomValue = ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
        BinarySearchTree root = new BinarySearchTree(null, null, randomValue);

        for (int i = 0; i < NODE_COUNT; i++) {
            randomValue = ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
            root = root.insert(root, randomValue);
        }

        return root;
    }

    public static int findNextGreaterOrEqual(BinarySearchTree node, int targetValue) {
        if (node == null) {
            return 0;
        }

        if (node.value > targetValue) {
            int candidateValue = findNextGreaterOrEqual(node.left, targetValue);
            if (candidateValue == 0) {
                return node.value;
            }
            return candidateValue;
        }

        return findNextGreaterOrEqual(node.right, targetValue);
    }
}

class BinarySearchTree {

    public BinarySearchTree left;
    public BinarySearchTree right;
    public int value;

    BinarySearchTree(int value) {
        this.left = null;
        this.right = null;
        this.value = value;
    }

    BinarySearchTree(BinarySearchTree right, BinarySearchTree left, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public BinarySearchTree insert(BinarySearchTree node, int valueToInsert) {
        if (node == null) {
            return new BinarySearchTree(valueToInsert);
        }

        if (valueToInsert < node.value) {
            node.left = insert(node.left, valueToInsert);
        } else if (valueToInsert > node.value) {
            node.right = insert(node.right, valueToInsert);
        }

        return node;
    }
}
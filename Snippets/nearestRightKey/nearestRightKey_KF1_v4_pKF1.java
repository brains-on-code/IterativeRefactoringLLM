package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {
    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int NODE_COUNT = 1000;

    private BinarySearchTreeDemo() {}

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
        int randomValue =
                ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
        BinarySearchTree root = new BinarySearchTree(null, null, randomValue);

        for (int i = 0; i < NODE_COUNT; i++) {
            randomValue =
                    ThreadLocalRandom.current().nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
            root = root.insert(root, randomValue);
        }

        return root;
    }

    public static int findNextGreaterOrEqual(BinarySearchTree currentNode, int targetValue) {
        if (currentNode == null) {
            return 0;
        }

        if (currentNode.value > targetValue) {
            int candidateValue = findNextGreaterOrEqual(currentNode.left, targetValue);
            if (candidateValue == 0) {
                return currentNode.value;
            }
            return candidateValue;
        }

        return findNextGreaterOrEqual(currentNode.right, targetValue);
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

    BinarySearchTree(BinarySearchTree rightChild, BinarySearchTree leftChild, int value) {
        this.left = leftChild;
        this.right = rightChild;
        this.value = value;
    }

    public BinarySearchTree insert(BinarySearchTree currentNode, int valueToInsert) {
        if (currentNode == null) {
            return new BinarySearchTree(valueToInsert);
        }

        if (valueToInsert < currentNode.value) {
            currentNode.left = insert(currentNode.left, valueToInsert);
        } else if (valueToInsert > currentNode.value) {
            currentNode.right = insert(currentNode.right, valueToInsert);
        }

        return currentNode;
    }
}
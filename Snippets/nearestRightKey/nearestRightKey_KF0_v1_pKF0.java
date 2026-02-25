package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int TREE_SIZE = 1000;

    private NearestRightKey() {
        // Utility class
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

    public static NRKTree buildRandomTree() {
        int randomValue = randomInRange(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
        NRKTree root = new NRKTree(randomValue);

        for (int i = 0; i < TREE_SIZE; i++) {
            randomValue = randomInRange(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
            root = root.insertKey(root, randomValue);
        }

        return root;
    }

    private static int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int findNearestRightKey(NRKTree root, int target) {
        if (root == null) {
            return 0;
        }

        if (root.data > target) {
            int leftResult = findNearestRightKey(root.left, target);
            return leftResult == 0 ? root.data : leftResult;
        }

        return findNearestRightKey(root.right, target);
    }
}

class NRKTree {

    public NRKTree left;
    public NRKTree right;
    public int data;

    NRKTree(int data) {
        this.data = data;
    }

    NRKTree(NRKTree left, NRKTree right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

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
package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private static final int MIN_RANDOM_VALUE = 0;
    private static final int MAX_RANDOM_VALUE = 100;
    private static final int TREE_SIZE = 1000;
    private static final int NOT_FOUND = 0;

    private NearestRightKey() {}

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
        NRKTree root = new NRKTree(randomValue());
        for (int i = 0; i < TREE_SIZE; i++) {
            root.insertKey(root, randomValue());
        }
        return root;
    }

    private static int randomValue() {
        return ThreadLocalRandom.current()
            .nextInt(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE + 1);
    }

    public static int findNearestRightKey(NRKTree root, int target) {
        if (root == null) {
            return NOT_FOUND;
        }

        if (root.data > target) {
            int candidate = findNearestRightKey(root.left, target);
            return candidate == NOT_FOUND ? root.data : candidate;
        }

        return findNearestRightKey(root.right, target);
    }
}

class NRKTree {

    public NRKTree left;
    public NRKTree right;
    public int data;

    NRKTree(int value) {
        this.data = value;
    }

    NRKTree(NRKTree right, NRKTree left, int value) {
        this.left = left;
        this.right = right;
        this.data = value;
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
package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class NearestRightKey {

    private NearestRightKey() {}

    public static void main(String[] args) {
        BinarySearchTreeNode root = buildRandomTree();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number: ");
        int targetValue = scanner.nextInt();
        int nearestKey = findNearestRightKey(root, targetValue);
        System.out.println("Key: " + nearestKey);
        scanner.close();
    }

    public static BinarySearchTreeNode buildRandomTree() {
        int randomValue = ThreadLocalRandom.current().nextInt(0, 101);
        BinarySearchTreeNode root = new BinarySearchTreeNode(null, null, randomValue);

        for (int i = 0; i < 1000; i++) {
            randomValue = ThreadLocalRandom.current().nextInt(0, 101);
            root = root.insert(root, randomValue);
        }

        return root;
    }

    public static int findNearestRightKey(BinarySearchTreeNode root, int targetValue) {
        if (root == null) {
            return 0;
        }

        if (root.data > targetValue) {
            int candidate = findNearestRightKey(root.left, targetValue);
            return candidate == 0 ? root.data : candidate;
        }

        return findNearestRightKey(root.right, targetValue);
    }
}

class BinarySearchTreeNode {

    public BinarySearchTreeNode left;
    public BinarySearchTreeNode right;
    public int data;

    BinarySearchTreeNode(int value) {
        this.left = null;
        this.right = null;
        this.data = value;
    }

    BinarySearchTreeNode(BinarySearchTreeNode right, BinarySearchTreeNode left, int value) {
        this.left = left;
        this.right = right;
        this.data = value;
    }

    public BinarySearchTreeNode insert(BinarySearchTreeNode current, int value) {
        if (current == null) {
            return new BinarySearchTreeNode(value);
        }

        if (value < current.data) {
            current.left = insert(current.left, value);
        } else if (value > current.data) {
            current.right = insert(current.right, value);
        }

        return current;
    }
}
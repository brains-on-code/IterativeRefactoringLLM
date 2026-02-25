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

        int nearestRightKey = findNearestRightKey(root, targetValue);
        System.out.println("Nearest right key: " + nearestRightKey);

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

    public static int findNearestRightKey(BinarySearchTreeNode node, int targetValue) {
        if (node == null) {
            return 0;
        }

        if (node.data > targetValue) {
            int nearestCandidate = findNearestRightKey(node.left, targetValue);
            return nearestCandidate == 0 ? node.data : nearestCandidate;
        }

        return findNearestRightKey(node.right, targetValue);
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

    BinarySearchTreeNode(BinarySearchTreeNode rightChild, BinarySearchTreeNode leftChild, int value) {
        this.left = leftChild;
        this.right = rightChild;
        this.data = value;
    }

    public BinarySearchTreeNode insert(BinarySearchTreeNode node, int value) {
        if (node == null) {
            return new BinarySearchTreeNode(value);
        }

        if (value < node.data) {
            node.left = insert(node.left, value);
        } else if (value > node.data) {
            node.right = insert(node.right, value);
        }

        return node;
    }
}
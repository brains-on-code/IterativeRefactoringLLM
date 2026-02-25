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

    public static int findNearestRightKey(BinarySearchTreeNode currentNode, int targetValue) {
        if (currentNode == null) {
            return 0;
        }

        if (currentNode.data > targetValue) {
            int nearestCandidate = findNearestRightKey(currentNode.left, targetValue);
            return nearestCandidate == 0 ? currentNode.data : nearestCandidate;
        }

        return findNearestRightKey(currentNode.right, targetValue);
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

    public BinarySearchTreeNode insert(BinarySearchTreeNode currentNode, int value) {
        if (currentNode == null) {
            return new BinarySearchTreeNode(value);
        }

        if (value < currentNode.data) {
            currentNode.left = insert(currentNode.left, value);
        } else if (value > currentNode.data) {
            currentNode.right = insert(currentNode.right, value);
        }

        return currentNode;
    }
}
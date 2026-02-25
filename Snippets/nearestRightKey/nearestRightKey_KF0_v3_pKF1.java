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
        System.out.println("Key: " + nearestRightKey);
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

        if (node.value > targetValue) {
            int nearestRightInLeftSubtree = findNearestRightKey(node.left, targetValue);
            return nearestRightInLeftSubtree == 0 ? node.value : nearestRightInLeftSubtree;
        }

        return findNearestRightKey(node.right, targetValue);
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

    BinarySearchTreeNode(BinarySearchTreeNode rightChild, BinarySearchTreeNode leftChild, int value) {
        this.left = leftChild;
        this.right = rightChild;
        this.value = value;
    }

    public BinarySearchTreeNode insert(BinarySearchTreeNode node, int valueToInsert) {
        if (node == null) {
            return new BinarySearchTreeNode(valueToInsert);
        }

        if (valueToInsert < node.value) {
            node.left = insert(node.left, valueToInsert);
        } else if (valueToInsert > node.value) {
            node.right = insert(node.right, valueToInsert);
        }

        return node;
    }
}
package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {

    private BinarySearchTreeDemo() {
    }

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
        int value = ThreadLocalRandom.current().nextInt(0, 101);
        BinarySearchTree root = new BinarySearchTree(null, null, value);

        for (int i = 0; i < 1000; i++) {
            value = ThreadLocalRandom.current().nextInt(0, 101);
            root = root.insert(root, value);
        }

        return root;
    }

    private static int findCeilingKey(BinarySearchTree node, int target) {
        if (node == null) {
            return 0;
        }

        if (node.value - target > 0) {
            int result = findCeilingKey(node.left, target);
            if (result == 0) {
                return node.value;
            }
            return result;
        } else {
            return findCeilingKey(node.right, target);
        }
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
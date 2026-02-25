package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class BinarySearchTreeDemo {
    private BinarySearchTreeDemo() {
    }

    public static void main(String[] args) {
        BinarySearchTree root = createRandomTree();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first number: ");
        int inputValue = scanner.nextInt();
        int key = findNextGreaterOrEqual(root, inputValue);
        System.out.println("Key: " + key);
        scanner.close();
    }

    public static BinarySearchTree createRandomTree() {
        int value = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        BinarySearchTree root = new BinarySearchTree(null, null, value);

        for (int i = 0; i < 1000; i++) {
            value = ThreadLocalRandom.current().nextInt(0, 100 + 1);
            root = root.insert(root, value);
        }

        return root;
    }

    public static int findNextGreaterOrEqual(BinarySearchTree node, int target) {
        if (node == null) {
            return 0;
        } else {
            if (node.value - target > 0) {
                int candidate = findNextGreaterOrEqual(node.left, target);
                if (candidate == 0) {
                    return node.value;
                }
                return candidate;
            } else {
                return findNextGreaterOrEqual(node.right, target);
            }
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
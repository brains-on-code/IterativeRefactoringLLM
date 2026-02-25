package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    private Node deleteRecursive(Node current, int targetValue) {
        if (current == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (targetValue < current.value) {
            current.left = deleteRecursive(current.left, targetValue);
        } else if (targetValue > current.value) {
            current.right = deleteRecursive(current.right, targetValue);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            } else if (current.left == null) {
                Node rightSubtree = current.right;
                current.right = null;
                return rightSubtree;
            } else if (current.right == null) {
                Node leftSubtree = current.left;
                current.left = null;
                return leftSubtree;
            } else {
                Node inorderSuccessor = current.right;
                while (inorderSuccessor.left != null) {
                    inorderSuccessor = inorderSuccessor.left;
                }
                current.value = inorderSuccessor.value;
                current.right = deleteRecursive(current.right, inorderSuccessor.value);
            }
        }
        return current;
    }

    private Node insertRecursive(Node current, int valueToInsert) {
        if (current == null) {
            return new Node(valueToInsert);
        }

        if (valueToInsert < current.value) {
            current.left = insertRecursive(current.left, valueToInsert);
        } else if (valueToInsert > current.value) {
            current.right = insertRecursive(current.right, valueToInsert);
        }

        return current;
    }

    private boolean searchRecursive(Node current, int targetValue) {
        if (current == null) {
            return false;
        }

        if (current.value == targetValue) {
            return true;
        } else if (targetValue < current.value) {
            return searchRecursive(current.left, targetValue);
        } else {
            return searchRecursive(current.right, targetValue);
        }
    }

    public void insert(int value) {
        this.root = insertRecursive(this.root, value);
    }

    public void delete(int value) {
        this.root = deleteRecursive(this.root, value);
    }

    public boolean search(int value) {
        if (searchRecursive(this.root, value)) {
            System.out.println(value + " is present in given BST.");
            return true;
        }
        System.out.println(value + " not found.");
        return false;
    }
}
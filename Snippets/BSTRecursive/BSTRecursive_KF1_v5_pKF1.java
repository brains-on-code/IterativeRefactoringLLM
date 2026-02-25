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

    private Node deleteRecursive(Node currentNode, int valueToDelete) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (valueToDelete < currentNode.value) {
            currentNode.left = deleteRecursive(currentNode.left, valueToDelete);
        } else if (valueToDelete > currentNode.value) {
            currentNode.right = deleteRecursive(currentNode.right, valueToDelete);
        } else {
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            } else if (currentNode.left == null) {
                Node rightChild = currentNode.right;
                currentNode.right = null;
                return rightChild;
            } else if (currentNode.right == null) {
                Node leftChild = currentNode.left;
                currentNode.left = null;
                return leftChild;
            } else {
                Node inorderSuccessor = currentNode.right;
                while (inorderSuccessor.left != null) {
                    inorderSuccessor = inorderSuccessor.left;
                }
                currentNode.value = inorderSuccessor.value;
                currentNode.right = deleteRecursive(currentNode.right, inorderSuccessor.value);
            }
        }
        return currentNode;
    }

    private Node insertRecursive(Node currentNode, int valueToInsert) {
        if (currentNode == null) {
            return new Node(valueToInsert);
        }

        if (valueToInsert < currentNode.value) {
            currentNode.left = insertRecursive(currentNode.left, valueToInsert);
        } else if (valueToInsert > currentNode.value) {
            currentNode.right = insertRecursive(currentNode.right, valueToInsert);
        }

        return currentNode;
    }

    private boolean searchRecursive(Node currentNode, int targetValue) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.value == targetValue) {
            return true;
        } else if (targetValue < currentNode.value) {
            return searchRecursive(currentNode.left, targetValue);
        } else {
            return searchRecursive(currentNode.right, targetValue);
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
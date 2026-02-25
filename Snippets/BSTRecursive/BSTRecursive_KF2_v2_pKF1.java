package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class BSTRecursive {

    private Node root;

    public BSTRecursive() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    private Node delete(Node currentNode, int targetValue) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (targetValue < currentNode.data) {
            currentNode.left = delete(currentNode.left, targetValue);
        } else if (targetValue > currentNode.data) {
            currentNode.right = delete(currentNode.right, targetValue);
        } else {
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            } else if (currentNode.left == null) {
                Node rightSubtree = currentNode.right;
                currentNode.right = null;
                return rightSubtree;
            } else if (currentNode.right == null) {
                Node leftSubtree = currentNode.left;
                currentNode.left = null;
                return leftSubtree;
            } else {
                Node inorderSuccessor = currentNode.right;
                while (inorderSuccessor.left != null) {
                    inorderSuccessor = inorderSuccessor.left;
                }
                currentNode.data = inorderSuccessor.data;
                currentNode.right = delete(currentNode.right, inorderSuccessor.data);
            }
        }
        return currentNode;
    }

    private Node insert(Node currentNode, int valueToInsert) {
        if (currentNode == null) {
            return new Node(valueToInsert);
        }

        if (valueToInsert < currentNode.data) {
            currentNode.left = insert(currentNode.left, valueToInsert);
        } else if (valueToInsert > currentNode.data) {
            currentNode.right = insert(currentNode.right, valueToInsert);
        }

        return currentNode;
    }

    private boolean search(Node currentNode, int targetValue) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.data == targetValue) {
            return true;
        } else if (targetValue < currentNode.data) {
            return search(currentNode.left, targetValue);
        } else {
            return search(currentNode.right, targetValue);
        }
    }

    public void add(int value) {
        this.root = insert(this.root, value);
    }

    public void remove(int value) {
        this.root = delete(this.root, value);
    }

    public boolean find(int value) {
        if (search(this.root, value)) {
            System.out.println(value + " is present in given BST.");
            return true;
        }
        System.out.println(value + " not found.");
        return false;
    }
}
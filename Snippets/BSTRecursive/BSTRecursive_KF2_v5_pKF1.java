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

    private Node delete(Node current, int targetValue) {
        if (current == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (targetValue < current.data) {
            current.left = delete(current.left, targetValue);
        } else if (targetValue > current.data) {
            current.right = delete(current.right, targetValue);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            } else if (current.left == null) {
                Node rightChild = current.right;
                current.right = null;
                return rightChild;
            } else if (current.right == null) {
                Node leftChild = current.left;
                current.left = null;
                return leftChild;
            } else {
                Node inorderSuccessor = current.right;
                while (inorderSuccessor.left != null) {
                    inorderSuccessor = inorderSuccessor.left;
                }
                current.data = inorderSuccessor.data;
                current.right = delete(current.right, inorderSuccessor.data);
            }
        }
        return current;
    }

    private Node insert(Node current, int valueToInsert) {
        if (current == null) {
            return new Node(valueToInsert);
        }

        if (valueToInsert < current.data) {
            current.left = insert(current.left, valueToInsert);
        } else if (valueToInsert > current.data) {
            current.right = insert(current.right, valueToInsert);
        }

        return current;
    }

    private boolean search(Node current, int targetValue) {
        if (current == null) {
            return false;
        }

        if (current.data == targetValue) {
            return true;
        } else if (targetValue < current.data) {
            return search(current.left, targetValue);
        } else {
            return search(current.right, targetValue);
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
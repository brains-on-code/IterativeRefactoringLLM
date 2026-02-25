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

    private Node delete(Node currentNode, int key) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (key < currentNode.data) {
            currentNode.left = delete(currentNode.left, key);
        } else if (key > currentNode.data) {
            currentNode.right = delete(currentNode.right, key);
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
                Node successor = currentNode.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                currentNode.data = successor.data;
                currentNode.right = delete(currentNode.right, successor.data);
            }
        }
        return currentNode;
    }

    private Node insert(Node currentNode, int key) {
        if (currentNode == null) {
            return new Node(key);
        }

        if (key < currentNode.data) {
            currentNode.left = insert(currentNode.left, key);
        } else if (key > currentNode.data) {
            currentNode.right = insert(currentNode.right, key);
        }

        return currentNode;
    }

    private boolean search(Node currentNode, int key) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.data == key) {
            return true;
        } else if (key < currentNode.data) {
            return search(currentNode.left, key);
        } else {
            return search(currentNode.right, key);
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
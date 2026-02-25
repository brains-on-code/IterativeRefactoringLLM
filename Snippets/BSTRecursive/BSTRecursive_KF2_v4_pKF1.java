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

    private Node delete(Node currentNode, int value) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (value < currentNode.data) {
            currentNode.left = delete(currentNode.left, value);
        } else if (value > currentNode.data) {
            currentNode.right = delete(currentNode.right, value);
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

    private Node insert(Node currentNode, int value) {
        if (currentNode == null) {
            return new Node(value);
        }

        if (value < currentNode.data) {
            currentNode.left = insert(currentNode.left, value);
        } else if (value > currentNode.data) {
            currentNode.right = insert(currentNode.right, value);
        }

        return currentNode;
    }

    private boolean search(Node currentNode, int value) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.data == value) {
            return true;
        } else if (value < currentNode.data) {
            return search(currentNode.left, value);
        } else {
            return search(currentNode.right, value);
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
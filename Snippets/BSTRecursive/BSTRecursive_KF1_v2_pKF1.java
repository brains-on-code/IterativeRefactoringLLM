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

    private Node deleteNode(Node currentNode, int value) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (currentNode.var2 > value) {
            currentNode.left = deleteNode(currentNode.left, value);
        } else if (currentNode.var2 < value) {
            currentNode.right = deleteNode(currentNode.right, value);
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
                currentNode.var2 = inorderSuccessor.var2;
                currentNode.right = deleteNode(currentNode.right, inorderSuccessor.var2);
            }
        }
        return currentNode;
    }

    private Node insertNode(Node currentNode, int value) {
        if (currentNode == null) {
            return new Node(value);
        }

        if (currentNode.var2 > value) {
            currentNode.left = insertNode(currentNode.left, value);
        } else if (currentNode.var2 < value) {
            currentNode.right = insertNode(currentNode.right, value);
        }

        return currentNode;
    }

    private boolean searchNode(Node currentNode, int value) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.var2 == value) {
            return true;
        } else if (currentNode.var2 > value) {
            return searchNode(currentNode.left, value);
        } else {
            return searchNode(currentNode.right, value);
        }
    }

    public void insert(int value) {
        this.root = insertNode(this.root, value);
    }

    public void delete(int value) {
        this.root = deleteNode(this.root, value);
    }

    public boolean search(int value) {
        if (searchNode(this.root, value)) {
            System.out.println(value + " is present in given BST.");
            return true;
        }
        System.out.println(value + " not found.");
        return false;
    }
}
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class Class1 {

    private Node root;

    Class1() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    private Node deleteNode(Node currentNode, int value) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
        } else if (currentNode.var2 > value) {
            currentNode.left = deleteNode(currentNode.left, value);
        } else if (currentNode.var2 < value) {
            currentNode.right = deleteNode(currentNode.right, value);
        } else {
            if (currentNode.right == null && currentNode.left == null) {
                currentNode = null;
            } else if (currentNode.left == null) {
                Node temp = currentNode.right;
                currentNode.right = null;
                currentNode = temp;
            } else if (currentNode.right == null) {
                Node temp = currentNode.left;
                currentNode.left = null;
                currentNode = temp;
            } else {
                Node successor = currentNode.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                currentNode.var2 = successor.var2;
                currentNode.right = deleteNode(currentNode.right, successor.var2);
            }
        }
        return currentNode;
    }

    private Node insertNode(Node currentNode, int value) {
        if (currentNode == null) {
            currentNode = new Node(value);
        } else if (currentNode.var2 > value) {
            currentNode.left = insertNode(currentNode.left, value);
        } else if (currentNode.var2 < value) {
            currentNode.right = insertNode(currentNode.right, value);
        }
        return currentNode;
    }

    private boolean searchNode(Node currentNode, int value) {
        if (currentNode == null) {
            return false;
        } else if (currentNode.var2 == value) {
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
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

    public void add(int data) {
        root = insert(root, data);
    }

    public void remove(int data) {
        root = delete(root, data);
    }

    public boolean find(int data) {
        boolean found = search(root, data);
        if (found) {
            System.out.println(data + " is present in given BST.");
        } else {
            System.out.println(data + " not found.");
        }
        return found;
    }

    private Node insert(Node current, int data) {
        if (current == null) {
            return new Node(data);
        }

        if (data < current.data) {
            current.left = insert(current.left, data);
        } else if (data > current.data) {
            current.right = insert(current.right, data);
        }

        return current;
    }

    private Node delete(Node current, int data) {
        if (current == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (data < current.data) {
            current.left = delete(current.left, data);
            return current;
        }

        if (data > current.data) {
            current.right = delete(current.right, data);
            return current;
        }

        // Node to delete found
        if (isLeaf(current)) {
            return null;
        }

        if (current.left == null) {
            Node rightChild = current.right;
            current.right = null;
            return rightChild;
        }

        if (current.right == null) {
            Node leftChild = current.left;
            current.left = null;
            return leftChild;
        }

        Node successor = findMin(current.right);
        current.data = successor.data;
        current.right = delete(current.right, successor.data);
        return current;
    }

    private boolean search(Node current, int data) {
        if (current == null) {
            return false;
        }

        if (data == current.data) {
            return true;
        }

        if (data < current.data) {
            return search(current.left, data);
        }

        return search(current.right, data);
    }

    private boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    private Node findMin(Node node) {
        Node current = node;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }
}
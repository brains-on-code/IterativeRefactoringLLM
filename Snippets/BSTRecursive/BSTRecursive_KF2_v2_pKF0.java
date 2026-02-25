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

    private Node insert(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        }

        return node;
    }

    private Node delete(Node node, int data) {
        if (node == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (data < node.data) {
            node.left = delete(node.left, data);
            return node;
        }

        if (data > node.data) {
            node.right = delete(node.right, data);
            return node;
        }

        // Node to delete found
        if (isLeaf(node)) {
            return null;
        }

        if (node.left == null) {
            Node rightChild = node.right;
            node.right = null;
            return rightChild;
        }

        if (node.right == null) {
            Node leftChild = node.left;
            node.left = null;
            return leftChild;
        }

        Node successor = findMin(node.right);
        node.data = successor.data;
        node.right = delete(node.right, successor.data);
        return node;
    }

    private boolean search(Node node, int data) {
        if (node == null) {
            return false;
        }

        if (data == node.data) {
            return true;
        }

        if (data < node.data) {
            return search(node.left, data);
        }

        return search(node.right, data);
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
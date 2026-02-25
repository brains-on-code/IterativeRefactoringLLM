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

        // Case 1: No children
        if (node.left == null && node.right == null) {
            return null;
        }

        // Case 2: Only right child
        if (node.left == null) {
            Node temp = node.right;
            node.right = null;
            return temp;
        }

        // Case 3: Only left child
        if (node.right == null) {
            Node temp = node.left;
            node.left = null;
            return temp;
        }

        // Case 4: Two children
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        node.data = successor.data;
        node.right = delete(node.right, successor.data);
        return node;
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

    public void add(int data) {
        this.root = insert(this.root, data);
    }

    public void remove(int data) {
        this.root = delete(this.root, data);
    }

    public boolean find(int data) {
        boolean found = search(this.root, data);
        if (found) {
            System.out.println(data + " is present in given BST.");
        } else {
            System.out.println(data + " not found.");
        }
        return found;
    }
}
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Binary Search Tree wrapper with basic operations:
 * insert, delete, search, and root access.
 */
public class BinarySearchTree {

    /** Root node of the BST. */
    private Node root;

    /** Creates an empty BST. */
    public BinarySearchTree() {
        this.root = null;
    }

    /** Returns the root node of the BST. */
    public Node getRoot() {
        return root;
    }

    /** Inserts a value into the BST. */
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    /** Deletes a value from the BST (if present). */
    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    /**
     * Searches for a value in the BST.
     *
     * @return true if the value is present, false otherwise
     */
    public boolean search(int value) {
        boolean found = searchRecursive(root, value);
        if (found) {
            System.out.println(value + " is present in given BST.");
        } else {
            System.out.println(value + " not found.");
        }
        return found;
    }

    // ----------------- Private helpers -----------------

    private Node insertRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.var2) {
            current.left = insertRecursive(current.left, value);
        } else if (value > current.var2) {
            current.right = insertRecursive(current.right, value);
        }
        // if value == current.var2, do nothing (no duplicates)
        return current;
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (value < current.var2) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }

        if (value > current.var2) {
            current.right = deleteRecursive(current.right, value);
            return current;
        }

        // Node to delete found

        // Case 1: no children
        if (current.left == null && current.right == null) {
            return null;
        }

        // Case 2: one child (right only)
        if (current.left == null) {
            Node temp = current.right;
            current.right = null;
            return temp;
        }

        // Case 3: one child (left only)
        if (current.right == null) {
            Node temp = current.left;
            current.left = null;
            return temp;
        }

        // Case 4: two children
        Node successor = findMin(current.right);
        current.var2 = successor.var2;
        current.right = deleteRecursive(current.right, successor.var2);

        return current;
    }

    private Node findMin(Node node) {
        Node current = node;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    private boolean searchRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }

        if (current.var2 == value) {
            return true;
        }

        if (value < current.var2) {
            return searchRecursive(current.left, value);
        }

        return searchRecursive(current.right, value);
    }
}
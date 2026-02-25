package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Binary Search Tree wrapper that provides insert, delete, and search
 * operations over {@link BinaryTree.Node}.
 */
public class Class1 {

    /** Root node of the BST. */
    private Node root;

    /** Creates an empty BST. */
    Class1() {
        root = null;
    }

    /** Returns the root node of the BST. */
    public Node getRoot() {
        return root;
    }

    /**
     * Deletes a value from the BST rooted at {@code node}.
     *
     * @param node current subtree root
     * @param value value to delete
     * @return new subtree root after deletion
     */
    private Node delete(Node node, int value) {
        if (node == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (value < node.var2) {
            node.left = delete(node.left, value);
        } else if (value > node.var2) {
            node.right = delete(node.right, value);
        } else {
            // Node to delete found

            // Case 1: no children (leaf node)
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: only right child
            if (node.left == null) {
                Node temp = node.right;
                node.right = null;
                return temp;
            }

            // Case 3: only left child
            if (node.right == null) {
                Node temp = node.left;
                node.left = null;
                return temp;
            }

            // Case 4: two children
            // Find inorder successor (smallest in right subtree)
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }

            // Copy successor's value to current node
            node.var2 = successor.var2;

            // Delete the inorder successor
            node.right = delete(node.right, successor.var2);
        }

        return node;
    }

    /**
     * Inserts a value into the BST rooted at {@code node}.
     *
     * @param node current subtree root
     * @param value value to insert
     * @return new subtree root after insertion
     */
    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.var2) {
            node.left = insert(node.left, value);
        } else if (value > node.var2) {
            node.right = insert(node.right, value);
        }

        return node;
    }

    /**
     * Searches for a value in the BST rooted at {@code node}.
     *
     * @param node current subtree root
     * @param value value to search for
     * @return {@code true} if the value exists, {@code false} otherwise
     */
    private boolean contains(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (node.var2 == value) {
            return true;
        } else if (value < node.var2) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    /**
     * Inserts a value into this BST.
     *
     * @param value value to insert
     */
    public void insert(int value) {
        this.root = insert(this.root, value);
    }

    /**
     * Deletes a value from this BST.
     *
     * @param value value to delete
     */
    public void delete(int value) {
        this.root = delete(this.root, value);
    }

    /**
     * Checks whether a value is present in this BST and prints the result.
     *
     * @param value value to search for
     * @return {@code true} if the value exists, {@code false} otherwise
     */
    public boolean search(int value) {
        if (contains(this.root, value)) {
            System.out.println(value + " is present in given BST.");
            return true;
        }
        System.out.println(value + " not found.");
        return false;
    }
}
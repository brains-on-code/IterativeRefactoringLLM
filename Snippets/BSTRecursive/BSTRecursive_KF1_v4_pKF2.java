package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Binary Search Tree implementation that provides insert, delete, and search
 * operations over {@link BinaryTree.Node}.
 */
public class Class1 {

    /** Root node of the binary search tree. */
    private Node root;

    /** Creates an empty binary search tree. */
    Class1() {
        this.root = null;
    }

    /** Returns the root node of the tree. */
    public Node getRoot() {
        return root;
    }

    /**
     * Removes the specified value from the subtree rooted at {@code node}.
     *
     * @param node  current subtree root
     * @param value value to remove
     * @return new subtree root after removal
     */
    private Node delete(Node node, int value) {
        if (node == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (value < node.var2) {
            node.left = delete(node.left, value);
            return node;
        }

        if (value > node.var2) {
            node.right = delete(node.right, value);
            return node;
        }

        // Node with the target value found

        // Leaf node
        if (node.left == null && node.right == null) {
            return null;
        }

        // Only right child
        if (node.left == null) {
            Node rightChild = node.right;
            node.right = null;
            return rightChild;
        }

        // Only left child
        if (node.right == null) {
            Node leftChild = node.left;
            node.left = null;
            return leftChild;
        }

        // Two children: replace with inorder successor
        Node successor = findMin(node.right);
        node.var2 = successor.var2;
        node.right = delete(node.right, successor.var2);
        return node;
    }

    /**
     * Finds the node with the minimum value in the subtree rooted at {@code node}.
     *
     * @param node subtree root
     * @return node with the minimum value
     */
    private Node findMin(Node node) {
        Node current = node;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Inserts the specified value into the subtree rooted at {@code node}.
     *
     * @param node  current subtree root
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
     * Returns whether the specified value exists in the subtree rooted at {@code node}.
     *
     * @param node  current subtree root
     * @param value value to search for
     * @return {@code true} if the value exists, {@code false} otherwise
     */
    private boolean contains(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (node.var2 == value) {
            return true;
        }

        if (value < node.var2) {
            return contains(node.left, value);
        }

        return contains(node.right, value);
    }

    /**
     * Inserts the specified value into this tree.
     *
     * @param value value to insert
     */
    public void insert(int value) {
        this.root = insert(this.root, value);
    }

    /**
     * Removes the specified value from this tree.
     *
     * @param value value to remove
     */
    public void delete(int value) {
        this.root = delete(this.root, value);
    }

    /**
     * Returns whether the specified value exists in this tree and prints the result.
     *
     * @param value value to search for
     * @return {@code true} if the value exists, {@code false} otherwise
     */
    public boolean search(int value) {
        boolean found = contains(this.root, value);
        if (found) {
            System.out.println(value + " is present in given BST.");
        } else {
            System.out.println(value + " not found.");
        }
        return found;
    }
}
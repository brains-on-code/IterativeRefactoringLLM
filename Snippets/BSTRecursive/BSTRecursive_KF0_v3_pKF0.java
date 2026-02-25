package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * <h1>Binary Search Tree (Recursive)</h1>
 *
 * Recursive implementation of a Binary Search Tree (BST).
 * A BST is a binary tree that satisfies:
 * - left child is less than root node
 * - right child is greater than root node
 * - both left and right children must themselves be BSTs
 *
 * Public methods provide the API, while private methods implement recursion.
 */
public class BSTRecursive {

    /** Root node of the BST. */
    private Node root;

    /** Constructs an empty BST. */
    public BSTRecursive() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Adds a value to the BST. If the value is already present, no change occurs.
     *
     * @param data value to be inserted
     */
    public void add(int data) {
        root = insert(root, data);
    }

    /**
     * Removes a value from the BST if present.
     *
     * @param data value to be removed
     */
    public void remove(int data) {
        root = delete(root, data);
    }

    /**
     * Checks if a value is present in the BST.
     *
     * @param data value to search for
     * @return true if present, false otherwise
     */
    public boolean find(int data) {
        boolean found = search(root, data);
        System.out.println(
            found
                ? data + " is present in given BST."
                : data + " not found."
        );
        return found;
    }

    /**
     * Recursively inserts a value into the BST.
     *
     * @param node current node in the traversal
     * @param data value to be inserted
     * @return updated subtree root after insertion
     */
    private Node insert(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        }
        // if data == node.data, do nothing (no duplicates)
        return node;
    }

    /**
     * Recursively deletes a value from the BST if present.
     *
     * @param node current node in the traversal
     * @param data value to be deleted
     * @return updated subtree root after deletion
     */
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

        // node.data == data: perform deletion
        if (isLeaf(node)) {
            return null;
        }

        if (node.left == null) {
            return node.right; // only right child
        }

        if (node.right == null) {
            return node.left; // only left child
        }

        // both children present: replace with inorder successor
        Node successor = findMin(node.right);
        node.data = successor.data;
        node.right = delete(node.right, successor.data);
        return node;
    }

    /**
     * Checks if a node is a leaf (has no children).
     *
     * @param node node to check
     * @return true if leaf, false otherwise
     */
    private boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    /**
     * Finds the node with the minimum value in a subtree.
     *
     * @param node root of the subtree
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
     * Recursively searches for a value in the BST.
     *
     * @param node current node in the traversal
     * @param data value to search for
     * @return true if found, false otherwise
     */
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
}
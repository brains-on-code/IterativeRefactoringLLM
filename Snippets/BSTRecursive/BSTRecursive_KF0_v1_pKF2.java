package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Binary Search Tree (BST) implementation using recursive operations.
 *
 * <p>A Binary Search Tree is a binary tree that satisfies:
 * <ul>
 *   <li>Left child is less than the root node</li>
 *   <li>Right child is greater than the root node</li>
 *   <li>Both left and right subtrees are themselves BSTs</li>
 * </ul>
 *
 * <p>Public methods provide the API, while private methods implement the
 * recursive logic.
 */
public class BSTRecursive {

    /** Root node of the BST. */
    private Node root;

    /** Creates an empty BST. */
    BSTRecursive() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Recursively deletes a value from the BST, if present.
     *
     * @param node current subtree root
     * @param data value to delete
     * @return updated subtree root after deletion
     */
    private Node delete(Node node, int data) {
        if (node == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (data < node.data) {
            node.left = delete(node.left, data);
        } else if (data > node.data) {
            node.right = delete(node.right, data);
        } else {
            // Node to delete found

            // Case 1: leaf node
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
            // Find the leftmost node in the right subtree (in-order successor)
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }

            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }

        return node;
    }

    /**
     * Recursively inserts a value into the BST.
     *
     * @param node current subtree root
     * @param data value to insert
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

        return node;
    }

    /**
     * Recursively searches for a value in the BST.
     *
     * @param node current subtree root
     * @param data value to search for
     * @return {@code true} if the value is found, {@code false} otherwise
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

    /**
     * Inserts a value into the BST. If the value already exists, the tree is unchanged.
     *
     * @param data value to insert
     */
    public void add(int data) {
        this.root = insert(this.root, data);
    }

    /**
     * Removes a value from the BST, if present.
     *
     * @param data value to remove
     */
    public void remove(int data) {
        this.root = delete(this.root, data);
    }

    /**
     * Checks whether a value is present in the BST.
     *
     * @param data value to search for
     * @return {@code true} if the value is present, {@code false} otherwise
     */
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
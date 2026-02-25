package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * <h1>Binary Search Tree (Iterative)</h1>
 *
 * <p>Iterative implementation of a Binary Search Tree (BST). A BST is a binary
 * tree that satisfies:
 * <ul>
 *     <li>Left child is less than root node</li>
 *     <li>Right child is greater than root node</li>
 *     <li>Both left and right subtrees are themselves BSTs</li>
 * </ul>
 */
public class BSTIterative {

    /** Root node of the BST. */
    private Node root;

    /** Initializes the BST with an empty root. */
    BSTIterative() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Inserts a new value into the BST. If the value already exists, insertion
     * is ignored.
     *
     * @param data the value to be inserted
     */
    public void add(int data) {
        if (root == null) {
            root = new Node(data);
            return;
        }

        Node parent = null;
        Node current = root;
        boolean insertLeft = false;

        while (current != null) {
            parent = current;
            if (data < current.data) {
                current = current.left;
                insertLeft = true;
            } else if (data > current.data) {
                current = current.right;
                insertLeft = false;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);
        if (insertLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    /**
     * Deletes a node with the given value from the BST, if present.
     *
     * @param data the value to be deleted
     */
    public void remove(int data) {
        Node parent = null;
        Node current = root;
        boolean isLeftChild = false;

        // Find node to delete and its parent
        while (current != null && current.data != data) {
            parent = current;
            if (data < current.data) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
        }

        // Value not found
        if (current == null) {
            return;
        }

        Node replacement;

        // Case 1: Leaf node
        if (current.left == null && current.right == null) {
            replacement = null;
        }
        // Case 2: Only left child
        else if (current.right == null) {
            replacement = current.left;
            current.left = null;
        }
        // Case 3: Only right child
        else if (current.left == null) {
            replacement = current.right;
            current.right = null;
        }
        // Case 4: Two children
        else {
            // If right child has no left child, it is the inorder successor
            if (current.right.left == null) {
                current.data = current.right.data;
                current.right = current.right.right;
                replacement = current;
            } else {
                // Find leftmost node in right subtree (inorder successor)
                Node successorParent = current.right;
                Node successor = current.right.left;

                while (successor.left != null) {
                    successorParent = successor;
                    successor = successor.left;
                }

                current.data = successor.data;
                successorParent.left = successor.right;
                replacement = current;
            }
        }

        // Update parent reference
        if (parent == null) {
            root = replacement;
        } else if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    /**
     * Checks if the given value exists in the BST.
     *
     * @param data the value to search for
     * @return {@code true} if the value is found, {@code false} otherwise
     */
    public boolean find(int data) {
        Node current = root;

        while (current != null) {
            if (data < current.data) {
                current = current.left;
            } else if (data > current.data) {
                current = current.right;
            } else {
                System.out.println(data + " is present in the BST.");
                return true;
            }
        }

        System.out.println(data + " not found.");
        return false;
    }
}
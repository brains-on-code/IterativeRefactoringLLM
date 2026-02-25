package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * A simple Binary Search Tree (BST) wrapper that supports insertion,
 * deletion, search, and root access.
 */
public class Class1 {

    /** Root node of the BST. */
    private Node root;

    /** Creates an empty BST. */
    Class1() {
        this.root = null;
    }

    /** Returns the root node of the BST. */
    public Node getRoot() {
        return root;
    }

    /**
     * Inserts a value into the BST.
     * If the value already exists, the tree is not modified.
     *
     * @param value value to insert
     */
    public void insert(int value) {
        Node parent = null;
        Node current = root;
        int childSide = -1; // 0 = left, 1 = right

        while (current != null) {
            if (current.var1 > value) {
                parent = current;
                current = current.left;
                childSide = 0;
            } else if (current.var1 < value) {
                parent = current;
                current = current.right;
                childSide = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);

        if (parent == null) {
            root = newNode;
        } else if (childSide == 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    /**
     * Deletes a value from the BST, if present.
     *
     * @param value value to delete
     */
    public void delete(int value) {
        Node parent = null;
        Node current = root;
        int childSide = -1; // 0 = left, 1 = right

        // Find node to delete and its parent
        while (current != null) {
            if (current.var1 == value) {
                break;
            } else if (current.var1 > value) {
                parent = current;
                current = current.left;
                childSide = 0;
            } else {
                parent = current;
                current = current.right;
                childSide = 1;
            }
        }

        if (current == null) {
            return; // Value not found
        }

        Node replacement;

        // Case 1: No children
        if (current.left == null && current.right == null) {
            replacement = null;

        // Case 2: Only left child
        } else if (current.right == null) {
            replacement = current.left;
            current.left = null;

        // Case 3: Only right child
        } else if (current.left == null) {
            replacement = current.right;
            current.right = null;

        // Case 4: Two children
        } else {
            // Right child has no left child: it is the inorder successor
            if (current.right.left == null) {
                current.var1 = current.right.var1;
                current.right = current.right.right;
                replacement = current;
            } else {
                // Inorder successor: leftmost node in right subtree
                Node successorParent = current.right;
                Node successor = current.right.left;
                while (successor.left != null) {
                    successorParent = successor;
                    successor = successor.left;
                }
                current.var1 = successor.var1;
                successorParent.left = successor.right;
                replacement = current;
            }
        }

        // Reattach to parent or update root
        if (parent == null) {
            root = replacement;
        } else if (childSide == 0) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    /**
     * Searches for a value in the BST.
     *
     * @param value value to search for
     * @return true if the value is present, false otherwise
     */
    public boolean search(int value) {
        Node current = root;

        while (current != null) {
            if (current.var1 > value) {
                current = current.left;
            } else if (current.var1 < value) {
                current = current.right;
            } else {
                System.out.println(value + " is present in the BST.");
                return true;
            }
        }

        System.out.println(value + " not found.");
        return false;
    }
}
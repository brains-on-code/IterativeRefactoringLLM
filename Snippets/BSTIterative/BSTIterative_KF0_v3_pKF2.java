package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Iterative implementation of a Binary Search Tree (BST).
 *
 * <p>BST properties:
 * <ul>
 *   <li>Left subtree contains values less than the node's value.</li>
 *   <li>Right subtree contains values greater than the node's value.</li>
 *   <li>Both left and right subtrees are also BSTs.</li>
 * </ul>
 */
public class BSTIterative {

    private Node root;

    public BSTIterative() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Inserts a value into the BST.
     * If the value already exists, the insertion is ignored.
     *
     * @param data the value to be inserted
     */
    public void add(int data) {
        Node parent = null;
        Node current = this.root;
        int direction = -1; // 0 = left child, 1 = right child

        while (current != null) {
            parent = current;
            if (data < current.data) {
                current = current.left;
                direction = 0;
            } else if (data > current.data) {
                current = current.right;
                direction = 1;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);

        if (parent == null) {
            this.root = newNode;
            return;
        }

        if (direction == 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    /**
     * Deletes a value from the BST if it exists.
     *
     * @param data the value to be deleted
     */
    public void remove(int data) {
        Node parent = null;
        Node current = this.root;
        int direction = -1; // 0 = left child, 1 = right child

        // Search for the node to remove, keeping track of its parent and direction
        while (current != null && current.data != data) {
            parent = current;
            if (data < current.data) {
                current = current.left;
                direction = 0;
            } else {
                current = current.right;
                direction = 1;
            }
        }

        // Value not found
        if (current == null) {
            return;
        }

        Node replacement;

        // Case 1: No children (leaf node)
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
                // Find inorder successor (leftmost node in right subtree)
                Node successorParent = current.right;
                Node successor = current.right.left;

                while (successor.left != null) {
                    successorParent = successor;
                    successor = successor.left;
                }

                // Replace current node's data with successor's data
                current.data = successor.data;

                // Remove successor from its original position
                successorParent.left = successor.right;
                replacement = current;
            }
        }

        // Update parent link or root
        if (parent == null) {
            this.root = replacement;
        } else if (direction == 0) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    /**
     * Checks whether a value exists in the BST.
     *
     * @param data the value to search for
     * @return {@code true} if the value is found, {@code false} otherwise
     */
    public boolean find(int data) {
        Node current = this.root;

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
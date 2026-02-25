package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 * Binary Search Tree (Iterative)
 *
 * <p>A Binary Search Tree (BST) is a binary tree where:
 * <ul>
 *   <li>The left child is less than the root node</li>
 *   <li>The right child is greater than the root node</li>
 *   <li>Both left and right subtrees are themselves BSTs</li>
 * </ul>
 */
public class BSTIterative {

    /** Root node of the BST. */
    private Node root;

    /** Creates an empty BST. */
    BSTIterative() {
        root = null;
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
        int direction = -1; // 0 = left, 1 = right

        // Find the correct parent and direction for the new node
        while (current != null) {
            if (current.data > data) {
                parent = current;
                current = current.left;
                direction = 0;
            } else if (current.data < data) {
                parent = current;
                current = current.right;
                direction = 1;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);

        // Tree is empty: new node becomes root
        if (parent == null) {
            this.root = newNode;
            return;
        }

        // Attach new node to the appropriate side of the parent
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
        int direction = -1; // 0 = left, 1 = right

        // Find the node to delete and its parent
        while (current != null) {
            if (current.data == data) {
                break;
            } else if (current.data > data) {
                parent = current;
                current = current.left;
                direction = 0;
            } else {
                parent = current;
                current = current.right;
                direction = 1;
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
            // If the right child has no left child, it is the inorder successor
            if (current.right.left == null) {
                current.data = current.right.data;
                replacement = current;
                current.right = current.right.right;
            } else {
                // Find the leftmost node in the right subtree (inorder successor)
                Node successorParent = current.right;
                Node successor = current.right.left;
                while (successor.left != null) {
                    successorParent = successor;
                    successor = successorParent.left;
                }
                current.data = successor.data;
                successorParent.left = successor.right;
                replacement = current;
            }
        }

        // Update the parent's reference
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
            if (current.data > data) {
                current = current.left;
            } else if (current.data < data) {
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
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 *
 *
 * <h1>Binary Search Tree (Iterative)</h1>
 *
 * <p>
 * An implementation of BST iteratively. Binary Search Tree is a binary tree
 * which satisfies three properties: left child is less than root node, right
 * child is greater than root node, both left and right child must themselves be
 * a BST.
 *
 * @author [Lakhan Nad](<a href="https://github.com/Lakhan-Nad">git-Lakhan Nad</a>)
 */

public class BSTIterative {

    /**
     * Root node of the BST.
     */
    private Node root;

    /**
     * Default Constructor initializes the root of BST with null.
     */
    BSTIterative() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Inserts a new value into the BST. If the given value is already
     * present in the BST, the insertion is ignored.
     *
     * @param value the value to be inserted
     */
    public void add(int value) {
        Node parent = null;
        Node current = this.root;
        int childDirection = -1; // 0 for left, 1 for right

        // Find the proper place this node can be placed according to rules of BST.
        while (current != null) {
            if (current.data > value) {
                parent = current;
                current = parent.left;
                childDirection = 0;
            } else if (current.data < value) {
                parent = current;
                current = parent.right;
                childDirection = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return; // if value already present we ignore insertion
            }
        }

        // Create a new node with the value passed since this value doesn't already exist
        Node newNode = new Node(value);

        // If the parent node is null then the insertion is to be done in root itself.
        if (parent == null) {
            this.root = newNode;
        } else {
            // Check if insertion is to be made in left or right subtree.
            if (childDirection == 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    /**
     * Deletes the node with the given value from the BST, if present.
     *
     * @param value the value that needs to be deleted
     */
    public void remove(int value) {
        Node parent = null;
        Node current = this.root;
        int childDirection = -1; // 0 for left, 1 for right

        /*
         * Find the parent of the node and node itself that is to be deleted.
         * parent stores parent, current stores node itself.
         * childDirection is used to keep track whether child is left or right subtree.
         */
        while (current != null) {
            if (current.data == value) {
                break;
            } else if (current.data > value) {
                parent = current;
                current = parent.left;
                childDirection = 0;
            } else {
                parent = current;
                current = parent.right;
                childDirection = 1;
            }
        }

        // If current is null then node with given value is not present in our tree.
        if (current != null) {
            Node replacement; // used to store the new node that will replace the deleted node

            if (current.right == null && current.left == null) { // Leaf node case
                replacement = null;
            } else if (current.right == null) { // Node with only left child
                replacement = current.left;
                current.left = null;
            } else if (current.left == null) { // Node with only right child
                replacement = current.right;
                current.right = null;
            } else {
                /*
                 * If both left and right child are present we replace this node's data with
                 * leftmost node's data in its right subtree to maintain the BST property.
                 * And then delete that node.
                 */
                if (current.right.left == null) {
                    current.data = current.right.data;
                    replacement = current;
                    current.right = current.right.right;
                } else {
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

            // Change references of parent after deleting the child.
            if (parent == null) {
                this.root = replacement;
            } else {
                if (childDirection == 0) {
                    parent.left = replacement;
                } else {
                    parent.right = replacement;
                }
            }
        }
    }

    /**
     * Checks if the given value exists in the Binary Search Tree.
     *
     * @param value the value that needs to be searched for
     * @return boolean representing if the value was found
     */
    public boolean find(int value) {
        Node current = this.root;

        // Check if node exists
        while (current != null) {
            if (current.data > value) {
                current = current.left;
            } else if (current.data < value) {
                current = current.right;
            } else {
                // If found return true
                System.out.println(value + " is present in the BST.");
                return true;
            }
        }
        System.out.println(value + " not found.");
        return false;
    }
}
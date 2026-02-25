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
        int direction = -1; // 0 = left, 1 = right

        while (current != null) {
            if (data < current.data) {
                parent = current;
                current = current.left;
                direction = 0;
            } else if (data > current.data) {
                parent = current;
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
        int direction = -1; // 0 = left, 1 = right

        while (current != null) {
            if (data == current.data) {
                break;
            } else if (data < current.data) {
                parent = current;
                current = current.left;
                direction = 0;
            } else {
                parent = current;
                current = current.right;
                direction = 1;
            }
        }

        if (current == null) {
            return;
        }

        Node replacement;

        if (current.left == null && current.right == null) {
            replacement = null;
        } else if (current.right == null) {
            replacement = current.left;
            current.left = null;
        } else if (current.left == null) {
            replacement = current.right;
            current.right = null;
        } else {
            if (current.right.left == null) {
                current.data = current.right.data;
                replacement = current;
                current.right = current.right.right;
            } else {
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
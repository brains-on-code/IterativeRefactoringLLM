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
    public BSTIterative() {
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

        while (current != null) {
            parent = current;
            if (data < current.data) {
                current = current.left;
            } else if (data > current.data) {
                current = current.right;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);
        if (data < parent.data) {
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

        Node replacement = getReplacementNode(current);

        // Update parent reference
        if (parent == null) {
            root = replacement;
        } else if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    private Node getReplacementNode(Node nodeToRemove) {
        // Case 1: Leaf node
        if (nodeToRemove.left == null && nodeToRemove.right == null) {
            return null;
        }

        // Case 2: Only left child
        if (nodeToRemove.right == null) {
            Node replacement = nodeToRemove.left;
            nodeToRemove.left = null;
            return replacement;
        }

        // Case 3: Only right child
        if (nodeToRemove.left == null) {
            Node replacement = nodeToRemove.right;
            nodeToRemove.right = null;
            return replacement;
        }

        // Case 4: Two children
        return replaceWithInorderSuccessor(nodeToRemove);
    }

    private Node replaceWithInorderSuccessor(Node node) {
        // If right child has no left child, it is the inorder successor
        if (node.right.left == null) {
            node.data = node.right.data;
            node.right = node.right.right;
            return node;
        }

        // Find leftmost node in right subtree (inorder successor)
        Node successorParent = node.right;
        Node successor = node.right.left;

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        node.data = successor.data;
        successorParent.left = successor.right;
        return node;
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
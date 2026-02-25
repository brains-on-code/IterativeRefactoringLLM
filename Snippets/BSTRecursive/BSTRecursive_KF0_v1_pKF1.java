package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

/**
 *
 *
 * <h1>Binary Search Tree (Recursive)</h1>
 *
 * An implementation of BST recursively. In recursive implementation the checks
 * are down the tree First root is checked if not found then its children are
 * checked Binary Search Tree is a binary tree which satisfies three properties:
 * left child is less than root node, right child is grater than root node, both
 * left and right children must themselves be a BST.
 *
 * <p>
 * I have made public functions as methods and to actually implement recursive
 * approach I have used private methods
 *
 * @author [Lakhan Nad](<a href="https://github.com/Lakhan-Nad">git-Lakhan Nad</a>)
 */
public class BSTRecursive {

    /**
     * Root node of the BST.
     */
    private Node root;

    /**
     * Constructor initializes root as null.
     */
    BSTRecursive() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Recursive method to delete a value if present in BST.
     *
     * @param currentNode the current node to search for value
     * @param value the value to be deleted
     * @return Node the updated subtree root after delete operation
     */
    private Node delete(Node currentNode, int value) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
        } else if (currentNode.data > value) {
            currentNode.left = delete(currentNode.left, value);
        } else if (currentNode.data < value) {
            currentNode.right = delete(currentNode.right, value);
        } else {
            if (currentNode.right == null && currentNode.left == null) { // Leaf node
                currentNode = null;
            } else if (currentNode.left == null) { // Only right child present
                Node rightChild = currentNode.right;
                currentNode.right = null;
                currentNode = rightChild;
            } else if (currentNode.right == null) { // Only left child present
                Node leftChild = currentNode.left;
                currentNode.left = null;
                currentNode = leftChild;
            } else { // Both children present
                Node successor = currentNode.right;
                // Find leftmost child of right subtree (in-order successor)
                while (successor.left != null) {
                    successor = successor.left;
                }
                currentNode.data = successor.data;
                currentNode.right = delete(currentNode.right, successor.data);
            }
        }
        return currentNode;
    }

    /**
     * Recursive insertion of value in BST.
     *
     * @param currentNode current subtree root where the value may be inserted
     * @param value the value to be inserted
     * @return the modified subtree root after insertion
     */
    private Node insert(Node currentNode, int value) {
        if (currentNode == null) {
            currentNode = new Node(value);
        } else if (currentNode.data > value) {
            currentNode.left = insert(currentNode.left, value);
        } else if (currentNode.data < value) {
            currentNode.right = insert(currentNode.right, value);
        }
        return currentNode;
    }

    /**
     * Search recursively if the given value is present in BST or not.
     *
     * @param currentNode the current node to check
     * @param value the value to be checked
     * @return boolean if value is present or not
     */
    private boolean search(Node currentNode, int value) {
        if (currentNode == null) {
            return false;
        } else if (currentNode.data == value) {
            return true;
        } else if (currentNode.data > value) {
            return search(currentNode.left, value);
        } else {
            return search(currentNode.right, value);
        }
    }

    /**
     * Add value in BST. If the value is not already present it is inserted or
     * else no change takes place.
     *
     * @param value the value to be inserted
     */
    public void add(int value) {
        this.root = insert(this.root, value);
    }

    /**
     * If value is present in BST delete it else do nothing.
     *
     * @param value the value to be removed
     */
    public void remove(int value) {
        this.root = delete(this.root, value);
    }

    /**
     * To check if given value is present in tree or not.
     *
     * @param value the value to be searched for
     */
    public boolean find(int value) {
        if (search(this.root, value)) {
            System.out.println(value + " is present in given BST.");
            return true;
        }
        System.out.println(value + " not found.");
        return false;
    }
}
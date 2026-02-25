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
     * @param valueToDelete the value to be deleted
     * @return Node the updated subtree root after delete operation
     */
    private Node delete(Node currentNode, int valueToDelete) {
        if (currentNode == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (valueToDelete < currentNode.data) {
            currentNode.left = delete(currentNode.left, valueToDelete);
        } else if (valueToDelete > currentNode.data) {
            currentNode.right = delete(currentNode.right, valueToDelete);
        } else {
            // Node to delete found
            if (currentNode.left == null && currentNode.right == null) {
                // Leaf node
                return null;
            } else if (currentNode.left == null) {
                // Only right child present
                Node rightChild = currentNode.right;
                currentNode.right = null;
                return rightChild;
            } else if (currentNode.right == null) {
                // Only left child present
                Node leftChild = currentNode.left;
                currentNode.left = null;
                return leftChild;
            } else {
                // Both children present
                Node successorNode = currentNode.right;
                // Find leftmost child of right subtree (in-order successor)
                while (successorNode.left != null) {
                    successorNode = successorNode.left;
                }
                currentNode.data = successorNode.data;
                currentNode.right = delete(currentNode.right, successorNode.data);
            }
        }
        return currentNode;
    }

    /**
     * Recursive insertion of value in BST.
     *
     * @param currentNode current subtree root where the value may be inserted
     * @param valueToInsert the value to be inserted
     * @return the modified subtree root after insertion
     */
    private Node insert(Node currentNode, int valueToInsert) {
        if (currentNode == null) {
            return new Node(valueToInsert);
        }

        if (valueToInsert < currentNode.data) {
            currentNode.left = insert(currentNode.left, valueToInsert);
        } else if (valueToInsert > currentNode.data) {
            currentNode.right = insert(currentNode.right, valueToInsert);
        }
        return currentNode;
    }

    /**
     * Search recursively if the given value is present in BST or not.
     *
     * @param currentNode the current node to check
     * @param valueToFind the value to be checked
     * @return boolean if value is present or not
     */
    private boolean search(Node currentNode, int valueToFind) {
        if (currentNode == null) {
            return false;
        }

        if (currentNode.data == valueToFind) {
            return true;
        } else if (valueToFind < currentNode.data) {
            return search(currentNode.left, valueToFind);
        } else {
            return search(currentNode.right, valueToFind);
        }
    }

    /**
     * Add value in BST. If the value is not already present it is inserted or
     * else no change takes place.
     *
     * @param valueToInsert the value to be inserted
     */
    public void add(int valueToInsert) {
        this.root = insert(this.root, valueToInsert);
    }

    /**
     * If value is present in BST delete it else do nothing.
     *
     * @param valueToRemove the value to be removed
     */
    public void remove(int valueToRemove) {
        this.root = delete(this.root, valueToRemove);
    }

    /**
     * To check if given value is present in tree or not.
     *
     * @param valueToFind the value to be searched for
     */
    public boolean find(int valueToFind) {
        if (search(this.root, valueToFind)) {
            System.out.println(valueToFind + " is present in given BST.");
            return true;
        }
        System.out.println(valueToFind + " not found.");
        return false;
    }
}
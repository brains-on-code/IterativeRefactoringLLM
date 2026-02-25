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
 * child is grater than root node, both left and right child must themselves be
 * a BST.
 *
 * @author [Lakhan Nad](<a href="https://github.com/Lakhan-Nad">git-Lakhan Nad</a>)
 */

public class BSTIterative {

    /**
     * Reference for the node of BST.
     */
    private Node root;

    /**
     * Default Constructor Initializes the root of BST with null.
     */
    BSTIterative() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * A method to insert a new value in BST. If the given value is already
     * present in BST the insertion is ignored.
     *
     * @param value the value to be inserted
     */
    public void add(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childSide = -1; // 0 for left, 1 for right

        // Find the proper place this node can be placed according to rules of BST.
        while (currentNode != null) {
            if (currentNode.data > value) {
                parentNode = currentNode;
                currentNode = parentNode.left;
                childSide = 0;
            } else if (currentNode.data < value) {
                parentNode = currentNode;
                currentNode = parentNode.right;
                childSide = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return; // if value already present we ignore insertion
            }
        }

        // Create a new node with the value passed since this value doesn't already exist
        Node newNode = new Node(value);

        // If the parent node is null then the insertion is to be done in root itself.
        if (parentNode == null) {
            this.root = newNode;
        } else {
            // Check if insertion is to be made in left or right subtree.
            if (childSide == 0) {
                parentNode.left = newNode;
            } else {
                parentNode.right = newNode;
            }
        }
    }

    /**
     * A method to delete the node in BST. If node is present it will be deleted
     *
     * @param value the value that needs to be deleted
     */
    public void remove(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childSide = -1; // 0 for left, 1 for right

        /*
         * Find the parent of the node and node itself that is to be deleted.
         * parentNode stores parent, currentNode stores node itself.
         * childSide is used to keep track whether child is left or right subtree.
         */
        while (currentNode != null) {
            if (currentNode.data == value) {
                break;
            } else if (currentNode.data > value) {
                parentNode = currentNode;
                currentNode = parentNode.left;
                childSide = 0;
            } else {
                parentNode = currentNode;
                currentNode = parentNode.right;
                childSide = 1;
            }
        }

        // If currentNode is null then node with given value is not present in our tree.
        if (currentNode != null) {
            Node replacementNode; // used to store the new values for replacing nodes

            if (currentNode.right == null && currentNode.left == null) { // Leaf node case
                replacementNode = null;
            } else if (currentNode.right == null) { // Node with only left child
                replacementNode = currentNode.left;
                currentNode.left = null;
            } else if (currentNode.left == null) { // Node with only right child
                replacementNode = currentNode.right;
                currentNode.right = null;
            } else {
                /*
                 * If both left and right child are present we replace this node's data with
                 * leftmost node's data in its right subtree to maintain the balance of BST.
                 * And then delete that node.
                 */
                if (currentNode.right.left == null) {
                    currentNode.data = currentNode.right.data;
                    replacementNode = currentNode;
                    currentNode.right = currentNode.right.right;
                } else {
                    Node successorParent = currentNode.right;
                    Node successor = currentNode.right.left;
                    while (successor.left != null) {
                        successorParent = successor;
                        successor = successorParent.left;
                    }
                    currentNode.data = successor.data;
                    successorParent.left = successor.right;
                    replacementNode = currentNode;
                }
            }

            // Change references of parent after deleting the child.
            if (parentNode == null) {
                this.root = replacementNode;
            } else {
                if (childSide == 0) {
                    parentNode.left = replacementNode;
                } else {
                    parentNode.right = replacementNode;
                }
            }
        }
    }

    /**
     * A method to check if given data exists in our Binary Search Tree.
     *
     * @param value the value that needs to be searched for
     * @return boolean representing if the value was found
     */
    public boolean find(int value) {
        Node currentNode = this.root;

        // Check if node exists
        while (currentNode != null) {
            if (currentNode.data > value) {
                currentNode = currentNode.left;
            } else if (currentNode.data < value) {
                currentNode = currentNode.right;
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
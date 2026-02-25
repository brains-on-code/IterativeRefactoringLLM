package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childDirection = -1; // 0 = left, 1 = right

        while (currentNode != null) {
            if (currentNode.var1 > value) {
                parentNode = currentNode;
                currentNode = currentNode.left;
                childDirection = 0;
            } else if (currentNode.var1 < value) {
                parentNode = currentNode;
                currentNode = currentNode.right;
                childDirection = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);

        if (parentNode == null) {
            this.root = newNode;
        } else if (childDirection == 0) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }
    }

    public void delete(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childDirection = -1; // 0 = left, 1 = right

        while (currentNode != null) {
            if (currentNode.var1 == value) {
                break;
            } else if (currentNode.var1 > value) {
                parentNode = currentNode;
                currentNode = currentNode.left;
                childDirection = 0;
            } else {
                parentNode = currentNode;
                currentNode = currentNode.right;
                childDirection = 1;
            }
        }

        if (currentNode == null) {
            return;
        }

        Node replacementNode;

        // Case 1: No children
        if (currentNode.left == null && currentNode.right == null) {
            replacementNode = null;
        }
        // Case 2: Only left child
        else if (currentNode.right == null) {
            replacementNode = currentNode.left;
            currentNode.left = null;
        }
        // Case 3: Only right child
        else if (currentNode.left == null) {
            replacementNode = currentNode.right;
            currentNode.right = null;
        }
        // Case 4: Two children
        else {
            // If right child has no left child, it is the inorder successor
            if (currentNode.right.left == null) {
                currentNode.var1 = currentNode.right.var1;
                replacementNode = currentNode;
                currentNode.right = currentNode.right.right;
            } else {
                Node successorParent = currentNode.right;
                Node successorNode = currentNode.right.left;

                while (successorNode.left != null) {
                    successorParent = successorNode;
                    successorNode = successorParent.left;
                }

                currentNode.var1 = successorNode.var1;
                successorParent.left = successorNode.right;
                replacementNode = currentNode;
            }
        }

        if (parentNode == null) {
            this.root = replacementNode;
        } else if (childDirection == 0) {
            parentNode.left = replacementNode;
        } else {
            parentNode.right = replacementNode;
        }
    }

    public boolean search(int value) {
        Node currentNode = this.root;

        while (currentNode != null) {
            if (currentNode.var1 > value) {
                currentNode = currentNode.left;
            } else if (currentNode.var1 < value) {
                currentNode = currentNode.right;
            } else {
                System.out.println(value + " is present in the BST.");
                return true;
            }
        }

        System.out.println(value + " not found.");
        return false;
    }
}
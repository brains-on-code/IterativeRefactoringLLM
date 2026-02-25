package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class BSTIterative {

    private Node root;

    public BSTIterative() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void add(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childDirection = -1; // 0 for left, 1 for right

        while (currentNode != null) {
            if (currentNode.data > value) {
                parentNode = currentNode;
                currentNode = currentNode.left;
                childDirection = 0;
            } else if (currentNode.data < value) {
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

    public void remove(int value) {
        Node parentNode = null;
        Node currentNode = this.root;
        int childDirection = -1; // 0 for left, 1 for right

        while (currentNode != null) {
            if (currentNode.data == value) {
                break;
            } else if (currentNode.data > value) {
                parentNode = currentNode;
                currentNode = currentNode.left;
                childDirection = 0;
            } else {
                parentNode = currentNode;
                currentNode = currentNode.right;
                childDirection = 1;
            }
        }

        if (currentNode != null) {
            Node replacementNode;

            if (currentNode.left == null && currentNode.right == null) {
                replacementNode = null;
            } else if (currentNode.right == null) {
                replacementNode = currentNode.left;
                currentNode.left = null;
            } else if (currentNode.left == null) {
                replacementNode = currentNode.right;
                currentNode.right = null;
            } else {
                if (currentNode.right.left == null) {
                    currentNode.data = currentNode.right.data;
                    replacementNode = currentNode;
                    currentNode.right = currentNode.right.right;
                } else {
                    Node successorParentNode = currentNode.right;
                    Node successorNode = currentNode.right.left;

                    while (successorNode.left != null) {
                        successorParentNode = successorNode;
                        successorNode = successorNode.left;
                    }

                    currentNode.data = successorNode.data;
                    successorParentNode.left = successorNode.right;
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
    }

    public boolean find(int value) {
        Node currentNode = this.root;

        while (currentNode != null) {
            if (currentNode.data > value) {
                currentNode = currentNode.left;
            } else if (currentNode.data < value) {
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
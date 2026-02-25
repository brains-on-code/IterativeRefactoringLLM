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
        Node parent = null;
        Node current = this.root;
        int childDirection = -1; // 0 = left, 1 = right

        while (current != null) {
            if (current.var1 > value) {
                parent = current;
                current = current.left;
                childDirection = 0;
            } else if (current.var1 < value) {
                parent = current;
                current = current.right;
                childDirection = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);

        if (parent == null) {
            this.root = newNode;
        } else if (childDirection == 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void delete(int value) {
        Node parent = null;
        Node current = this.root;
        int childDirection = -1; // 0 = left, 1 = right

        while (current != null) {
            if (current.var1 == value) {
                break;
            } else if (current.var1 > value) {
                parent = current;
                current = current.left;
                childDirection = 0;
            } else {
                parent = current;
                current = current.right;
                childDirection = 1;
            }
        }

        if (current == null) {
            return;
        }

        Node replacement;

        // Case 1: No children
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
                current.var1 = current.right.var1;
                replacement = current;
                current.right = current.right.right;
            } else {
                Node successorParent = current.right;
                Node successor = current.right.left;

                while (successor.left != null) {
                    successorParent = successor;
                    successor = successorParent.left;
                }

                current.var1 = successor.var1;
                successorParent.left = successor.right;
                replacement = current;
            }
        }

        if (parent == null) {
            this.root = replacement;
        } else if (childDirection == 0) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    public boolean search(int value) {
        Node current = this.root;

        while (current != null) {
            if (current.var1 > value) {
                current = current.left;
            } else if (current.var1 < value) {
                current = current.right;
            } else {
                System.out.println(value + " is present in the BST.");
                return true;
            }
        }

        System.out.println(value + " not found.");
        return false;
    }
}
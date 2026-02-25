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

    public void add(int data) {
        Node parent = null;
        Node current = this.root;
        boolean isLeftChild = false;

        while (current != null) {
            parent = current;
            if (data < current.data) {
                current = current.left;
                isLeftChild = true;
            } else if (data > current.data) {
                current = current.right;
                isLeftChild = false;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);

        if (parent == null) {
            this.root = newNode;
        } else if (isLeftChild) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void remove(int data) {
        Node parent = null;
        Node current = this.root;
        boolean isLeftChild = false;

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

        if (current == null) {
            return;
        }

        Node replacement;

        if (current.left == null && current.right == null) {
            replacement = null;
        } else if (current.left == null) {
            replacement = current.right;
            current.right = null;
        } else if (current.right == null) {
            replacement = current.left;
            current.left = null;
        } else {
            if (current.right.left == null) {
                current.data = current.right.data;
                current.right = current.right.right;
                replacement = current;
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
        } else if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

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
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
        Node current = root;
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
            root = newNode;
            return;
        }

        if (isLeftChild) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void remove(int data) {
        Node parent = null;
        Node current = root;
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

        Node replacement = getReplacementNode(current);

        if (parent == null) {
            root = replacement;
            return;
        }

        if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    private Node getReplacementNode(Node nodeToRemove) {
        if (nodeToRemove.left == null && nodeToRemove.right == null) {
            return null;
        }

        if (nodeToRemove.left == null) {
            Node replacement = nodeToRemove.right;
            nodeToRemove.right = null;
            return replacement;
        }

        if (nodeToRemove.right == null) {
            Node replacement = nodeToRemove.left;
            nodeToRemove.left = null;
            return replacement;
        }

        if (nodeToRemove.right.left == null) {
            nodeToRemove.data = nodeToRemove.right.data;
            nodeToRemove.right = nodeToRemove.right.right;
            return nodeToRemove;
        }

        Node successorParent = nodeToRemove.right;
        Node successor = nodeToRemove.right.left;

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        nodeToRemove.data = successor.data;
        successorParent.left = successor.right;

        return nodeToRemove;
    }

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
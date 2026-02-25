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
        if (root == null) {
            root = new Node(data);
            return;
        }

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
        } else if (isLeftChild) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }
    }

    private Node getReplacementNode(Node node) {
        if (node.left == null && node.right == null) {
            return null;
        }

        if (node.left == null) {
            Node rightChild = node.right;
            node.right = null;
            return rightChild;
        }

        if (node.right == null) {
            Node leftChild = node.left;
            node.left = null;
            return leftChild;
        }

        return removeWithTwoChildren(node);
    }

    private Node removeWithTwoChildren(Node node) {
        if (node.right.left == null) {
            node.data = node.right.data;
            node.right = node.right.right;
            return node;
        }

        Node parentOfSuccessor = node.right;
        Node successor = node.right.left;

        while (successor.left != null) {
            parentOfSuccessor = successor;
            successor = successor.left;
        }

        node.data = successor.data;
        parentOfSuccessor.left = successor.right;
        return node;
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
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class BSTRecursive {

    private Node root;

    public BSTRecursive() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    private Node delete(Node node, int data) {
        if (node == null) {
            System.out.println("No such data present in BST.");
            return null;
        }

        if (data < node.data) {
            node.left = delete(node.left, data);
            return node;
        }

        if (data > node.data) {
            node.right = delete(node.right, data);
            return node;
        }

        // data == node.data: delete this node

        boolean hasNoChildren = node.left == null && node.right == null;
        boolean hasOnlyRightChild = node.left == null && node.right != null;
        boolean hasOnlyLeftChild = node.left != null && node.right == null;
        boolean hasTwoChildren = node.left != null && node.right != null;

        if (hasNoChildren) {
            return null;
        }

        if (hasOnlyRightChild) {
            Node rightChild = node.right;
            node.right = null;
            return rightChild;
        }

        if (hasOnlyLeftChild) {
            Node leftChild = node.left;
            node.left = null;
            return leftChild;
        }

        if (hasTwoChildren) {
            Node successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }

        return node;
    }

    private Node insert(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        }

        return node;
    }

    private boolean search(Node node, int data) {
        if (node == null) {
            return false;
        }

        if (data == node.data) {
            return true;
        }

        if (data < node.data) {
            return search(node.left, data);
        }

        return search(node.right, data);
    }

    public void add(int data) {
        this.root = insert(this.root, data);
    }

    public void remove(int data) {
        this.root = delete(this.root, data);
    }

    public boolean find(int data) {
        boolean found = search(this.root, data);
        if (found) {
            System.out.println(data + " is present in given BST.");
        } else {
            System.out.println(data + " not found.");
        }
        return found;
    }
}
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class Class1 {

    private Node root;

    public Class1() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Node parent = null;
        Node current = root;
        boolean insertToLeft = false;

        while (current != null) {
            parent = current;
            if (value < current.var1) {
                current = current.left;
                insertToLeft = true;
            } else if (value > current.var1) {
                current = current.right;
                insertToLeft = false;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);
        if (insertToLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void delete(int value) {
        Node parent = null;
        Node current = root;
        boolean isLeftChild = false;

        while (current != null && current.var1 != value) {
            parent = current;
            if (value < current.var1) {
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

    private Node getReplacementNode(Node nodeToDelete) {
        boolean hasLeftChild = nodeToDelete.left != null;
        boolean hasRightChild = nodeToDelete.right != null;

        if (!hasLeftChild && !hasRightChild) {
            return null;
        }

        if (!hasLeftChild) {
            Node rightChild = nodeToDelete.right;
            nodeToDelete.right = null;
            return rightChild;
        }

        if (!hasRightChild) {
            Node leftChild = nodeToDelete.left;
            nodeToDelete.left = null;
            return leftChild;
        }

        if (nodeToDelete.right.left == null) {
            nodeToDelete.var1 = nodeToDelete.right.var1;
            nodeToDelete.right = nodeToDelete.right.right;
            return nodeToDelete;
        }

        Node successorParent = nodeToDelete.right;
        Node successor = nodeToDelete.right.left;

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }

        nodeToDelete.var1 = successor.var1;
        successorParent.left = successor.right;
        return nodeToDelete;
    }

    public boolean search(int value) {
        Node current = root;

        while (current != null) {
            if (value < current.var1) {
                current = current.left;
            } else if (value > current.var1) {
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
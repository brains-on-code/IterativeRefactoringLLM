package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class Class1 {

    private Node root;

    Class1() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        Node parent = null;
        Node current = this.root;
        int direction = -1; // 0 = left, 1 = right

        while (current != null) {
            if (current.var1 > value) {
                parent = current;
                current = current.left;
                direction = 0;
            } else if (current.var1 < value) {
                parent = current;
                current = current.right;
                direction = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);

        if (parent == null) {
            this.root = newNode;
        } else if (direction == 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void delete(int value) {
        Node parent = null;
        Node current = this.root;
        int direction = -1; // 0 = left, 1 = right

        while (current != null) {
            if (current.var1 == value) {
                break;
            } else if (current.var1 > value) {
                parent = current;
                current = current.left;
                direction = 0;
            } else {
                parent = current;
                current = current.right;
                direction = 1;
            }
        }

        if (current != null) {
            Node replacement;

            if (current.left == null && current.right == null) {
                replacement = null;
            } else if (current.right == null) {
                replacement = current.left;
                current.left = null;
            } else if (current.left == null) {
                replacement = current.right;
                current.right = null;
            } else {
                if (current.right.left == null) {
                    current.var1 = current.right.var1;
                    replacement = current;
                    current.right = current.right.right;
                } else {
                    Node successorParent = current.right;
                    Node successor = current.right.left;

                    while (successor.left != null) {
                        successorParent = successor;
                        successor = successor.left;
                    }

                    current.var1 = successor.var1;
                    successorParent.left = successor.right;
                    replacement = current;
                }
            }

            if (parent == null) {
                this.root = replacement;
            } else if (direction == 0) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
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
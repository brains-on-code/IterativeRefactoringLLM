package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;

public class Class1 {

    private Node root;

    Class1() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        Node parent = null;
        Node current = this.root;
        int childDirection = -1;

        while (current != null) {
            if (current.var1 > value) {
                parent = current;
                current = parent.left;
                childDirection = 0;
            } else if (current.var1 < value) {
                parent = current;
                current = parent.right;
                childDirection = 1;
            } else {
                System.out.println(value + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(value);

        if (parent == null) {
            this.root = newNode;
        } else {
            if (childDirection == 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    public void delete(int value) {
        Node parent = null;
        Node current = this.root;
        int childDirection = -1;

        while (current != null) {
            if (current.var1 == value) {
                break;
            } else if (current.var1 > value) {
                parent = current;
                current = parent.left;
                childDirection = 0;
            } else {
                parent = current;
                current = parent.right;
                childDirection = 1;
            }
        }

        if (current != null) {
            Node replacementNode;
            if (current.right == null && current.left == null) {
                replacementNode = null;
            } else if (current.right == null) {
                replacementNode = current.left;
                current.left = null;
            } else if (current.left == null) {
                replacementNode = current.right;
                current.right = null;
            } else {
                if (current.right.left == null) {
                    current.var1 = current.right.var1;
                    replacementNode = current;
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
                    replacementNode = current;
                }
            }

            if (parent == null) {
                this.root = replacementNode;
            } else {
                if (childDirection == 0) {
                    parent.left = replacementNode;
                } else {
                    parent.right = replacementNode;
                }
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
package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;



public class BSTIterative {


    private Node root;


    BSTIterative() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }


    public void add(int data) {
        Node parent = null;
        Node temp = this.root;
        int rightOrLeft = -1;

        while (temp != null) {
            if (temp.data > data) {
                parent = temp;
                temp = parent.left;
                rightOrLeft = 0;
            } else if (temp.data < data) {
                parent = temp;
                temp = parent.right;
                rightOrLeft = 1;
            } else {
                System.out.println(data + " is already present in BST.");
                return;
            }
        }

        Node newNode = new Node(data);

        if (parent == null) {
            this.root = newNode;
        } else {

            if (rightOrLeft == 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }


    public void remove(int data) {
        Node parent = null;
        Node temp = this.root;
        int rightOrLeft = -1;

        while (temp != null) {
            if (temp.data == data) {
                break;
            } else if (temp.data > data) {
                parent = temp;
                temp = parent.left;
                rightOrLeft = 0;
            } else {
                parent = temp;
                temp = parent.right;
                rightOrLeft = 1;
            }
        }

        if (temp != null) {
            Node replacement;
            if (temp.right == null && temp.left == null) {
                replacement = null;
            } else if (temp.right == null) {
                replacement = temp.left;
                temp.left = null;
            } else if (temp.left == null) {
                replacement = temp.right;
                temp.right = null;
            } else {

                if (temp.right.left == null) {
                    temp.data = temp.right.data;
                    replacement = temp;
                    temp.right = temp.right.right;
                } else {
                    Node parent2 = temp.right;
                    Node child = temp.right.left;
                    while (child.left != null) {
                        parent2 = child;
                        child = parent2.left;
                    }
                    temp.data = child.data;
                    parent2.left = child.right;
                    replacement = temp;
                }
            }

            if (parent == null) {
                this.root = replacement;
            } else {
                if (rightOrLeft == 0) {
                    parent.left = replacement;
                } else {
                    parent.right = replacement;
                }
            }
        }
    }


    public boolean find(int data) {
        Node temp = this.root;

        while (temp != null) {
            if (temp.data > data) {
                temp = temp.left;
            } else if (temp.data < data) {
                temp = temp.right;
            } else {

                System.out.println(data + " is present in the BST.");
                return true;
            }
        }
        System.out.println(data + " not found.");
        return false;
    }
}

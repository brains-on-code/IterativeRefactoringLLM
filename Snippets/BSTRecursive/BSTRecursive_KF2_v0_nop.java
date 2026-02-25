package com.thealgorithms.datastructures.trees;

import com.thealgorithms.datastructures.trees.BinaryTree.Node;


public class BSTRecursive {


    private Node root;


    BSTRecursive() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }


    private Node delete(Node node, int data) {
        if (node == null) {
            System.out.println("No such data present in BST.");
        } else if (node.data > data) {
            node.left = delete(node.left, data);
        } else if (node.data < data) {
            node.right = delete(node.right, data);
        } else {
            if (node.right == null && node.left == null) {
                node = null;
            } else if (node.left == null) {
                Node temp = node.right;
                node.right = null;
                node = temp;
            } else if (node.right == null) {
                Node temp = node.left;
                node.left = null;
                node = temp;
            } else {
                Node temp = node.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                node.data = temp.data;
                node.right = delete(node.right, temp.data);
            }
        }
        return node;
    }


    private Node insert(Node node, int data) {
        if (node == null) {
            node = new Node(data);
        } else if (node.data > data) {
            node.left = insert(node.left, data);
        } else if (node.data < data) {
            node.right = insert(node.right, data);
        }
        return node;
    }


    private boolean search(Node node, int data) {
        if (node == null) {
            return false;
        } else if (node.data == data) {
            return true;
        } else if (node.data > data) {
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }


    public void add(int data) {
        this.root = insert(this.root, data);
    }


    public void remove(int data) {
        this.root = delete(this.root, data);
    }


    public boolean find(int data) {
        if (search(this.root, data)) {
            System.out.println(data + " is present in given BST.");
            return true;
        }
        System.out.println(data + " not found.");
        return false;
    }
}

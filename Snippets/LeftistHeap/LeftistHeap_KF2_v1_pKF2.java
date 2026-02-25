package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap {

    /**
     * Node of a leftist heap.
     * element: stored key
     * npl: null-path length (shortest distance to a null child)
     * left, right: children
     */
    private static final class Node {
        private final int element;
        private int npl;
        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.npl = 0;
        }
    }

    private Node root;

    public LeftistHeap() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    /**
     * Merge another heap into this heap.
     * After merging, the other heap becomes empty.
     */
    public void merge(LeftistHeap other) {
        if (other == null || other.root == null) {
            return;
        }
        root = merge(root, other.root);
        other.root = null;
    }

    /**
     * Merge two heap roots and return the new root.
     */
    private Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        // Ensure that 'a' has the smaller root element
        if (a.element > b.element) {
            Node temp = a;
            a = b;
            b = temp;
        }

        // Merge 'b' into the right subtree of 'a'
        a.right = merge(a.right, b);

        // Maintain leftist property: left subtree has >= npl than right
        if (a.left == null) {
            a.left = a.right;
            a.right = null;
        } else {
            if (a.right != null && a.left.npl < a.right.npl) {
                Node temp = a.left;
                a.left = a.right;
                a.right = temp;
            }
            a.npl = (a.right == null ? 0 : a.right.npl + 1);
        }

        return a;
    }

    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    /**
     * Remove and return the minimum element (root).
     * Returns -1 if the heap is empty.
     */
    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int min = root.element;
        root = merge(root.left, root.right);
        return min;
    }

    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrder(root, result);
        return new ArrayList<>(result);
    }

    private void inOrder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrder(node.left, result);
        result.add(node.element);
        inOrder(node.right, result);
    }
}
package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap {

    private static final class Node {
        private final int element;
        private int nullPathLength;
        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.nullPathLength = 0;
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

    public void merge(LeftistHeap otherHeap) {
        if (otherHeap == null || otherHeap.root == null) {
            return;
        }
        root = merge(root, otherHeap.root);
        otherHeap.root = null;
    }

    private Node merge(Node first, Node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        if (first.element > second.element) {
            Node temp = first;
            first = second;
            second = temp;
        }

        first.right = merge(first.right, second);
        enforceLeftistProperty(first);
        updateNullPathLength(first);

        return first;
    }

    private void enforceLeftistProperty(Node node) {
        if (node.left == null) {
            node.left = node.right;
            node.right = null;
        } else if (node.right != null && node.left.nullPathLength < node.right.nullPathLength) {
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
    }

    private void updateNullPathLength(Node node) {
        node.nullPathLength = (node.right == null) ? 0 : node.right.nullPathLength + 1;
    }

    public void insert(int value) {
        root = merge(new Node(value), root);
    }

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
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.element);
        inOrderTraversal(node.right, result);
    }
}
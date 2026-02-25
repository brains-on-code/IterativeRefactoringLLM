package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Leftist Heap implementation.
 */
public class LeftistHeap {

    private static final class Node {
        private final int key;
        private int rank;
        private Node left;
        private Node right;

        private Node(int key) {
            this.key = key;
            this.rank = 0;
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

    public void merge(LeftistHeap other) {
        if (other == null || other.root == null) {
            return;
        }
        root = merge(root, other.root);
        other.root = null;
    }

    private Node merge(Node first, Node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        if (first.key > second.key) {
            Node temp = first;
            first = second;
            second = temp;
        }

        first.right = merge(first.right, second);

        if (first.left == null) {
            first.left = first.right;
            first.right = null;
        } else {
            if (first.right != null && first.left.rank < first.right.rank) {
                Node temp = first.left;
                first.left = first.right;
                first.right = temp;
            }
            first.rank = (first.right == null ? 0 : first.right.rank) + 1;
        }

        return first;
    }

    public void insert(int key) {
        root = merge(new Node(key), root);
    }

    public int deleteMin() {
        if (isEmpty()) {
            return -1;
        }

        int min = root.key;
        root = merge(root.left, root.right);
        return min;
    }

    public List<Integer> toList() {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.key);
        inOrderTraversal(node.right, result);
    }
}
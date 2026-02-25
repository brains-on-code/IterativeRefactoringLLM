package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Leftist Heap implementation.
 */
public class LeftistHeap {

    private static final class Node {
        private final int value;
        private int nullPathLength;
        private Node left;
        private Node right;

        private Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.nullPathLength = 0;
        }
    }

    private Node root;

    public LeftistHeap() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void merge(LeftistHeap otherHeap) {
        root = merge(root, otherHeap.root);
        otherHeap.root = null;
    }

    private Node merge(Node firstRoot, Node secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }

        if (secondRoot == null) {
            return firstRoot;
        }

        if (firstRoot.value > secondRoot.value) {
            Node temp = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temp;
        }

        firstRoot.right = merge(firstRoot.right, secondRoot);

        if (firstRoot.left == null) {
            firstRoot.left = firstRoot.right;
            firstRoot.right = null;
        } else {
            if (firstRoot.right != null && firstRoot.left.nullPathLength < firstRoot.right.nullPathLength) {
                Node temp = firstRoot.left;
                firstRoot.left = firstRoot.right;
                firstRoot.right = temp;
            }
            firstRoot.nullPathLength = (firstRoot.right == null ? 0 : firstRoot.right.nullPathLength) + 1;
        }
        return firstRoot;
    }

    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    public int deleteMin() {
        if (isEmpty()) {
            return -1;
        }

        int minValue = root.value;
        root = merge(root.left, root.right);
        return minValue;
    }

    public List<Integer> toSortedList() {
        List<Integer> sortedElements = new ArrayList<>();
        inOrderTraversal(root, sortedElements);
        return new ArrayList<>(sortedElements);
    }

    private void inOrderTraversal(Node current, List<Integer> result) {
        if (current == null) {
            return;
        }
        inOrderTraversal(current.left, result);
        result.add(current.value);
        inOrderTraversal(current.right, result);
    }
}
package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap {
    private static final class Node {
        private final int key;
        private int nullPathLength;
        private Node left;
        private Node right;

        private Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
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
        root = merge(root, otherHeap.root);
        otherHeap.root = null;
    }

    public Node merge(Node firstRoot, Node secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }

        if (secondRoot == null) {
            return firstRoot;
        }

        if (firstRoot.key > secondRoot.key) {
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

    public void insert(int key) {
        root = merge(new Node(key), root);
    }

    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int minKey = root.key;
        root = merge(root.left, root.right);
        return minKey;
    }

    public List<Integer> inOrder() {
        List<Integer> traversalResult = new ArrayList<>();
        inOrderTraversal(root, traversalResult);
        return new ArrayList<>(traversalResult);
    }

    private void inOrderTraversal(Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.left, traversalResult);
        traversalResult.add(currentNode.key);
        inOrderTraversal(currentNode.right, traversalResult);
    }
}
package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap {
    private static final class Node {
        private final int value;
        private int nullPathLength;
        private Node leftChild;
        private Node rightChild;

        private Node(int value) {
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
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

        if (firstRoot.value > secondRoot.value) {
            Node temp = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temp;
        }

        firstRoot.rightChild = merge(firstRoot.rightChild, secondRoot);

        if (firstRoot.leftChild == null) {
            firstRoot.leftChild = firstRoot.rightChild;
            firstRoot.rightChild = null;
        } else {
            if (firstRoot.leftChild.nullPathLength < firstRoot.rightChild.nullPathLength) {
                Node temp = firstRoot.leftChild;
                firstRoot.leftChild = firstRoot.rightChild;
                firstRoot.rightChild = temp;
            }
            firstRoot.nullPathLength = firstRoot.rightChild.nullPathLength + 1;
        }
        return firstRoot;
    }

    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int minValue = root.value;
        root = merge(root.leftChild, root.rightChild);
        return minValue;
    }

    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return new ArrayList<>(result);
    }

    private void inOrderTraversal(Node current, List<Integer> result) {
        if (current == null) {
            return;
        }
        inOrderTraversal(current.leftChild, result);
        result.add(current.value);
        inOrderTraversal(current.rightChild, result);
    }
}
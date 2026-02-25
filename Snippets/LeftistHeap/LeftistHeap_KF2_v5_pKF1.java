package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap {
    private static final class Node {
        private final int key;
        private int nullPathLength;
        private Node leftChild;
        private Node rightChild;

        private Node(int key) {
            this.key = key;
            this.leftChild = null;
            this.rightChild = null;
            this.nullPathLength = 0;
        }
    }

    private Node rootNode;

    public LeftistHeap() {
        this.rootNode = null;
    }

    public boolean isEmpty() {
        return rootNode == null;
    }

    public void clear() {
        rootNode = null;
    }

    public void merge(LeftistHeap otherHeap) {
        rootNode = merge(rootNode, otherHeap.rootNode);
        otherHeap.rootNode = null;
    }

    public Node merge(Node firstRoot, Node secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }

        if (secondRoot == null) {
            return firstRoot;
        }

        if (firstRoot.key > secondRoot.key) {
            Node tempRoot = firstRoot;
            firstRoot = secondRoot;
            secondRoot = tempRoot;
        }

        firstRoot.rightChild = merge(firstRoot.rightChild, secondRoot);

        if (firstRoot.leftChild == null) {
            firstRoot.leftChild = firstRoot.rightChild;
            firstRoot.rightChild = null;
        } else {
            if (firstRoot.rightChild != null
                    && firstRoot.leftChild.nullPathLength < firstRoot.rightChild.nullPathLength) {
                Node tempChild = firstRoot.leftChild;
                firstRoot.leftChild = firstRoot.rightChild;
                firstRoot.rightChild = tempChild;
            }
            firstRoot.nullPathLength =
                    (firstRoot.rightChild == null ? 0 : firstRoot.rightChild.nullPathLength) + 1;
        }
        return firstRoot;
    }

    public void insert(int key) {
        rootNode = merge(new Node(key), rootNode);
    }

    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int minKey = rootNode.key;
        rootNode = merge(rootNode.leftChild, rootNode.rightChild);
        return minKey;
    }

    public List<Integer> inOrder() {
        List<Integer> traversalResult = new ArrayList<>();
        inOrderTraversal(rootNode, traversalResult);
        return new ArrayList<>(traversalResult);
    }

    private void inOrderTraversal(Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, traversalResult);
        traversalResult.add(currentNode.key);
        inOrderTraversal(currentNode.rightChild, traversalResult);
    }
}